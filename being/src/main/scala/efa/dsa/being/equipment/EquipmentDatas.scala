package efa.dsa.being.equipment

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.{DB, DBs, Util}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class EquipmentDatas(
  ammunition: DB[AmmunitionData],
  armor: DB[ArmorData],
  articles: DB[ArticleData],
  meleeWeapons: DB[MeleeWeaponData],
  rangedWeapons: DB[RangedWeaponData],
  shields: DB[ShieldData],
  zoneArmor: DB[ZoneArmorData]
)

object EquipmentDatas extends DBs with Util {
  private def empty[A]: DB[A] = Map.empty

  lazy val default = EquipmentDatas(db, db, db, db, db, db, db)

  implicit lazy val EquipmentDatasDefault = Default default default

  implicit lazy val EquipmentDatasEqual = Equal.equalA[EquipmentDatas]

  implicit lazy val EquipmentDatasArbitrary = Arbitrary(
    ^^^^^^(a[DB[AmmunitionData]],
      a[DB[ArmorData]],
      a[DB[ArticleData]],
      a[DB[MeleeWeaponData]],
      a[DB[RangedWeaponData]],
      a[DB[ShieldData]],
      a[DB[ZoneArmorData]])(EquipmentDatas.apply)
  )

  implicit lazy val EquipmentDatasToXml = new ToXml[EquipmentDatas] {
    implicit val ammoXml = dbToXml[AmmunitionData]("dsa_ammunition")
    implicit val armorXml = dbToXml[ArmorData]("dsa_armor")
    implicit val articleXml = dbToXml[ArticleData]("dsa_article")
    implicit val meleeXml = dbToXml[MeleeWeaponData]("dsa_meleeWeapon")
    implicit val rangedXml = dbToXml[RangedWeaponData]("dsa_rangedWeapon")
    implicit val shieldXml = dbToXml[ShieldData]("dsa_shield")
    implicit val zoneArmorXml = dbToXml[ZoneArmorData]("dsa_zoneArmor")

    def fromXml (ns: Seq[Node]) =
      ^^^^^^(ns.readTag[DB[AmmunitionData]]("ammo"),
        ns.readTag[DB[ArmorData]]("armor"),
        ns.readTag[DB[ArticleData]]("articles"),
        ns.readTag[DB[MeleeWeaponData]]("meleeWeapons"),
        ns.readTag[DB[RangedWeaponData]]("rangedWeapons"),
        ns.readTag[DB[ShieldData]]("shields"),
        ns.readTag[DB[ZoneArmorData]]("zoneArmor"))(EquipmentDatas.apply)

    def toXml (a: EquipmentDatas) = 
      ("ammo" xml a.ammunition) ++
      ("armor" xml a.armor) ++
      ("articles" xml a.articles) ++
      ("meleeWeapons" xml a.meleeWeapons) ++
      ("rangedWeapons" xml a.rangedWeapons) ++
      ("shields" xml a.shields) ++
      ("zoneArmor" xml a.zoneArmor)
  }

  def read (ns: Seq[Node]) = EquipmentDatasToXml fromXml ns

  def write (h: EquipmentDatas) = EquipmentDatasToXml toXml h

  val ammunition: EquipmentDatas @> DB[AmmunitionData] =
    Lens.lensu((a,b) ⇒ a.copy(ammunition = b), _.ammunition)

  val armor: EquipmentDatas @> DB[ArmorData] =
    Lens.lensu((a,b) ⇒ a.copy(armor = b), _.armor)

  val articles: EquipmentDatas @> DB[ArticleData] =
    Lens.lensu((a,b) ⇒ a.copy(articles = b), _.articles)

  val meleeWeapons: EquipmentDatas @> DB[MeleeWeaponData] =
    Lens.lensu((a,b) ⇒ a.copy(meleeWeapons = b), _.meleeWeapons)

  val rangedWeapons: EquipmentDatas @> DB[RangedWeaponData] =
    Lens.lensu((a,b) ⇒ a.copy(rangedWeapons = b), _.rangedWeapons)

  val shields: EquipmentDatas @> DB[ShieldData] =
    Lens.lensu((a,b) ⇒ a.copy(shields = b), _.shields)

  val zoneArmor: EquipmentDatas @> DB[ZoneArmorData] =
    Lens.lensu((a,b) ⇒ a.copy(zoneArmor = b), _.zoneArmor)
}

// vim: set ts=2 sw=2 et:
