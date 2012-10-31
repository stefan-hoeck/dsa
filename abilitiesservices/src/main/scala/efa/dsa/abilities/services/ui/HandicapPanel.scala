package efa.dsa.abilities.services.ui

import efa.dsa.abilities.{HandicapItem,HandicapGp}
import efa.core.Efa._
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class HandicapPanel (p: ItemPair[HandicapItem]) extends AbilityPanel(p){
  val gpC = numField (item.gp) 

  def in = ^(dataIn, textIn[HandicapGp] (gpC))(HandicapItem.apply)

  protected def lbls = List (il.gp)
  protected def fields = List (gpC)
}

object HandicapPanel {
  def apply (p: ItemPair[HandicapItem]) = IO(new HandicapPanel(p))
}

// vim: set ts=2 sw=2 et:
