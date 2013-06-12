package efa.dsa.equipment.services

import dire.SIn
import efa.dsa.equipment._
import efa.dsa.world.EquipmentMaps
import efa.rpg.items.controller.{Factory, ItemsInfo}
import efa.rpg.items.spi.ItemsInfoProvider
import scalaz._, Scalaz._, effect.IO

private[services] object EquipmentController extends Factory {
  import implicits._

  val ammunitionNames = ("Geschosse", "dsa_ammunition_item")
  val armorNames = ("Ruestungen", "dsa_armor_item")
  val articleNames = ("Gegenstaende", "dsa_equipment_item")
  val meleeNames = ("Nahkampfwaffen", "dsa_meleeweapon_item")
  val rangedNames = ("Fernkampfwaffen", "dsa_rangedweapon_item")
  val shieldNames = ("Schilde", "dsa_shield_item")
  val zoneArmorNames = ("Zonenruestungen", "dsa_zonearmor_item")
  val ammunitionC = singleton[AmmunitionItem](ammunitionNames)
  val armorC = singleton[ArmorItem](armorNames)
  val articleC = singleton[ArticleItem](articleNames)
  val meleeC = singleton[MeleeWeaponItem](meleeNames)
  val rangedC = singleton[RangedWeaponItem](rangedNames)
  val shieldC = singleton[ShieldItem](shieldNames)
  val zoneArmorC = singleton[ZoneArmorItem](zoneArmorNames)

  lazy val infos: Map[String, ItemsInfo] = Map (
    ammunitionNames._1 → ammunitionC.info,
    armorNames._1 → armorC.info,
    articleNames._1 → articleC.info,
    meleeNames._1 → meleeC.info,
    rangedNames._1 → rangedC.info,
    shieldNames._1 → shieldC.info,
    zoneArmorNames._1 → zoneArmorC.info
  )

  lazy val equipment: SIn[EquipmentItems] =
    ammunitionC.dbIn ⊛
    armorC.dbIn ⊛
    articleC.dbIn ⊛
    meleeC.dbIn ⊛
    rangedC.dbIn ⊛
    shieldC.dbIn ⊛
    zoneArmorC.dbIn apply EquipmentMaps.apply
}

class EquipmentInfoProvider extends ItemsInfoProvider {
  def infos = EquipmentController.infos
}

class EquipmentProviderImpl
   extends efa.dsa.being.services.spi.EquipmentProvider {
  def equipment = EquipmentController.equipment
}

// vim: set ts=2 sw=2 et:
