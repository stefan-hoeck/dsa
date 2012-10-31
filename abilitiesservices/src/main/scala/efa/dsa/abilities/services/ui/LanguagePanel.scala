package efa.dsa.abilities.services.ui

import efa.dsa.abilities.{LanguageItem, Complexity}
import efa.dsa.world.RaisingCost
import efa.core.Efa._
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class LanguagePanel(p: ItemPair[LanguageItem]) extends AbilityPanel (p) {
  def in = 
    ^(dataIn,
      comboBox (rcC),
      intIn (complexityC, Complexity.validate),
      stringIn(scriptureC),
      stringIn (familyC))(LanguageItem.apply)

  val rcC = comboBox (item.raisingCost, RaisingCost.values)
  val complexityC = numField (item.complexity)
  val familyC = textField (item.family)
  val scriptureC = textField (item.scripture)

  protected def lbls =
    List (il.raisingCost, il.complexity, il.family, il.scripture)
  protected def fields = List (rcC, complexityC, familyC, scriptureC)
}

object LanguagePanel {
  def apply (p: ItemPair[LanguageItem]) = IO(new LanguagePanel(p))
}

// vim: set ts=2 sw=2 et:
