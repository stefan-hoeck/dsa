package efa.dsa.equipment.services.ui

import efa.dsa.equipment.AmmunitionItem
import efa.rpg.core.DieRoller
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class AmmunitionPanel(p: ItemPair[AmmunitionItem])
   extends EquipmentLikePanel(p) {
  def in = ^(eqIn, textIn[DieRoller](tpC))(AmmunitionItem.apply)

  val tpC = numField(item.tp) 
  protected def lbls = List (el.tp)
  protected def fields = List (tpC)
}

object AmmunitionPanel {
  def apply(p: ItemPair[AmmunitionItem]) = IO(new AmmunitionPanel(p))
}

// vim: set ts=2 sw=2 et:
