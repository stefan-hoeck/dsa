package efa.dsa.equipment.services.ui

import efa.dsa.equipment.{ShieldItem, Bf, Ini}
import efa.dsa.world.{ShieldSize, ShieldType, Wm}
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class ShieldPanel(p: ItemPair[ShieldItem])
   extends EquipmentLikePanel(p) {
  def in = 
    ^(eqIn, 
      comboBox(sizeC),
      comboBox(typeC),
      intIn(iniC, Ini.validate),
      intIn(bfC, Bf.validate),
      textIn[Wm](wmC))(ShieldItem.apply)

  val sizeC = comboBox(item.size, ShieldSize.values)
  val typeC = comboBox(item.shieldType, ShieldType.values)
  val iniC = numField(item.ini)
  val bfC = numField(item.bf)
  val wmC = numField(item.wm)
  protected def lbls = List(el.size, el.shieldType, el.ini, el.bf, el.wm)
  protected def fields = List(sizeC, typeC, iniC, bfC, wmC)
}

object ShieldPanel {
  def apply(p: ItemPair[ShieldItem]) = IO(new ShieldPanel(p))
}

// vim: set ts=2 sw=2 et:
