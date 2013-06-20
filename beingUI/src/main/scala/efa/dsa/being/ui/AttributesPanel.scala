package efa.dsa.being.ui

import dire.swing._, Swing._
import efa.dsa.being._
import efa.dsa.world.Attribute
import efa.rpg.being.BeingPanel, BeingPanel._
import efa.rpg.core.{RpgEnum, HasModifiers}
import scalaz._, Scalaz._, effect.IO

object AttributesPanel {
  def apply[A:HasModifiers,B](): IO[BeingPanel[A,B,Panel]] = for {
  }
}
//
//class AttributesPanel[A:HasModifiers,B] extends BeingPanel[A,B] {
//  import AttributesPanel._
//
//  val pnls = Attribute.values map ((_, numberDisabled))
//
//  pnls foldMap panelElem add()
//
//  def set = pnls foldMap panelSet
//
//  private def panelElem (p: Panels): Elem @@ Horizontal =
//    p._1.loc.shortName beside p._2 horizontal
//
//  private def panelSet( p: Panels) =
//    modifiedProp(attributeKeyFor(p._1))(p._2)
//}
//
//object AttributesPanel {
//  type Panels = (Attribute, TextField)
//}

// vim: set ts=2 sw=2 et:
