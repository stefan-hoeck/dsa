package efa.dsa.being.calc

import efa.core.{ValRes, Validators, ValSt, EndoVal}
import efa.dsa.abilities._
import efa.dsa.being.generation.GenData
import efa.dsa.being.skills._
import efa.dsa.being.HeroData
import efa.dsa.generation.{SkillPrototype, SkillPrototypes}
import efa.dsa.world.RaisingCost
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
   * Lens for SkillPrototypes
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

  def skillList: Skills ⇒ List[SKILL] = s ⇒ 
    skills.get(s).toList map (_._2) sortBy (_.name)

  def itemToData (i: I): D = (for {
    _ ← SD.idL := SI.id(i)
    _ ← SD.raisingCostL := SI.raisingCost(i)
  } yield ()) exec SD.default

  final def addI (i: I): State[SkillDatas,Unit] = add (itemToData(i))

  final def delete(s: SKILL): State[SkillDatas,Unit] =
    data -= s.id void
    
  final def add(d: D): State[SkillDatas,Unit] =
    data += (SD.id(d) → d) void
    
  final def set[X] (l: D @> X): (SKILL,X) ⇒ State[SkillDatas,Unit] =
    (s,x) ⇒ add(l set (s.skill, x))

  final lazy val special = set(SD.specialExpL)

  final lazy val raisingCost = set(SD.raisingCostL)

//  private def rlSkill(h: Hero, id: Int, tapAdd: Int, ap: Mod => Int): HeroData = 
//    modsFromData(h.skills) find (_.parent.parentId == id) map {m => 
//      val sd = h.heroData.skilledData
//      val s = (skillsFromData(sd) find (_.parentId == id)).get
//      val usedAp = h.heroData.apUsed + ap(m)
//      if (ap(m) == 0 || usedAp < 0 || usedAp > h.heroData.ap) h.heroData else {
//        h.heroData
//        .skilledData_=(updateS(sd, s.tap_=(s.tap + tapAdd).specialExp_=(false)))
//        .apUsed_=(usedAp)
//      }
//    } getOrElse h.heroData
  
//  /**
//   * Raises a skill of a hero and lowers its rest AP at the same time. The skill
//   * is not raised, if the ap-cost is higher than the hero can afford.
//   */
//  final def raiseSkill(h: Hero, id: Int): HeroData = rlSkill(h, id, 1, _.raiseAp)
//  final def lowerSkill(h: Hero, id: Int): HeroData = rlSkill(h, id, -1, - _.lowerAp)
//  
  final def heroSkills (h: HeroData, as: AbilityItems): DB[SKILL] = {
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
    
    data get h.skills flatMap single
  }
}

object SkillLinker {

  def heroSkills (h: HeroData, as: AbilityItems): Skills = Skills (
    LanguageLinker heroSkills (h, as),
    MeleeTalentLinker heroSkills (h, as),
    RangedTalentLinker heroSkills (h, as),
    RitualLinker heroSkills (h, as),
    ScriptureLinker heroSkills (h, as),
    SpellLinker heroSkills (h, as),
    TalentLinker heroSkills (h, as)
  )

  implicit val LanguageLinker = new TalentDataLinker[LanguageItem] {
    def data = SkillDatas.languages
    def prototypes = SkillPrototypes.languages
    def items = AbilityItems.languages
    def skills = Skills.languages
  }

  implicit val MeleeTalentLinker =
  new SkillLinker[MeleeTalentItem,MeleeTalentData]{
    def data = SkillDatas.meleeTalents
    def prototypes = SkillPrototypes.meleeTalents
    def items = AbilityItems.meleeTalents
    def skills = Skills.meleeTalents

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
    def data = SkillDatas.rangedTalents
    def prototypes = SkillPrototypes.rangedTalents
    def items = AbilityItems.rangedTalents
    def skills = Skills.rangedTalents
  }

  implicit val RitualLinker = new TalentDataLinker[RitualItem] {
    def data = SkillDatas.rituals
    def prototypes = SkillPrototypes.rituals
    def items = AbilityItems.rituals
    def skills = Skills.rituals
  }

  implicit val ScriptureLinker = new TalentDataLinker[ScriptureItem] {
    def data = SkillDatas.scriptures
    def prototypes = SkillPrototypes.scriptures
    def items = AbilityItems.scriptures
    def skills = Skills.scriptures
  }

  implicit val SpellLinker = new SkillLinker[SpellItem,SpellData] {
    def data = SkillDatas.spells
    def prototypes = SkillPrototypes.spells
    def items = AbilityItems.spells
    def skills = Skills.spells
  }

  implicit val TalentLinker = new TalentDataLinker[TalentItem] {
    def data = SkillDatas.talents
    def prototypes = SkillPrototypes.talents
    def items = AbilityItems.talents
    def skills = Skills.talents
  }

  sealed abstract class TalentDataLinker[I:SkillItem]
     extends SkillLinker[I,TalentData]
}

// vim: set ts=2 sw=2 et:
