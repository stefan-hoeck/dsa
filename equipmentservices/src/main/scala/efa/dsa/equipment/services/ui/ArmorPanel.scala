package efa.dsa.equipment.services.ui

import efa.dsa.world.{Be, Rs}
import efa.dsa.equipment.ArmorItem
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class ArmorPanel(p: ItemPair[ArmorItem]) extends EquipmentLikePanel(p) {
  def in =
    ^^^(eqIn, 
      intIn(rsC, Rs.validate),
      intIn(beC, Be.validate),
      checkBox(additionC))(ArmorItem.apply)

  val rsC = numField(item.rs) 
  val beC = numField(item.be) 
  val additionC = checkBox(item.isAddition)

  protected def lbls = List (el.rs, el.be, el.isAddition)
  protected def fields = List (rsC, beC, additionC)
}

object ArmorPanel {
  def apply(p: ItemPair[ArmorItem]) = IO(new ArmorPanel(p))
}

// vim: set ts=2 sw=2 et:
