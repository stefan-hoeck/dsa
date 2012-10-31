package efa.dsa.equipment

import efa.rpg.core.{DB, DBs}
import scalaz.{Equal, Lens, @>}

case class EquipmentItems (
  ammunition: DB[AmmunitionItem],
  armor: DB[ArmorItem],
  articles: DB[ArticleItem],
  meleeWeapons: DB[MeleeWeaponItem],
  rangedWeapons: DB[RangedWeaponItem],
  shields: DB[ShieldItem],
  zoneArmor: DB[ZoneArmorItem]
)

object EquipmentItems extends DBs {
  implicit val EquipmentItemsEqual:Equal[EquipmentItems] = Equal.equalA

  lazy val default = EquipmentItems(db, db, db, db, db, db, db)

  val ammunition: EquipmentItems @> DB[AmmunitionItem] =
    Lens.lensu((a,b) ⇒ a copy (ammunition = b), _.ammunition)

  val armor: EquipmentItems @> DB[ArmorItem] =
    Lens.lensu((a,b) ⇒ a copy (armor = b), _.armor)

  val articles: EquipmentItems @> DB[ArticleItem] =
    Lens.lensu((a,b) ⇒ a copy (articles = b), _.articles)

  val meleeWeapons: EquipmentItems @> DB[MeleeWeaponItem] =
    Lens.lensu((a,b) ⇒ a copy (meleeWeapons = b), _.meleeWeapons)

  val rangedWeapons: EquipmentItems @> DB[RangedWeaponItem] =
    Lens.lensu((a,b) ⇒ a copy (rangedWeapons = b), _.rangedWeapons)

  val shields: EquipmentItems @> DB[ShieldItem] =
    Lens.lensu((a,b) ⇒ a copy (shields = b), _.shields)

  val zoneArmor: EquipmentItems @> DB[ZoneArmorItem] =
    Lens.lensu((a,b) ⇒ a copy (zoneArmor = b), _.zoneArmor)
}

// vim: set ts=2 sw=2 et:
