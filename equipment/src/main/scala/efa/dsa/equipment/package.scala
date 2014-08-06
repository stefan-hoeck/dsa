package efa.dsa

import efa.core._, Efa._
import efa.dsa.equipment.spi._
import efa.dsa.world.{BodyPart, RangedDistance, Rs, EquipmentMaps}
import efa.dsa.world.mittelreich.Distance
import efa.rpg.core.{RangeVals, DieRoller, EnumMaps, EnumMap}

package object equipment extends RangeVals {
  lazy val loc = Service.unique[EquipmentLocal]

  val Addition = xmlInfo[Boolean](Validators.dummy, "addition")
  val Bf = fullInfo (-99, 99, "bf")
  val Ini = fullInfo (-99, 99, "ini")
  val Length = fullInfo (0L, 1000L * Distance.S.multiplier, "length")
  val Price = fullInfo (0L, 1000000000000000L, "price")
  val Reach = EnumMaps.int[RangedDistance](0, Int.MaxValue, 0, "reach")
  val Talent = xmlInfo[String](Validators.dummy, "talent")
  val Tp = xmlInfo[DieRoller](Validators.dummy, "tp")
  val TpPlus = EnumMaps.int[RangedDistance](-99, 99, 0, "tpplus")
  val Ttl = fullInfo (0, 999, "timetoload")
  val Weight = fullInfo (0L, 1000000000000000L, "weight")
  val ZoneRs = EnumMaps.int[BodyPart](Rs.min, Rs.max, 0, "zoners")

  type ArticleItem = EquipmentItemData
  type Reach = EnumMap[RangedDistance, Int]
  type TpPlus = EnumMap[RangedDistance, Int]
  type ZoneRs = EnumMap[BodyPart, Int]

  type EquipmentItems = EquipmentMaps[AmmunitionItem, ArmorItem,
                                      ArticleItem, MeleeWeaponItem,
                                      RangedWeaponItem, ShieldItem,
                                      ZoneArmorItem]
}

// vim: set ts=2 sw=2 et:
