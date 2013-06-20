package efa.dsa.being.ui.humanoid

//import efa.dsa.being._, efa.dsa.being.{loc ⇒ bLoc}
//import efa.dsa.world.BodyPart
//import efa.nb.VSET
//import efa.react.swing.GbPanel
//import efa.react.swing.radioButton.values
//import efa.rpg.being.BeingPanel
//import efa.rpg.core.RpgEnum
//import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
//import scala.swing.{RadioButton, TextField, ButtonGroup}
//import scalaz._, Scalaz._
//
//class ZoneRsPanel[A:AsHumanoid] extends BeingPanel[A,HumanoidData] {
//  import ZoneRsPanel._, AsHumanoid._
// 
//  val panels = RpgEnum[BodyPart].values map panel
//
//  panels foldMap panelToElem add ()
//
//  lazy val set = panels foldMap panelToSet
//
//  private def panel (bp: BodyPart): ZonePanel = {
//    val radios = (0 to zoneWoundsMax) map (i ⇒ new RadioButton(i.toString))
//
//    (bp, radios.toList, numberDisabled, new ButtonGroup(radios: _*))
//  }
//
//  private def panelToElem (p: ZonePanel): Elem @@ Horizontal = 
//    new GbPanel {
//      (bLoc.shortRs beside p._3) above bLoc.wounds above
//      (p._2 foldMap (_ fillH 2: Elem)) add()
//       
//      border = titledBorder(p._1.loc.locName)
//    } horizontal
//
//  private def panelToSet (p: ZonePanel): VSET[A,HumanoidData] =
//    (lensed(values(p._2) >=> success)(lens(p._1)) ∙ humanoidData[A]) ⊹
//    modifiedProp(zoneRsKeyFor(p._1))(p._3)
//}
//
//object ZoneRsPanel {
//  type ZonePanel = (BodyPart, List[RadioButton], TextField, ButtonGroup)
//
//  def lens (bp: BodyPart): HumanoidData @> Int =
//    HumanoidData.zoneWounds at bp
//}

// vim: set ts=2 sw=2 et:
