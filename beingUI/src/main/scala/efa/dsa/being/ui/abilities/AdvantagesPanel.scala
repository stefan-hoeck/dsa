package efa.dsa.being.ui.abilities

import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.abilities._
import efa.nb.VSET
import efa.nb.node.NbNode
import efa.nb.tc.OutlinePanel
import scalaz.effect.IO

class AbilitiesPanel (n: NbNode) extends OutlinePanel {
  def localizations = List(bLoc.isActiveLoc)
  def rootNode = n

  def set: VSET[Abilities,AbilityDatas] = AbilityNodes.default set n
}

object AbilitiesPanel {
  def create: IO[AbilitiesPanel] = for {
    n ← NbNode.apply
    p ← IO(new AbilitiesPanel(n))
  } yield p
}

// vim: set ts=2 sw=2 et:
