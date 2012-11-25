package efa.dsa.being.calc

import efa.core.Efa._
import efa.dsa.abilities._
import efa.dsa.being.generation.GenData
import efa.dsa.being.skills._
import efa.dsa.generation.SkillPrototypes
import efa.dsa.test.{CompleteData, AbilityGens}
import org.scalacheck._, Prop._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

object SkillLinkerTest extends Properties("SkillLinker") {
  
  import SkillLinker._

  //property("heroSkills") = Prop.forAll {cd: CompleteData ⇒ 
  //  val skills = SkillLinker heroSkills (cd.hero, cd.abilities)

  //  heroSkills[LanguageItem,TalentData](cd, skills) &&
  //  heroSkills[MeleeTalentItem,MeleeTalentData](cd, skills) &&
  //  heroSkills[RangedTalentItem,TalentData](cd, skills) &&
  //  heroSkills[RitualItem,TalentData](cd, skills) &&
  //  heroSkills[ScriptureItem,TalentData](cd, skills) &&
  //  heroSkills[SpellItem,SpellData](cd, skills) &&
  //  heroSkills[TalentItem,TalentData](cd, skills)
  //}

  property("items") = Prop.forAll(AbilityGens.abilitiesGen) {as ⇒ 
    (LanguageLinker.items.get(as) ≟ as.languages) &&
    (MeleeTalentLinker.items.get(as) ≟ as.meleeTalents) &&
    (RangedTalentLinker.items.get(as) ≟ as.rangedTalents) &&
    (RitualLinker.items.get(as) ≟ as.rituals) &&
    (ScriptureLinker.items.get(as) ≟ as.scriptures) &&
    (SpellLinker.items.get(as) ≟ as.spells) &&
    (TalentLinker.items.get(as) ≟ as.talents)
  }

  property("data") = Prop.forAll {as: SkillDatas ⇒ 
    (LanguageLinker.data.get(as) ≟ as.languages) &&
    (MeleeTalentLinker.data.get(as) ≟ as.meleeTalents) &&
    (RangedTalentLinker.data.get(as) ≟ as.rangedTalents) &&
    (RitualLinker.data.get(as) ≟ as.rituals) &&
    (ScriptureLinker.data.get(as) ≟ as.scriptures) &&
    (SpellLinker.data.get(as) ≟ as.spells) &&
    (TalentLinker.data.get(as) ≟ as.talents)
  }

  property("prototypes") = Prop.forAll {as: SkillPrototypes ⇒ 
    (LanguageLinker.prototypes.get(as) ≟ as.languages) &&
    (MeleeTalentLinker.prototypes.get(as) ≟ as.meleeTalents) &&
    (RangedTalentLinker.prototypes.get(as) ≟ as.rangedTalents) &&
    (RitualLinker.prototypes.get(as) ≟ as.rituals) &&
    (ScriptureLinker.prototypes.get(as) ≟ as.scriptures) &&
    (SpellLinker.prototypes.get(as) ≟ as.spells) &&
    (TalentLinker.prototypes.get(as) ≟ as.talents)
  }

  def heroSkills[I,D] (
    cd: CompleteData,
    skills: Skills
  )(implicit
    ie: Equal[I],
    de: Equal[D],
    linker: SkillLinker[I,D],
    sd: SkillData[D]
  ): Prop = {

    def fromGenData(i: Int, gd: GenData): Int =
      linker.prototypes get gd.skills get i fold (_.value, 0)

    def tap(i: Int): Int = sd.tap(linker.data get cd.hero.humanoid.skills apply i)

    def taw (i: Int) =
      fromGenData(i, cd.hero.base.race.data) +
      fromGenData(i, cd.hero.base.culture.data) +
      fromGenData(i, cd.hero.base.profession) +
      tap(i)

    def checkLang (p: (Int, Skill[I,D])): Prop = {
      val (i, l) = p
      (l.item ≟ (linker.items get cd.abilities apply i)) :| "item" &&
      (l.skill ≟ (linker.data get cd.hero.humanoid.skills apply i)) :| "data" &&
      (l.permanentTaw ≟ taw(i)) :| "permanentTaw" &&
      (l.taw ≟ l.permanentTaw) :| "taw" &&
      (l.tap ≟ tap(i)) :| "tap"
    }
    
    linker.skills.get(skills).toList foldMap checkLang
  }
}

// vim: set ts=2 sw=2 et:
