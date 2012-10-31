package efa.dsa.equipment.services.ui

import efa.core.Read.readV
import efa.dsa.equipment.{ZoneArmorItem, ZoneRs}
import efa.dsa.world.Be
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class ZoneArmorPanel(p: ItemPair[ZoneArmorItem])
   extends EquipmentLikePanel(p) {

  def in = 
    ^(eqIn, 
      textIn[ZoneRs](rsC)(readV(ZoneRs.read)),
      intIn(beC, Be.validate),
      checkBox(additionC))(ZoneArmorItem.apply)

  val rsC = textField(ZoneRs shows item.rs)
  val beC = numField(item.be)
  val additionC = checkBox(item.isAddition)
  protected def lbls = List(el.rs, el.be, el.isAddition)
  protected def fields = List(rsC, beC, additionC)
}

object ZoneArmorPanel {
  def apply(p: ItemPair[ZoneArmorItem]) = IO(new ZoneArmorPanel(p))
}

// vim: set ts=2 sw=2 et:
