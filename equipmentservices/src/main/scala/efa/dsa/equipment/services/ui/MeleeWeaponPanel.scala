package efa.dsa.equipment.services.ui

import efa.core.Read
import efa.dsa.equipment.{MeleeWeaponItem, Bf, Ini, Length}
import efa.dsa.world.{DistanceClass, Wm, TpKk}
import efa.dsa.world.mittelreich.Distance
import efa.nb.VSIn
import efa.rpg.core.{DieRoller, UnitEnum}
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class MeleeWeaponPanel(p: ItemPair[MeleeWeaponItem])
   extends EquipmentLikePanel(p) {
  import MeleeWeaponPanel._

  def in = Apply[VSIn].apply11(
    eqIn,
    textIn[DieRoller](tpC),
    textIn[TpKk](tpkkC),
    checkBox(improvisedC),
    stringIn(talentC),
    intIn(bfC, Bf.validate),
    checkBox(twohandedC),
    textIn[Wm](wmC),
    intIn(iniC, Ini.validate),
    comboBox(dkC),
    textIn(lengthC, Length.validate)(lengthRead)
  )(MeleeWeaponItem.apply)

  val tpC = numField(item.tp)
  val tpkkC = numField(item.tpkk)
  val improvisedC = checkBox(item.improvised)
  val talentC = textField(item.talent)
  val bfC = numField(item.bf)
  val twohandedC = checkBox(item.twoHanded)
  val wmC = numField(item.wm)
  val iniC = numField(item.ini)
  val dkC = comboBox(item.dk, DistanceClass.values)
  val lengthC = numField(item.length, ued showPretty (Distance.S, 2))
  protected def lbls = List(el.tp, el.tpkk, el.improvised, el.talent,
    el.bf, el.twoHanded, el.wm, el.ini, el.dk, el.length)
  protected def fields = List(tpC, tpkkC, improvisedC, talentC, bfC,
    twohandedC, wmC, iniC, dkC, lengthC)
}

object MeleeWeaponPanel {
  def apply(p: ItemPair[MeleeWeaponItem]) = IO(new MeleeWeaponPanel(p))

  def ued = UnitEnum[Distance]
  val lengthRead = Read readV (ued readPretty Distance.S)
}

// vim: set ts=2 sw=2 et:
