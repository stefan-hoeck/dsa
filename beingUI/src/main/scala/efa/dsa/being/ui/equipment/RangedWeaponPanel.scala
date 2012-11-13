package efa.dsa.being.ui.equipment

import efa.core.Read
import efa.dsa.being.equipment._
import efa.dsa.equipment._
import efa.dsa.world.{TpKk, Wm}
import efa.nb.VSIn
import efa.rpg.core.DieRoller
import scalaz._, Scalaz._, effect.IO

class RangedWeaponPanel (a: RangedWeapon) extends EquipmentPanel(a) {
  val tpC = numField(a.data.tp)
  val talentC = textField(a.data.talent)
  val tpkkC = numField(a.data.tpkk)
  val reachC = numField(Reach shows a.data.reach)
  val tpPlusC = numField(TpPlus shows a.data.tpPlus)
  val ttlC = numField(a.data.timeToLoad)

  def lbls = List(el.tp, el.talent, el.tpkk, el.reach,
                  el.tpPlus, el.timeToLoad)
  def fields = List(tpC, talentC, tpkkC, reachC, tpPlusC, ttlC)

  def in: VSIn[RangedWeaponData] = Apply[VSIn].apply8(
    eqIn,
    parentIn,
    textIn[DieRoller](tpC),
    stringIn(talentC),
    textIn[TpKk](tpkkC),
    textIn[Reach](reachC)(Read readV Reach.read),
    textIn[TpPlus](tpPlusC)(Read readV TpPlus.read),
    intIn(ttlC, Ttl.validate)
  )(RangedWeaponData.apply)
}

object RangedWeaponPanel {
  def create (a: RangedWeapon): IO[RangedWeaponPanel] = for {
    p ← IO(new RangedWeaponPanel(a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
