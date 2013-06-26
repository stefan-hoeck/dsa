package efa.dsa.being.ui

import dire.swing._, Swing._
import efa.dsa.being.{loc ⇒ bLoc, _}
import efa.rpg.being.BeingPanel, BeingPanel._
import efa.rpg.core.HasModifiers
import scalaz._, Scalaz._, effect.IO

object BePanel {
  def apply[A:HasModifiers,B](): IO[BeingPanel[A,B,Panel]] = for {
    be ← disabledNumeric
    p  ← BeKey.loc.shortName beside be panel (border := Border.title(bLoc.be))
  } yield BeingPanel[A,B,Panel](p, modifiedProp(BeKey, be))
}

// vim: set ts=2 sw=2 et:
