package efa.dsa.being.ui

import dire.swing._, dire.swing.Swing._
import efa.core.{ValSt, Localization}
import efa.nb.tc.OutlineNb
import efa.nb.node.{NodeOut, NbNode}
import efa.rpg.being.BeingPanel
import scalaz.effect.IO

object NodePanel {
  def apply[A,B](out: NodeOut[A,ValSt[B]], ls: List[Localization])
    :IO[NP[A,B]] = for {
      n  ← NbNode()
      nb ← OutlineNb(n, ls)
    } yield BeingPanel(nb, out sf n)

  def apply[A,B](out: NodeOut[A,ValSt[B]],
                 ls: List[Localization],
                 title: String): IO[NP[A,B]] = for {
    bp ← NodePanel(out, ls)
    _  ← bp.p properties (border := Border.title(title))
  } yield bp
}

// vim: set ts=2 sw=2 et:
