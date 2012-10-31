package efa.dsa.abilities.services.ui

import efa.dsa.abilities.{ScriptureItem, Complexity}
import efa.dsa.world.RaisingCost
import efa.core.Efa._
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class ScripturePanel(p: ItemPair[ScriptureItem]) extends AbilityPanel(p) {

  def in = 
    ^(dataIn, 
      comboBox (rcC),
      intIn (complexityC, Complexity.validate))(ScriptureItem.apply)

  val rcC = comboBox (item.raisingCost, RaisingCost.values)
  val complexityC = numField (item.complexity)
  protected def lbls = List (il.raisingCost, il.complexity)
  protected def fields = List (rcC, complexityC)
}

object ScripturePanel {
  def apply (p: ItemPair[ScriptureItem]) = IO(new ScripturePanel(p))
}

// vim: set ts=2 sw=2 et:
