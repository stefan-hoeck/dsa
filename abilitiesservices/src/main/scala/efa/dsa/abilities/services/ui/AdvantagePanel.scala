package efa.dsa.abilities.services.ui

import efa.dsa.abilities.{AdvantageItem, Gp}
import efa.core.Efa._
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class AdvantagePanel(p: ItemPair[AdvantageItem]) extends AbilityPanel(p) {

  val gpC = numField (item.gp) 
  def in = ^(dataIn, intIn (gpC, Gp.validate))(AdvantageItem.apply)

  protected def lbls = List (il.gp)
  protected def fields = List (gpC)
}

object AdvantagePanel {
  def apply (p: ItemPair[AdvantageItem]) = IO(new AdvantagePanel(p))
}

// vim: set ts=2 sw=2 et:
