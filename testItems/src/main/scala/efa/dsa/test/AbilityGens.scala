package efa.dsa.test

import efa.dsa.abilities._
import efa.dsa.being.abilities._
import efa.dsa.being.skills._
import efa.dsa.world.{Ebe, RaisingCost}
import efa.rpg.core.{ItemData, DB, RpgItem, DBs}
import org.scalacheck._, Arbitrary.arbitrary
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._


object AbilityGens extends DBs {

  def abilityDatasGen (abs: AbilityItems): Gen[AbilityDatas] = {

    def advantageDataGen[A:RpgItem](db: DB[A]) = for {
      fds ← featDataGen(db)
      v   ← advValueGen
    } yield fds map {case (i,a) ⇒ (i, AdvantageData(a, v))}
 
    def featDataGen[A:RpgItem](db: DB[A]): Gen[Map[String,FeatData]] = {
      def fromBool (b: Boolean, n: String, i: Int) = (n, FeatData(i, n, b, ""))
      def gen(a: A) = arbitrary[Boolean] ∘ (fromBool(_, rpg name a, rpg id a))
      def fromSeq (as: Seq[(Int,A)]) =
        as.toList traverse (p ⇒ gen(p._2)) map (_.toMap)

      Gen someOf db flatMap fromSeq
    }

    for {
      adv ← advantageDataGen(abs.advantages)
      han ← advantageDataGen (abs.handicaps)
      fea ← featDataGen (abs.feats)
    } yield AbilityDatas(adv, han, fea)
  }

  def skillDatasGen (abs: AbilityItems): Gen[SkillDatas] = {
    def si[A:SkillItem] = implicitly[SkillItem[A]]

    def siToTalentData[A:SkillItem](a: A) = for {
      tap ← tapGen
      spe ← arbitrary[Boolean]
    } yield TalentData(si id a, tap, si raisingCost a, spe)

    def dbGen[A:SkillItem,B](db: DB[A], gen: TalentData ⇒ Gen[B]) = {
      def bGen (a: A): Gen[B] = siToTalentData(a) flatMap gen
      def pairGen (p: (Int,A)): Gen[(Int,B)] = bGen(p._2) ∘ ((p._1, _))
      def fromSeq (as: Seq[(Int,A)]) =
        as.toList traverse pairGen map (_.toMap)

      Gen someOf db flatMap fromSeq
    }

    def talentDataDbGen[A:SkillItem](db: DB[A]): Gen[DB[TalentData]] =
      dbGen(db, Gen value _)

    def meleeDbGen = {
      def gen (td: TalentData) =
        Gen choose (0, td.tap) map (MeleeTalentData(td, _))

      dbGen[MeleeTalentItem,MeleeTalentData](abs.meleeTalents, gen)
    }

    def spellDbGen = {
      def gen (td: TalentData) =
        arbitrary[Boolean] map (SpellData(td, _, ""))

      dbGen[SpellItem, SpellData](abs.spells, gen)
    }

    for {
      lan ← talentDataDbGen(abs.languages)
      mel ← meleeDbGen
      ran ← talentDataDbGen(abs.rangedTalents)
      rit ← talentDataDbGen(abs.rituals)
      scr ← talentDataDbGen(abs.scriptures)
      spe ← spellDbGen
      tal ← talentDataDbGen(abs.talents)
    } yield SkillDatas(lan, mel, ran, rit, scr, spe, tal)
  }

  lazy val abilitiesGen = for {
    adv ← advantagesGen
    han ← handicapsGen
    fea ← featsGen
    lan ← languagesGen
    mel ← meleeTalentsGen
    ran ← rangedTalentsGen
    rit ← ritualsGen
    scr ← scripturesGen
    spe ← spellsGen
    tal ← talentsGen
  } yield AbilityItems(adv, han, fea, lan, mel, ran, rit, scr, spe, tal)

  lazy val advantagesGen: Gen[DB[AdvantageItem]] =
    dbGen(advantageNames, id ⇒ Gp.gen map (AdvantageItem(id, _)))

  lazy val handicapsGen: Gen[DB[HandicapItem]] =
    dbGen(handicapNames, id ⇒ arbitrary[HandicapGp] map (HandicapItem(id, _)))

  lazy val featsGen: Gen[DB[FeatItem]] =
    dbGen(featNames, id ⇒ Ap.gen map (FeatItem(id, _, "")))

  lazy val languagesGen: Gen[DB[LanguageItem]] = {
    def gen (id: ItemData) =
      complexityGen map (LanguageItem(id, RaisingCost.A, _, "", ""))

    dbGen(languageNames, gen)
  }

  lazy val meleeTalentsGen: Gen[DB[MeleeTalentItem]] = {
    def gen (id: ItemData) = for {
      rc ← arbitrary[RaisingCost]
      ebe ← arbitrary[Ebe]
      bt ← arbitrary[Boolean]
    } yield MeleeTalentItem(id, ebe, rc, bt)

    dbGen(meleeTalentNames, gen)
  }

  lazy val rangedTalentsGen: Gen[DB[RangedTalentItem]] = {
    def gen (id: ItemData) = for {
      rc ← arbitrary[RaisingCost]
      ebe ← arbitrary[Ebe]
      bt ← arbitrary[Boolean]
    } yield RangedTalentItem(id, ebe, rc, bt)

    dbGen(rangedTalentNames, gen)
  }

  lazy val ritualsGen: Gen[DB[RitualItem]] =
    dbGen(ritualNames, id ⇒ Gen.value(RitualItem(id, RaisingCost.E)))

  lazy val scripturesGen: Gen[DB[ScriptureItem]] = {
    def gen (id: ItemData) =
      complexityGen map (ScriptureItem(id, RaisingCost.A, _))

    dbGen(scriptureNames, gen)
  }

  lazy val spellsGen: Gen[DB[SpellItem]] = {
    def gen (id: ItemData) = for {
      rc ← arbitrary[RaisingCost]
      atts ← arbitrary[Attributes]
    } yield SpellItem(id, rc, atts)

    dbGen(spellNames, gen)
  }

  lazy val talentsGen: Gen[DB[TalentItem]] = {
    def gen (id: ItemData) = for {
      atts ← arbitrary[Attributes]
      ebe ← arbitrary[Ebe]
      rc ← arbitrary[RaisingCost]
      ib ← arbitrary[Boolean]
      tt ← arbitrary[TalentType]
    } yield TalentItem(id, atts, ebe, rc, ib, tt)

    dbGen(talentNames, gen)
  }
    
  def dbGen[A] (names: List[String], gen: ItemData ⇒ Gen[A])
    (implicit R:RpgItem[A]): Gen[DB[A]] = {
    val seqGen = itemDatas(names) traverse gen

    seqGen map (_ map (a ⇒ (R id a, a)) toMap)
  }

  lazy val advantageNames = List(
    "Adlig",
    "Akademische Bildung [Magier]",
    "Ausdauernd",
    "Besonderer Besitz",
    "Eisern",
    "Flink"
  )
  
  lazy val featNames = List(
    "Aufmerksamkeit",
    "Finte",
    "Schildkampf I",
    "Schildkampf II",
    "Schildkampf III",
    "Wuchtschlag"
  )
  
  lazy val handicapNames = List(
    "Arroganz",
    "Eitel",
    "Kleinwüchsig",
    "Zwergenwuchs"
  )
  
  lazy val languageNames = List(
    "Garethi",
    "Oroarkh",
    "Rologan",
    "Tulamidya"
  )
  
  lazy val meleeTalentNames = List(
    "Dolche",
    "Hiebwaffen",
    "Schwerter",
    "Speere",
    "Stangenwaffen",
    "Zweihandhiebwaffen",
    "Zweihandschwerter"
  )
  
  lazy val rangedTalentNames = List(
    "Armbrust",
    "Bogen",
    "Schleuder"
  )
  
  lazy val ritualNames = List(
    "Ritualkenntnis [Druide]",
    "Ritualkenntnis [Gildenmagier]",
    "Ritualkenntnis [Hexe]"
  )
  
  lazy val scriptureNames = List(
    "Kusliker Zeichen",
    "Tulamydia"
  )
  
  lazy val spellNames = List(
    "Armatrutz",
    "Balsam Salabunde",
    "Horriphobus Schreckenspein",
    "Zagibu"
  )
  
  lazy val talentNames = List(
    "Athletik",
    "Fährtensuchen",
    "Schmiedekunst",
    "Zechen"
  )
}

// vim: set ts=2 sw=2 et:
