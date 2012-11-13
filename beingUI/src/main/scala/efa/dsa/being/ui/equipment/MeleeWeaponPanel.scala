package efa.dsa.being.ui.equipment

import efa.dsa.being.equipment._
import efa.dsa.equipment._
import efa.dsa.world.{TpKk, Wm}
import efa.nb.VSIn
import efa.rpg.core.DieRoller
import scalaz._, Scalaz._, effect.IO

class MeleeWeaponPanel (a: MeleeWeapon) extends EquipmentPanel(a) {
  val tpC = numField(a.data.tp)
  val talentC = textField(a.data.talent)
  val bfC = numField(a.data.bf)
  val tpkkC = numField(a.data.tpkk)
  val iniC = numField(a.data.ini)
  val wmC = numField(a.data.wm)

  def lbls = List(el.tp, el.talent, el.bf, el.tpkk, el.ini, el.wm)
  def fields = List(tpC, talentC, bfC, tpkkC, iniC, wmC)

  def in: VSIn[MeleeWeaponData] = Apply[VSIn].apply8(
    eqIn,
    parentIn,
    textIn[DieRoller](tpC),
    stringIn(talentC),
    intIn(bfC, Bf.validate),
    textIn[TpKk](tpkkC),
    intIn(iniC, Ini.validate),
    textIn[Wm](wmC)
  )(MeleeWeaponData.apply)
}

object MeleeWeaponPanel {
  def create (a: MeleeWeapon): IO[MeleeWeaponPanel] = for {
    p ← IO(new MeleeWeaponPanel(a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
