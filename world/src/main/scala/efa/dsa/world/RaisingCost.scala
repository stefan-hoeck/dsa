package efa.dsa.world

import efa.core.{IsLocalized, Localization, Validators}
import efa.rpg.core.LocEnum

sealed abstract class RaisingCost(n: String) extends IsLocalized {
  override val loc = new Localization (n, n)

  lazy val lower: RaisingCost = RaisingCost lower this

  lazy val upper: RaisingCost = RaisingCost upper this
}

object RaisingCost {

  case object AStar extends RaisingCost("A*")
  case object A extends RaisingCost("A")
  case object B extends RaisingCost("B")
  case object C extends RaisingCost("C")
  case object D extends RaisingCost("D")
  case object E extends RaisingCost("E")
  case object F extends RaisingCost("F")
  case object G extends RaisingCost("G")
  case object H extends RaisingCost("H")

  lazy val values = List[RaisingCost](AStar, A, B, C, D, E, F, G, H)

  lazy val upper: Map[RaisingCost, RaisingCost] = 
    values zip (values.tail ++ Seq(H)) toMap
  
  lazy val lower: Map[RaisingCost, RaisingCost] = 
    values zip (AStar +: values.init) toMap

  implicit lazy val RCLocEnum =
    LocEnum.tagged(AStar, values.tail: _*)("raisingCost")

  implicit lazy val RCArbitrary = RCLocEnum.arbitrary
}

// vim: set ts=2 sw=2 et:
