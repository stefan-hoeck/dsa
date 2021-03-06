package efa.dsa.being.calc

import efa.core.{ValRes, Validators, ValSt, EndoVal}
import efa.dsa.abilities._
import efa.dsa.being.generation.GenData
import efa.dsa.being.skills._
import efa.dsa.being.{HumanoidData ⇒ HuD, HeroData ⇒ HD, Hero}
import efa.dsa.generation.{SkillPrototype, SkillPrototypes, NamedPrototype}
import efa.dsa.world.{RaisingCost, SkillMaps}
import efa.rpg.core.{DB, Modifier}
import scalaz._, Scalaz._

/**
 * This trait is used to provide a logical connection between SkillItems 
 * and Skills, Skills, SkillPrototypes, and Skills. These
 * are all helper methods used for interconversion between
 * several of the classes and groups of classes. 
 */
sealed abstract class SkillLinker[I,D] (implicit
  protected val SI:SkillItem[I],
  protected val SD:SkillData[D]
) {
  type SKILL = Skill[I,D]
  
  /**
   * Lens for items
   */
  def items: Lens[AbilityItems, DB[I]]

  /**
   * Lens for SkillPrototypes
   */
  def prototypes: Lens[SkillPrototypes, DB[SkillPrototype]]
  
  /**
   * Lens for Skills
   */
  def data: Lens[SkillDatas, DB[D]]
  
  /**
   * Lens for Skills
   */
  def skills: Lens[Skills, DB[SKILL]]

  final def protoList (gen: Hero ⇒ GenData)(h: Hero): List[NamedPrototype] = {
    def ss = skills get h.skills
    def ps = prototypes get gen(h).skills
    def toNamed (p: SkillPrototype) =
      ss get p.parentId map (s ⇒ NamedPrototype(s.name, p.parentId, p.value))

    ps.toList flatMap (p ⇒ toNamed(p._2)) sortBy (_.name)
  }

  final def protoValue (n: NamedPrototype, v: Int): State[SkillPrototypes,Unit] =
    prototypes += (n.id → SkillPrototype(n.id, v)) void

  final def skillList: Skills ⇒ List[SKILL] = s ⇒ 
    skills.get(s).toList map (_._2) sortBy (_.name)

  final def itemToData (i: I): D = (for {
    _ ← SD.idL := SI.id(i)
    _ ← SD.raisingCostL := SI.raisingCost(i)
  } yield ()) exec SD.default

  final def addI (i: I): State[SkillDatas,Unit] = add (itemToData(i))

  final def addIProto (l: HD @> GenData)(i: I): State[HD,Unit] = {
    val id = SI id i

    for {
      hd ← init[HD]
      _  ← (l.skills >=> prototypes) += (id → SkillPrototype(id, 0))
      _  ← data.get(hd.humanoid.skills).keySet(id) ?  (init[HD] void) | addIHD(i)
    } yield ()
  }
  
  final def addIHD (i: I): State[HD,Unit] = HD.humanoid.skills lifts addI(i)

  final def delete(s: SKILL): State[SkillDatas,Unit] =
    data -= s.id void
    
  final def add(d: D): State[SkillDatas,Unit] =
    data += (SD.id(d) → d) void
    
  final def set[X] (l: D @> X): (SKILL,X) ⇒ State[SkillDatas,Unit] =
    (s,x) ⇒ setS(l := x void)(s)
    
  final def setS[X] (st: State[D,Unit]): SKILL ⇒ State[SkillDatas,Unit] =
    s ⇒ add(st exec s.skill)

  final lazy val special = set(SD.specialExpL)

  final lazy val raisingCost = set(SD.raisingCostL)

  private def rlSkill(s: SKILL, cost: Int, add: Int): State[HD,Unit] = for {
    hd ← init[HD]
    ap = hd.base.apUsed + cost
    _  ← if (ap >= 0 && ap <= hd.base.ap) for {
           _ ← HD.humanoid.skills lifts (
               setS(
                 (SD.tapL := (s.tap + add)) >>
                 (SD.specialExpL := false void)
               ) apply s
             )
           _ ← HD.base.apUsed := ap
         } yield () else init[HD].void
  } yield ()
  
  /**
   * Raises a skill of a hero and lowers its rest AP at
   * the same time. The skill
   * is not raised, if the ap-cost is higher than the hero can afford.
   */
  final def raise(s: SKILL): State[HD,Unit] = rlSkill(s, s.raiseAp, 1)

  final def lower(s: SKILL): State[HD,Unit] = rlSkill(s, -s.lowerAp, -1)

  final def heroSkills (h: HD, as: AbilityItems): DB[SKILL] = {
    def single(p: (Int,D)): Option[(Int, SKILL)] = {
      val (id, s) = p

      def fromGenData(g: GenData): Option[Modifier] =
        prototypes get g.skills get id map (p ⇒ Modifier(g.name, p.value))

      def hum = h.base
      def genData = List(hum.race.data, hum.culture.data, hum.profession)
      def genDataMods =  genData flatMap fromGenData
      val allMods = Modifier(loc.bought, SD tap s) :: genDataMods
      val permanentTaw = allMods foldMap (_.value) toInt
      def idSkillPair (i: I) = (id, Skill(i, s, permanentTaw, allMods))
      
      items get as get id map idSkillPair
    }
    
    data get h.humanoid.skills flatMap single
  }
}

object SkillLinker {

  private val spl = Lens.lensId[SkillPrototypes]
  private val sdl = Lens.lensId[SkillDatas]
  private val sl = Lens.lensId[Skills]

  def heroSkills(h: HD, as: AbilityItems): Skills = SkillMaps(
    LanguageLinker heroSkills (h, as),
    MeleeTalentLinker heroSkills (h, as),
    RangedTalentLinker heroSkills (h, as),
    RitualLinker heroSkills (h, as),
    ScriptureLinker heroSkills (h, as),
    SpellLinker heroSkills (h, as),
    TalentLinker heroSkills (h, as)
  )

  implicit val LanguageLinker = new TalentDataLinker[LanguageItem] {
    def data = sdl.languages
    def prototypes = spl.languages
    def items = AbilityItems.languages
    def skills = sl.languages
  }

  implicit object MeleeTalentLinker
     extends SkillLinker[MeleeTalentItem,MeleeTalentData]{
    def data = sdl.meleeTalents
    def prototypes = spl.meleeTalents
    def items = AbilityItems.meleeTalents
    def skills = sl.meleeTalents

    import scalaz.Validation.FlatMap._
    def setAt (s: SKILL, vi: ValRes[Int]): ValSt[SkillDatas] = {
      def setAt (i: Int) = set(MeleeTalentData.at)(s, i)

      vi flatMap (atVal(s) run _ validation) map setAt
    }

    def at (s: SKILL): Int = s.skill.at

    def pa (s: SKILL): Int = s.taw - at(s)

    def setPa (s: SKILL, vi: ValRes[Int]): ValSt[SkillDatas] =
      setAt (s, vi map (s.taw - _))

    def atVal (s: SKILL): EndoVal[Int] = {
      //if taw <= 0: 0
      //if taw > 0: the lower of taw and At.max
      val maxAt = (s.taw <= 0) ? 0 | (s.taw min At.max)

      //if taw < 0: the higher of taw and At.min
      //if taw >= 0: 0
      val minAt = (s.taw >= 0) ? 0 | (s.taw max At.min)

      Validators.interval (minAt, maxAt)
    }
  }

  implicit val RangedTalentLinker = new TalentDataLinker[RangedTalentItem] {
    def data = sdl.rangedTalents
    def prototypes = spl.rangedTalents
    def items = AbilityItems.rangedTalents
    def skills = sl.rangedTalents
  }

  implicit val RitualLinker = new TalentDataLinker[RitualItem] {
    def data = sdl.rituals
    def prototypes = spl.rituals
    def items = AbilityItems.rituals
    def skills = sl.rituals
  }

  implicit val ScriptureLinker = new TalentDataLinker[ScriptureItem] {
    def data = sdl.scriptures
    def prototypes = spl.scriptures
    def items = AbilityItems.scriptures
    def skills = sl.scriptures
  }

  implicit val SpellLinker = new SkillLinker[SpellItem,SpellData] {
    def data = sdl.spells
    def prototypes = spl.spells
    def items = AbilityItems.spells
    def skills = sl.spells
  }

  implicit val TalentLinker = new TalentDataLinker[TalentItem] {
    def data = sdl.talents
    def prototypes = spl.talents
    def items = AbilityItems.talents
    def skills = sl.talents
  }

  sealed abstract class TalentDataLinker[I:SkillItem]
     extends SkillLinker[I,TalentData]
}

// vim: set ts=2 sw=2 et:
