package efa.dsa.being.ui.equipment

import efa.dsa.being.equipment._
import efa.dsa.world.{Rs, Be}
import efa.nb.VSIn
import efa.rpg.core.DieRoller
import scalaz._, Scalaz._, effect.IO

class ArmorPanel (a: Armor) extends EquipmentPanel(a) {
  val rsC = numField(a.data.rs)
  val beC = numField(a.data.be)
  val equippedC = checkBox(a.data.equipped)

  def lbls = List(el.rs, el.be, bl.equipped)
  def fields = List(rsC, beC, equippedC)

  def in: VSIn[ArmorData] = ^^^^(
    eqIn,
    parentIn,
    intIn(rsC, Rs.validate),
    intIn(beC, Be.validate),
    checkBox(equippedC)
  )(ArmorData.apply)
}

object ArmorPanel {
  def create (a: Armor): IO[ArmorPanel] = for {
    p ← IO(new ArmorPanel(a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
