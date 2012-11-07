package efa.dsa.abilities.services.ui

import efa.dsa.abilities.{TalentItem,TalentType,Attributes}
import efa.dsa.world.{RaisingCost,Ebe}
import efa.core.Efa._
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO


class TalentPanel(p: ItemPair[TalentItem]) extends AbilityPanel(p) {

  def in = 
    ^^^^^(dataIn, 
      attrC.in, 
      textIn[Ebe] (ebeC),
      comboBox (rcC),
      checkBox (btC),
      comboBox (ttC))(TalentItem.apply)

  val rcC = comboBox (item.raisingCost, RaisingCost.values)
  val attrC = new AttributesPanel(item.attributes)
  val ebeC = numField (item.ebe)
  val btC = checkBox (item.isBaseTalent)
  val ttC = comboBox (item.talentType, TalentType.values)

  protected def lbls = List (il.raisingCost, il.attributes, il.ebe,
    il.talentType,il.baseTalent)
  protected def fields = List (rcC, attrC, ebeC, ttC, btC)
}

object TalentPanel {
  def apply (p: ItemPair[TalentItem]) = IO(new TalentPanel(p))
}

// vim: set ts=2 sw=2 et:
