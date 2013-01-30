package efa.dsa.generation

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.core.std.map.{mapToXml, mapGen}
import efa.rpg.core.{DB, Util}
import org.scalacheck.Arbitrary, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class EquipmentPrototypes (
  ammunition: DB[EquipmentPrototype],
  armor: DB[EquipmentPrototype],
  articles: DB[EquipmentPrototype],
  meleeWeapons: DB[EquipmentPrototype],
  rangedWeapons: DB[EquipmentPrototype],
  shields: DB[EquipmentPrototype],
  zoneArmor: DB[EquipmentPrototype]
)

object EquipmentPrototypes extends Util {
  lazy val default = EquipmentPrototypes(db, db, db, db, db, db, db)

  implicit lazy val EquipmentPrototypesDefault = Default default default

  implicit lazy val EquipmentPrototypesEqual =
    Equal.equalA[EquipmentPrototypes]

  implicit lazy val EquipmentPrototypesArbitrary = Arbitrary(
    ^^^^^^(mapGen[EquipmentPrototype,Int],
      mapGen[EquipmentPrototype,Int],
      mapGen[EquipmentPrototype,Int],
      mapGen[EquipmentPrototype,Int],
      mapGen[EquipmentPrototype,Int],
      mapGen[EquipmentPrototype,Int],
      mapGen[EquipmentPrototype,Int])(EquipmentPrototypes.apply)
  )

  implicit lazy val EquipmentPrototypesToXml = new ToXml[EquipmentPrototypes] {
    implicit val psXml = mapToXml[EquipmentPrototype,Int]("item")

    def fromXml (ns: Seq[Node]) =
      ^^^^^^(ns.readTag[DB[EquipmentPrototype]]("ammunition"),
        ns.readTag[DB[EquipmentPrototype]]("armor"),
        ns.readTag[DB[EquipmentPrototype]]("articles"),
        ns.readTag[DB[EquipmentPrototype]]("meleeWeapons"),
        ns.readTag[DB[EquipmentPrototype]]("rangedWeapons"),
        ns.readTag[DB[EquipmentPrototype]]("shields"),
        ns.readTag[DB[EquipmentPrototype]]("zoneArmor"))(
          EquipmentPrototypes.apply)

    def toXml (a: EquipmentPrototypes) = 
      ("ammunition" xml a.ammunition) ++
      ("armor" xml a.armor) ++
      ("articles" xml a.articles) ++
      ("meleeWeapons" xml a.meleeWeapons) ++
      ("rangedWeapons" xml a.rangedWeapons) ++
      ("shields" xml a.shields) ++
      ("zoneArmor" xml a.zoneArmor)
  }

  def read (ns: Seq[Node]) = EquipmentPrototypesToXml fromXml ns

  def write (s: EquipmentPrototypes) = EquipmentPrototypesToXml toXml s

  val ammunition: EquipmentPrototypes @> DB[EquipmentPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(ammunition = b), _.ammunition)

  val armor: EquipmentPrototypes @> DB[EquipmentPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(armor = b), _.armor)

  val articles: EquipmentPrototypes @> DB[EquipmentPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(articles = b), _.articles)

  val meleeWeapons: EquipmentPrototypes @> DB[EquipmentPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(meleeWeapons = b), _.meleeWeapons)

  val rangedWeapons: EquipmentPrototypes @> DB[EquipmentPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(rangedWeapons = b), _.rangedWeapons)

  val shields: EquipmentPrototypes @> DB[EquipmentPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(shields = b), _.shields)

  val zoneArmor: EquipmentPrototypes @> DB[EquipmentPrototype] =
    Lens.lensu((a,b) ⇒ a.copy(zoneArmor = b), _.zoneArmor)
}

// vim: set ts=2 sw=2 et:
