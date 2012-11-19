package efa.dsa.being.ui

import efa.core.{ValSt, Localization}
import efa.nb.VSET
import efa.nb.tc.{OutlinePanel, PersistentOutline}
import efa.nb.node.{NodeOut, NbNode}
import scalaz.effect.IO

class NodePanel[A,B](
  out: NodeOut[A,ValSt[B]],
  val prefId: String,
  n: NbNode,
  ls: List[Localization]
) extends OutlinePanel with PersistentOutline {
  override protected def rootNode = n
  override protected def localizations = ls
  override protected def outline = ov
  override protected def version = efa.dsa.being.ui.version

  val set: VSET[A,B] = out set n
}

object NodePanel {
  def apply[A,B](out: NodeOut[A,ValSt[B]], id: String, ls: List[Localization])
  :IO[NodePanel[A,B]] = for {
    n ← NbNode.apply
  } yield new NodePanel(out, id, n, ls)
}

// vim: set ts=2 sw=2 et:
