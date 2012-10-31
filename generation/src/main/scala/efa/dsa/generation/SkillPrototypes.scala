package efa.dsa.generation

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.{DB, DBs, Util}
import org.scalacheck.Arbitrary, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class SkillPrototypes (
  languages: DB[SkillPrototype],
  meleeTalents: DB[SkillPrototype],
  rangedTalents: DB[SkillPrototype],
  rituals: DB[SkillPrototype],
  scriptures: DB[SkillPrototype],
  spells: DB[SkillPrototype],
  talents: DB[SkillPrototype]
)

object SkillPrototypes extends DBs with Util {
  lazy val default = SkillPrototypes(db, db, db, db, db, db, db)

  implicit lazy val SkillPrototypesDefault = Default default default

  implicit lazy val SkillPrototypesEqual = Equal.equalA[SkillPrototypes]

  implicit lazy val SkillPrototypesArbitrary = Arbitrary(
    ^(a[DB[SkillPrototype]],
      a[DB[SkillPrototype]],
      a[DB[SkillPrototype]],
      a[DB[SkillPrototype]],
      a[DB[SkillPrototype]],
      a[DB[SkillPrototype]],
      a[DB[SkillPrototype]])(SkillPrototypes.apply)
  )

  implicit lazy val SkillPrototypesToXml = new ToXml[SkillPrototypes] {
    implicit val psXml = dbToXml[SkillPrototype]("item")

    def fromXml (ns: Seq[Node]) =
      ^(ns.readTag[DB[SkillPrototype]]("languages"),
        ns.readTag[DB[SkillPrototype]]("meleeTalents"),
        ns.readTag[DB[SkillPrototype]]("rangedTalents"),
        ns.readTag[DB[SkillPrototype]]("rituals"),
        ns.readTag[DB[SkillPrototype]]("scriptures"),
        ns.readTag[DB[SkillPrototype]]("spells"),
        ns.readTag[DB[SkillPrototype]]("talents"))(SkillPrototypes.apply)

    def toXml (a: SkillPrototypes) = 
      ("languages" xml a.languages) ++
      ("meleeTalents" xml a.meleeTalents) ++
      ("rangedTalents" xml a.rangedTalents) ++
      ("rituals" xml a.rituals) ++
      ("scriptures" xml a.scriptures) ++
      ("spells" xml a.spells) ++
      ("talents" xml a.talents)
  }

  def read (ns: Seq[Node]) = SkillPrototypesToXml fromXml ns

  def write (s: SkillPrototypes) = SkillPrototypesToXml toXml s

  val languages: SkillPrototypes @> DB[SkillPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(languages = b), _.languages)

  val meleeTalents: SkillPrototypes @> DB[SkillPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(meleeTalents = b), _.meleeTalents)

  val rangedTalents: SkillPrototypes @> DB[SkillPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(rangedTalents = b), _.rangedTalents)

  val rituals: SkillPrototypes @> DB[SkillPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(rituals = b), _.rituals)

  val scriptures: SkillPrototypes @> DB[SkillPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(scriptures = b), _.scriptures)

  val spells: SkillPrototypes @> DB[SkillPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(spells = b), _.spells)

  val talents: SkillPrototypes @> DB[SkillPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(talents = b), _.talents)
}

// vim: set ts=2 sw=2 et:
