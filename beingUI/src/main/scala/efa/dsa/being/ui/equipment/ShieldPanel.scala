package efa.dsa.being.ui.equipment

import efa.dsa.being.equipment._
import efa.dsa.equipment._
import efa.dsa.world.{TpKk, Wm}
import efa.nb.VSIn
import efa.rpg.core.DieRoller
import scalaz._, Scalaz._, effect.IO

class ShieldPanel (a: Shield) extends EquipmentPanel(a) {
  val iniC = numField(a.data.ini)
  val bfC = numField(a.data.bf)
  val wmC = numField(a.data.wm)

  def lbls = List(el.ini, el.bf, el.wm)
  def fields = List(iniC, bfC, wmC)

  def in: VSIn[ShieldData] = Apply[VSIn].apply5(
    eqIn,
    parentIn,
    intIn(iniC, Ini.validate),
    intIn(bfC, Bf.validate),
    textIn[Wm](wmC)
  )(ShieldData.apply)
}

object ShieldPanel {
  def create (a: Shield): IO[ShieldPanel] = for {
    p ← IO(new ShieldPanel(a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
