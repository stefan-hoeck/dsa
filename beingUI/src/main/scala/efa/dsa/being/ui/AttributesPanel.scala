package efa.dsa.being.ui

import dire.swing._, Swing._, Elem.Horizontal
import efa.dsa.being._
import efa.dsa.world.Attribute
import efa.rpg.being.BeingPanel, BeingPanel._
import efa.rpg.core.{RpgEnum, HasModifiers}
import scalaz._, Scalaz._, effect.IO

object AttributesPanel {
  def apply[A:HasModifiers,B](): IO[BeingPanel[A,B,Panel]] = for {
    ps ← Attribute.values traverse (a ⇒ disabledNumeric map ((a, _)))
    p  ← ps foldMap panelElem panel
  } yield BeingPanel(p, ps foldMap sf[A,B])

  private def panelElem(p: (Attribute, TextField)): Elem @@ Horizontal =
    p._1.loc.shortName beside p._2 horizontal

  private def sf[A:HasModifiers,B](p: (Attribute, TextField)) =
    modifiedProp[A,B,TextField](attributeKeyFor(p._1), p._2)
}

// vim: set ts=2 sw=2 et:
