package efa.dsa.being

import efa.core.{Validators, Efa}, Efa._
import efa.dsa.equipment._
import efa.dsa.world.EquipmentMaps
import efa.rpg.core.RangeVals

package object equipment extends RangeVals {
  def Count = efa.dsa.generation.Count
  val Equipped = xmlInfo[Boolean](Validators.dummy, "equipped")

  type Ammunition = Equipment[AmmunitionItem,AmmunitionData]

  type Armor = Equipment[ArmorItem,ArmorData]

  type Article = Equipment[ArticleItem,ArticleData]

  type MeleeWeapon = Equipment[MeleeWeaponItem,MeleeWeaponData]

  type RangedWeapon = Equipment[RangedWeaponItem,RangedWeaponData]

  type Shield = Equipment[ShieldItem,ShieldData]

  type ZoneArmor = Equipment[ZoneArmorItem,ZoneArmorData]

  type EquipmentDatas = EquipmentMaps[AmmunitionData, ArmorData,
                                      ArticleData, MeleeWeaponData,
                                      RangedWeaponData, ShieldData,
                                      ZoneArmorData]

  type Equipments = EquipmentMaps[Ammunition, Armor, Article, MeleeWeapon,
                                  RangedWeapon, Shield, ZoneArmor]
}

// vim: set ts=2 sw=2 et:
