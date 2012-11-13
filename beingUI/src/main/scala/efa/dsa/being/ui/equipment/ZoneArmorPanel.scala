package efa.dsa.being.ui.equipment

import efa.core.Read
import efa.dsa.being.equipment._
import efa.dsa.equipment.ZoneRs
import efa.dsa.world.Be
import efa.nb.VSIn
import efa.rpg.core.DieRoller
import scalaz._, Scalaz._, effect.IO

class ZoneArmorPanel (a: ZoneArmor) extends EquipmentPanel(a) {
  val rsC = numField(ZoneRs shows a.data.rs)
  val beC = numField(a.data.be)
  val equippedC = checkBox(a.data.equipped)

  def lbls = List(el.rs, el.be, bl.equipped)
  def fields = List(rsC, beC, equippedC)

  def in: VSIn[ZoneArmorData] = ^^^^(
    eqIn,
    parentIn,
    textIn[ZoneRs](rsC)(Read readV ZoneRs.read),
    intIn(beC, Be.validate),
    checkBox(equippedC)
  )(ZoneArmorData.apply)
}

object ZoneArmorPanel {
  def create (a: ZoneArmor): IO[ZoneArmorPanel] = for {
    p ← IO(new ZoneArmorPanel(a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
