package efa.dsa.being.ui

import efa.nb.node.{NodeOut, NbNode ⇒ N}
import efa.rpg.core.{Described}
import scalaz._, Scalaz._

object Nodes {
  def described[A:Described]: NodeOut[A,Nothing] =
    N.name(Described[A].name) ⊹ N.desc(Described[A].shortDesc)

  def childActions (path: String): NodeOut[Any,Nothing] =
    N.contextRootsA(List("ContextActions/DsaChildNode", path))
}

// vim: set ts=2 sw=2 et:
