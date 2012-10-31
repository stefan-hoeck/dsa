package efa.dsa.abilities.services.ui

import efa.dsa.abilities.{FeatItem, Ap}
import efa.core.Efa._
import efa.rpg.items.ItemPair
import efa.nb.VSIn
import scalaz._, Scalaz._, effect.IO

class FeatPanel (a: ItemPair[FeatItem]) extends AbilityPanel(a) {
  val apC = numField (item.ap) 

  def in =
    ^(dataIn, intIn (apC, Ap.validate), item.featType.η[VSIn])(FeatItem.apply)

  protected def lbls = List (il.ap)
  protected def fields = List (apC)
}

object FeatPanel {
  def apply (p: ItemPair[FeatItem]) = IO(new FeatPanel(p))
}

// vim: set ts=2 sw=2 et:
