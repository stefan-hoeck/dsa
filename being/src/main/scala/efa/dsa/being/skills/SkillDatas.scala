package efa.dsa.being.skills

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.{DB, DBs, Util}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class SkillDatas (
  languages: DB[TalentData],
  meleeTalents: DB[MeleeTalentData],
  rangedTalents: DB[TalentData],
  rituals: DB[TalentData],
  scriptures: DB[TalentData],
  spells: DB[SpellData],
  talents: DB[TalentData]
)

object SkillDatas extends Util with DBs {
  lazy val default = SkillDatas(db, db, db, db, db, db, db)

  implicit lazy val SkillDatasDefault = Default default default

  implicit lazy val SkillDatasEqual = Equal.equalA[SkillDatas]

  implicit lazy val SkillDatasArbitrary = Arbitrary(
    ^(a[DB[TalentData]],
      a[DB[MeleeTalentData]],
      a[DB[TalentData]],
      a[DB[TalentData]],
      a[DB[TalentData]],
      a[DB[SpellData]],
      a[DB[TalentData]])(SkillDatas.apply)
  )

  implicit lazy val SkillDatasToXml = new ToXml[SkillDatas] {
    implicit val spellXml = dbToXml[SpellData]("dsa_spell")
    implicit val meleeXml = dbToXml[MeleeTalentData]("dsa_meleeTalent")
    implicit val sdXml = dbToXml[TalentData]("dsa_talentData")

    def fromXml (ns: Seq[Node]) =
      ^(ns.readTag[DB[TalentData]]("languages"),
        ns.readTag[DB[MeleeTalentData]]("meleeTalents"),
        ns.readTag[DB[TalentData]]("rangedTalents"),
        ns.readTag[DB[TalentData]]("rituals"),
        ns.readTag[DB[TalentData]]("scriptures"),
        ns.readTag[DB[SpellData]]("spells"),
        ns.readTag[DB[TalentData]]("talents"))(SkillDatas.apply)

    def toXml (a: SkillDatas) = 
      ("languages" xml a.languages) ++
      ("meleeTalents" xml a.meleeTalents) ++
      ("rangedTalents" xml a.rangedTalents) ++
      ("rituals" xml a.rituals) ++
      ("scriptures" xml a.scriptures) ++
      ("spells" xml a.spells) ++
      ("talents" xml a.talents)
  }

  def read (ns: Seq[Node]) = SkillDatasToXml fromXml ns

  def write (h: SkillDatas) = SkillDatasToXml toXml h

  val languages: SkillDatas @> DB[TalentData] =
    Lens.lensu((a,b) ⇒ a.copy(languages = b), _.languages)

  val meleeTalents: SkillDatas @> DB[MeleeTalentData] =
    Lens.lensu((a,b) ⇒ a.copy(meleeTalents = b), _.meleeTalents)

  val rangedTalents: SkillDatas @> DB[TalentData] =
    Lens.lensu((a,b) ⇒ a.copy(rangedTalents = b), _.rangedTalents)

  val rituals: SkillDatas @> DB[TalentData] =
    Lens.lensu((a,b) ⇒ a.copy(rituals = b), _.rituals)

  val scriptures: SkillDatas @> DB[TalentData] =
    Lens.lensu((a,b) ⇒ a.copy(scriptures = b), _.scriptures)

  val spells: SkillDatas @> DB[SpellData] =
    Lens.lensu((a,b) ⇒ a.copy(spells = b), _.spells)

  val talents: SkillDatas @> DB[TalentData] =
    Lens.lensu((a,b) ⇒ a.copy(talents = b), _.talents)
}

// vim: set ts=2 sw=2 et:
