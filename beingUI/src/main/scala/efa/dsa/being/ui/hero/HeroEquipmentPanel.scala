package efa.dsa.being.ui.hero

//import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
//import efa.dsa.being._, equipment._
//import efa.dsa.being.ui.{loc, NodePanel, AttributesPanel}
//import efa.dsa.being.ui.equipment.{EquipmentNodes ⇒ EN}
//import efa.dsa.world.mittelreich.Weight
//import efa.rpg.core.UnitEnum
//import efa.rpg.being.{MVPanel, BeingPanel}
//import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
//import scalaz._, Scalaz._, effect.IO
//
//class HeroEquipmentPanel (equipmentP: NodePanel[Equipments,EquipmentDatas])
//  extends MVPanel[Hero,HeroData] (
//    "DSA_HeroEquipmentPanel", loc.equipmentPanel, efa.dsa.being.ui.version
//  ) {
//
//  val carryP = new BeingPanel[Hero,HeroData] {
//    val capacityC = numberDisabled
//    val weightC = numberDisabled
//
//    bLoc.carryingCapacity beside capacityC beside
//    bLoc.carriedWeight beside weightC add ()
//
//    lazy val set = 
//      modifiedProp(CarryingCapacityKey)(capacityC) ⊹
//      modifiedProp(WeightKey, prettyWeight)(weightC)
//
//    private val prettyWeight = UnitEnum[Weight].showPretty(Weight.ST, 3)
//  }
//
//  equipmentP.border = titledBorder(loc.equipment)
//  carryP.border = titledBorder(loc.load)
//
//  carryP above (equipmentP fillV 1) add()
//
//  def set =
//    (mapSt(equipmentP.set)(HeroData.humanoid.equipment) ∙ 
//      ((_: Hero).equipment)) ⊹
//    carryP.set
//
//  override def persistentChildren = List(equipmentP)
//  override lazy val getLookup = equipmentP.getLookup
//}
//
//object HeroEquipmentPanel {
//  def create: IO[HeroEquipmentPanel] = for {
//    a ← NodePanel(EN.allOut, "DSA_AllEquipment_NodePanel",
//          List(bLoc.countLoc, loc.priceLoc, loc.weightLoc))
//  } yield new HeroEquipmentPanel(a)
//}

// vim: set ts=2 sw=2 et:
