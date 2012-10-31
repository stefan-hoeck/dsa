package efa.dsa.abilities.services.ui

import efa.dsa.abilities.RitualItem
import efa.dsa.world.{RaisingCost, Ebe}
import efa.core.Efa._
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class RitualPanel(p: ItemPair[RitualItem]) extends AbilityPanel(p) {
  def in = ^(dataIn, comboBox (rcC))(RitualItem.apply)

  val rcC = comboBox (item.raisingCost, RaisingCost.values)
  protected def lbls = List (il.raisingCost)
  protected def fields = List (rcC)
}

object RitualPanel {
  def apply (p: ItemPair[RitualItem]) = IO(new RitualPanel(p))
}

// vim: set ts=2 sw=2 et:
