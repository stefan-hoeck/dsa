package efa.dsa.equipment.services.ui

import efa.core.Read.readV
import efa.dsa.equipment._
import efa.dsa.world.TpKk
import efa.dsa.world.mittelreich.Distance
import efa.nb.VSIn
import efa.rpg.core.DieRoller
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class RangedWeaponPanel(p: ItemPair[RangedWeaponItem])
   extends EquipmentLikePanel(p) {

  def in = Apply[VSIn].apply10 (
    eqIn,
    textIn[DieRoller](tpC),
    textIn[TpKk](tpkkC),
    checkBox(improvisedC),
    stringIn(talentC),
    textIn[Reach](reachC)(readV(Reach.read)),
    textIn[TpPlus](tpPlusC)(readV(TpPlus.read)),
    checkBox(makesWoundC),
    intIn(ttlC, Ttl.validate),
    checkBox(usesAmmoC)
  )(RangedWeaponItem.apply)

  val tpC = numField(item.tp)
  val tpkkC = numField(item.tpkk)
  val improvisedC = checkBox(item.improvised)
  val talentC = textField(item.talent)
  val reachC = textField(Reach shows item.reach)
  val tpPlusC = textField(TpPlus shows item.tpPlus)
  val makesWoundC = checkBox(item.makesWound)
  val ttlC = numField(item.timeToLoad)
  val usesAmmoC = checkBox(item.usesAmmo)

  protected def lbls = List(el.tp, el.tpkk, el.improvised, el.talent,
    el.reach, el.tpPlus, el.makesWound, el.timeToLoad, el.usesAmmo)
  protected def fields = List(tpC, tpkkC, improvisedC, talentC, reachC,
    tpPlusC, makesWoundC, ttlC, usesAmmoC)
}

object RangedWeaponPanel {
  def apply(p: ItemPair[RangedWeaponItem]) = IO(new RangedWeaponPanel(p))
}

// vim: set ts=2 sw=2 et:
