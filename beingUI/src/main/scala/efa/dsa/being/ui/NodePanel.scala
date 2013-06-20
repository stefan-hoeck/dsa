package efa.dsa.being.ui

import efa.core.{ValSt, Localization}
import efa.nb.tc.OutlineNb
import efa.nb.node.{NodeOut, NbNode}
import efa.rpg.being.BeingPanel
import scalaz.effect.IO

object NodePanel {
  def apply[A,B](out: NodeOut[A,ValSt[B]], id: String, ls: List[Localization])
    :IO[BeingPanel[A,B,OutlineNb]] = for {
    n  ← NbNode()
    nb ← OutlineNb(n, ls)
  } yield BeingPanel(nb, out sf n)
}

// vim: set ts=2 sw=2 et:
