package efa.dsa.abilities.services.ui

import efa.dsa.abilities.{Attributes, SpellItem}
import efa.dsa.world.RaisingCost
import efa.core.Efa._
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class SpellPanel(p: ItemPair[SpellItem]) extends AbilityPanel(p) {
  def in = ^(dataIn, comboBox (rcC), attrC.in)(SpellItem.apply)

  val attrC = new AttributesPanel(item.attributes)
  val rcC = comboBox (item.raisingCost, RaisingCost.values)
  protected def lbls = List (il.raisingCost, il.attributes)
  protected def fields = List (rcC, attrC)
}

object SpellPanel {
  def apply (p: ItemPair[SpellItem]) = IO(new SpellPanel(p))
}

// vim: set ts=2 sw=2 et:
