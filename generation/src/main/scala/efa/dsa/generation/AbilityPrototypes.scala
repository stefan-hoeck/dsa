package efa.dsa.generation

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.{DB, DBs, Util}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class AbilityPrototypes (
  advantages: DB[AbilityPrototype],
  handicaps: DB[AbilityPrototype],
  feats: DB[AbilityPrototype]
)

object AbilityPrototypes extends Util with DBs {
  lazy val default = AbilityPrototypes(db, db, db)

  implicit lazy val AbilityPrototypesDefault = Default default default

  implicit lazy val AbilityPrototypesEqual =
    Equal.equalA[AbilityPrototypes]

  implicit lazy val AbilityPrototypesArbitrary = Arbitrary(
    ^^(a[DB[AbilityPrototype]],
      a[DB[AbilityPrototype]],
      a[DB[AbilityPrototype]])(AbilityPrototypes.apply)
  )

  implicit lazy val AbilityPrototypesToXml = new ToXml[AbilityPrototypes] {
    implicit val psXml = dbToXml[AbilityPrototype]("item")

    def fromXml (ns: Seq[Node]) =
      ^^(ns.readTag[DB[AbilityPrototype]]("advantages"),
        ns.readTag[DB[AbilityPrototype]]("handicaps"),
        ns.readTag[DB[AbilityPrototype]]("feats"))(AbilityPrototypes.apply)

    def toXml (a: AbilityPrototypes) = 
      ("advantages" xml a.advantages) ++
      ("handicaps" xml a.handicaps) ++
      ("feats" xml a.feats)
  }

  def read (ns: Seq[Node]) = AbilityPrototypesToXml fromXml ns

  def write (s: AbilityPrototypes) = AbilityPrototypesToXml toXml s

  val advantages: AbilityPrototypes @> DB[AbilityPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(advantages = b), _.advantages)

  val handicaps: AbilityPrototypes @> DB[AbilityPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(handicaps = b), _.handicaps)

  val feats: AbilityPrototypes @> DB[AbilityPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(feats = b), _.feats)
  
}

// vim: set ts=2 sw=2 et:
