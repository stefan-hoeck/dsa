package efa.dsa.equipment.services

import efa.dsa.equipment._
import efa.dsa.equipment.services.ui._
import efa.react.{SIn, sTrans}
import efa.rpg.items.controller.{ControllerFactory, ItemsInfo}
import efa.rpg.items.spi.ItemsInfoProvider
import scalaz._, Scalaz._, effect.IO

private[services] object EquipmentController extends ControllerFactory {
  val ammunitionNames = ("Geschosse", "dsa_ammunition_item")
  val armorNames = ("Ruestungen", "dsa_armor_item")
  val articleNames = ("Gegenstaende", "dsa_equipment_item")
  val meleeNames = ("Nahkampfwaffen", "dsa_meleeweapon_item")
  val rangedNames = ("Fernkampfwaffen", "dsa_rangedweapon_item")
  val shieldNames = ("Schilde", "dsa_shield_item")
  val zoneArmorNames = ("Zonenruestungen", "dsa_zonearmor_item")
  val ammunitionC = cached[AmmunitionItem](ammunitionNames)
  val armorC = cached[ArmorItem](armorNames)
  val articleC = cached[ArticleItem](articleNames)
  val meleeC = cached[MeleeWeaponItem](meleeNames)
  val rangedC = cached[RangedWeaponItem](rangedNames)
  val shieldC = cached[ShieldItem](shieldNames)
  val zoneArmorC = cached[ZoneArmorItem](zoneArmorNames)

  lazy val infos: Map[String, IO[ItemsInfo]] = Map (
    ammunitionNames._1 → ammunitionC.map(_.info).get,
    armorNames._1 → armorC.map(_.info).get,
    articleNames._1 → articleC.map(_.info).get,
    meleeNames._1 → meleeC.map(_.info).get,
    rangedNames._1 → rangedC.map(_.info).get,
    shieldNames._1 → shieldC.map(_.info).get,
    zoneArmorNames._1 → zoneArmorC.map(_.info).get
  )

  lazy val equipments: SIn[EquipmentItems] = {
    val cachedEquipments = signal(ammunitionC) ⊛
      signal(armorC) ⊛
      signal(articleC) ⊛
      signal(meleeC) ⊛
      signal(rangedC) ⊛
      signal(shieldC) ⊛
      signal(zoneArmorC) apply EquipmentItems.apply

    sTrans inIO (cachedEquipments.get >>= (_.go map (_._2)))
  }
}

class EquipmentInfoProvider extends ItemsInfoProvider {
  def infos = EquipmentController.infos
}

// vim: set ts=2 sw=2 et:
