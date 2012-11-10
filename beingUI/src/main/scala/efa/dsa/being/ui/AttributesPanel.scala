package efa.dsa.being.ui

import efa.dsa.being._
import efa.dsa.world.Attribute
import efa.rpg.being.BeingPanel
import efa.rpg.core.{RpgEnum, HasModifiers}
import scala.swing.TextField
import scalaz._, Scalaz._

class AttributesPanel[A:HasModifiers,B] extends BeingPanel[A,B] {
  import AttributesPanel._

  val pnls = Attribute.values map ((_, numberDisabled))

  pnls foldMap panelElem add()

  def set = pnls foldMap panelSet

  private def panelElem (p: Panels): Elem @@ Horizontal =
    p._1.loc.shortName beside p._2 horizontal

  private def panelSet( p: Panels) =
    modifiedProp(attributeKeyFor(p._1))(p._2)
}

object AttributesPanel {
  type Panels = (Attribute, TextField)
}

// vim: set ts=2 sw=2 et:
