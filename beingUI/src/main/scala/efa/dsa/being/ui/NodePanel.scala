package efa.dsa.being.ui

import efa.core.{ValSt, Localization}
import efa.nb.VSET
import efa.nb.tc.OutlinePanel
import efa.nb.node.{NodeOut, NbNode}
import scalaz.effect.IO

class NodePanel[A,B](
  out: NodeOut[A,ValSt[B]], n: NbNode, ls: List[Localization]
) extends OutlinePanel {
  override protected def rootNode = n
  override protected def localizations = ls

  val set: VSET[A,B] = out set n
}

object NodePanel {
  def apply[A,B](out: NodeOut[A,ValSt[B]], ls: List[Localization])
  :IO[NodePanel[A,B]] = for {
    n ← NbNode.apply
  } yield new NodePanel(out, n, ls)
}

// vim: set ts=2 sw=2 et:
