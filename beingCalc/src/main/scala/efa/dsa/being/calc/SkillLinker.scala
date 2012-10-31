package efa.dsa.being.calc

import efa.dsa.abilities._
import efa.dsa.being.generation.GenData
import efa.dsa.being.skills._
import efa.dsa.being.HeroData
import efa.dsa.generation.{SkillPrototype, SkillPrototypes}
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
  protected val SL:SkillData[D]
) {
  type SKILL = Skill[I,D]

  /**
   * Converts a SkillItem to a corresponding SkillData
   */
  def newData(i: I, tap: Int): D
  
  /**
   * Converts a SkillPrototype to a corresponding Skill if the SkillPrototype's
   * parent was found in the DB
   */
  def protoToData(p: SkillPrototype, as: AbilityItems): Option[D] =
    items get as get p.parentId map (newData(_, p.value))
  
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

  final def delete(d: D): State[SkillDatas,DB[D]] = data -= SL.id(d)
    
  final def add(d: D): State[SkillDatas,DB[D]] = data += (SL.id(d) → d)

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
      val allMods = Modifier(loc.bought, SL tap s) :: genDataMods
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
    def newData(i: MeleeTalentItem, tap: Int) =
      MeleeTalentData(TalentData(i.id, tap, i.raisingCost, false), 0)

    def data = SkillDatas.meleeTalents
    def prototypes = SkillPrototypes.meleeTalents
    def items = AbilityItems.meleeTalents
    def skills = Skills.meleeTalents
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
    def newData(i: SpellItem, tap: Int) =
      SpellData(TalentData(i.id, tap, i.raisingCost, false), false, "")
   
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
     extends SkillLinker[I,TalentData] {

    def newData(i: I, tap: Int) =
      TalentData(SI id i, tap, SI raisingCost i, false)
  }
}

// vim: set ts=2 sw=2 et:
