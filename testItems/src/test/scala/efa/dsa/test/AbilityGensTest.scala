package efa.dsa.test

import efa.dsa.abilities.{AbilityItems}
import efa.dsa.being.abilities.{AbilityDatas}
import efa.dsa.being.skills.{SkillDatas}
import efa.rpg.core.{RpgItem, DB}
import org.scalacheck._, Prop._
import scalaz._, Scalaz._

object AbilityGensTest extends Properties("AbilityGens") with ItemGensTest {
  import AbilityGens._

  val dataGen: Gen[(AbilityItems, AbilityDatas, SkillDatas)] = for {
    abs ← abilitiesGen
    abd ← abilityDatasGen(abs)
    skl ← skillDatasGen(abs)
  } yield (abs, abd, skl)

  property("specs") = Prop.forAll(dataGen) {t ⇒ 
    val (abs, abd, skl) = t

    advantages(abs) &&
    handicaps(abs) &&
    feats(abs) &&
    languages(abs) &&
    meleeTalents(abs) &&
    rangedTalents(abs) &&
    rituals(abs) &&
    scriptures(abs) &&
    spells(abs) &&
    talents(abs) &&
    advantageDatas(abs, abd) &&
    handicapDatas(abs, abd) &&
    featDatas(abs, abd) &&
    languageDatas(abs, skl) &&
    meleeTalentDatas(abs, skl) &&
    rangedTalentDatas(abs, skl) &&
    ritualDatas(abs, skl) &&
    scriptureDatas(abs, skl) &&
    spellDatas(abs, skl) &&
    talentDatas(abs, skl)
  }
 
  def advantages (as: AbilityItems) =
    testDB(as.advantages, advantageNames) :| "advantages"

  def handicaps (as: AbilityItems) =
    testDB(as.handicaps, handicapNames) :| "handicaps"

  def feats (as: AbilityItems) =
    testDB(as.feats, featNames) :| "feats"

  def languages (as: AbilityItems) =
    testDB(as.languages, languageNames) :| "languages"

  def meleeTalents (as: AbilityItems) =
    testDB(as.meleeTalents, meleeTalentNames) :| "meleeTalents"

  def rangedTalents (as: AbilityItems) =
    testDB(as.rangedTalents, rangedTalentNames) :| "rangedTalents"

  def rituals (as: AbilityItems) =
    testDB(as.rituals, ritualNames) :| "rituals"

  def scriptures (as: AbilityItems) =
    testDB(as.scriptures, scriptureNames) :| "scriptures"

  def spells (as: AbilityItems) =
    testDB(as.spells, spellNames) :| "spells"

  def talents (as: AbilityItems) =
    testDB(as.talents, talentNames) :| "talents"

  def advantageDatas (i: AbilityItems, d: AbilityDatas) =
    d.advantages forall {case (n,a) ⇒ i.advantages get a.parentId nonEmpty}

  def handicapDatas (i: AbilityItems, d: AbilityDatas) =
    d.handicaps forall {case (n,a) ⇒ i.handicaps get a.parentId nonEmpty}

  def featDatas (i: AbilityItems, d: AbilityDatas) =
    d.feats forall {case (n,a) ⇒ i.feats get a.parentId nonEmpty}

  def languageDatas (i: AbilityItems, d: SkillDatas) =
    d.languages forall {case (n,a) ⇒ i.languages get a.id nonEmpty}

  def meleeTalentDatas (i: AbilityItems, d: SkillDatas) =
    d.meleeTalents forall {case (n,a) ⇒ i.meleeTalents get a.id nonEmpty}

  def rangedTalentDatas (i: AbilityItems, d: SkillDatas) =
    d.rangedTalents forall {case (n,a) ⇒ i.rangedTalents get a.id nonEmpty}

  def ritualDatas (i: AbilityItems, d: SkillDatas) =
    d.rituals forall {case (n,a) ⇒ i.rituals get a.id nonEmpty}

  def scriptureDatas (i: AbilityItems, d: SkillDatas) =
    d.scriptures forall {case (n,a) ⇒ i.scriptures get a.id nonEmpty}

  def spellDatas (i: AbilityItems, d: SkillDatas) =
    d.spells forall {case (n,a) ⇒ i.spells get a.id nonEmpty}

  def talentDatas (i: AbilityItems, d: SkillDatas) =
    d.talents forall {case (n,a) ⇒ i.talents get a.id nonEmpty}
}

// vim: set ts=2 sw=2 et:
