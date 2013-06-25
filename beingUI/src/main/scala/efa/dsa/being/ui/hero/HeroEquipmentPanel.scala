package efa.dsa.being.ui.hero

import dire.swing._, Swing._
import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being.{loc ⇒ bLoc, _}, equipment._
import efa.dsa.being.ui._
import efa.dsa.being.ui.equipment.{EquipmentNodes ⇒ EN}
import efa.dsa.world.mittelreich.Weight
import efa.nb.tc.AsTc
import efa.rpg.core.{UnitEnum, HasModifiers}
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

final class HeroEquipmentPanel private(
  val ep: NP[Equipments,EquipmentDatas],
  val panel: Panel
)

object HeroEquipmentPanel {
  def apply(): IO[BeingPanel[Hero,HeroData,HeroEquipmentPanel]] = for {
    ep ← NodePanel(EN.allOut, eqLoc)
    cp ← carry
    p  ← cp ^^ ep.fillV(1) panel

    _  ← ep title loc.equipment
    _  ← cp title loc.load

    sf = cp.sf ⊹ ((ep.sf >=> mapSt(eqL)) ∙ Hero.equipment.get)
  } yield BeingPanel(new HeroEquipmentPanel(ep, p), sf)

  private val eqId = "DSA_AllEquipment_NodePanel"
  private val eqLoc = List(bLoc.countLoc, loc.priceLoc, loc.weightLoc)
  private val prettyWeight = UnitEnum[Weight].showPretty(Weight.ST, 3)
  private val eqL = HeroData.humanoid.equipment

  implicit val Tc: AsTc[HeroEquipmentPanel] = new BeingTc[HeroEquipmentPanel](
    loc.equipmentPanel, "DSA_HeroEquipmentPanel", _.panel){
      override def lookup(h: HeroEquipmentPanel) = h.ep.p.lookup
      override def readProps(h: HeroEquipmentPanel) = readNp(h.ep, eqId)
      override def writeProps(h: HeroEquipmentPanel) = writeNp(h.ep, eqId)
    }

  private def carry: IO[BP[Hero,HeroData]] = for {
    capacity ← disabledNumeric
    weight   ← disabledNumeric

    p        ← bLoc.carryingCapacity <> capacity <>
               bLoc.carriedWeight <> weight panel
  } yield BeingPanel(p, carrySf(capacity, weight))

  private def carrySf(c: TextField, w: TextField) =
    modifiedProp[Hero,HeroData,TextField](CarryingCapacityKey, c) ⊹
    modifiedProp(WeightKey, w, prettyWeight)
}

// vim: set ts=2 sw=2 et:
