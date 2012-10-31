package efa.dsa.abilities.services.ui

import efa.dsa.abilities.RangedTalentItem
import efa.dsa.world.{RaisingCost, Ebe}
import efa.core.Efa._
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class RangedPanel (p: ItemPair[RangedTalentItem]) extends AbilityPanel(p) {

  def in = ^(dataIn, textIn[Ebe] (ebeC), comboBox (rcC), checkBox (btC))(
      RangedTalentItem.apply)

  val ebeC = numField (item.ebe)
  val rcC = comboBox (item.raisingCost, RaisingCost.values)
  val btC = checkBox (item.isBaseTalent)
  protected def lbls = List (il.ebe, il.raisingCost, il.baseTalent)
  protected def fields = List (ebeC, rcC, btC)
}

object RangedPanel {
  def apply (p: ItemPair[RangedTalentItem]) = IO(new RangedPanel(p))
}

// vim: set ts=2 sw=2 et:
