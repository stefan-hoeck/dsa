package efa.dsa.being.ui.equipment

import efa.dsa.being.equipment._
import efa.nb.VSIn
import efa.rpg.core.DieRoller
import scalaz._, Scalaz._, effect.IO

class AmmunitionPanel (a: Ammunition) extends EquipmentPanel(a) {
  val countC = numField(a.data.count)
  val tpC = numField(a.data.tp)

  def lbls = List(el.tp, bl.count)
  def fields = List(tpC, countC)

  def in: VSIn[AmmunitionData] = ^^^(
    eqIn,
    parentIn,
    textIn[DieRoller](tpC),
    intIn(countC, Count.validate)
  )(AmmunitionData.apply)
}

object AmmunitionPanel {
  def create (a: Ammunition): IO[AmmunitionPanel] = for {
    p ← IO(new AmmunitionPanel(a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
