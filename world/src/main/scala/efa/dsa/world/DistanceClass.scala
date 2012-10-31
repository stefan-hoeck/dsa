package efa.dsa.world

import efa.core.{IsLocalized, Localization}
import efa.rpg.core.LocEnum

sealed abstract class DistanceClass(
  val smallest: Int, val largest: Int, val loc: Localization
) extends IsLocalized

object DistanceClass {
  import efa.dsa.world.loc

  case object H extends DistanceClass(0, 0, loc.dcHLoc)
  case object HN extends DistanceClass(0, 1, loc.dcHNLoc)
  case object N extends DistanceClass(1, 1, loc.dcNLoc)
  case object NS extends DistanceClass(1, 2, loc.dcNSLoc)
  case object S extends DistanceClass(2, 2, loc.dcSLoc)
  case object SP extends DistanceClass(2, 3, loc.dcSPLoc)
  case object P extends DistanceClass(3, 3, loc.dcPLoc)
  case object HNS extends DistanceClass(0, 2, loc.dcHNSLoc)
  case object NSP extends DistanceClass(1, 3, loc.dcNSPLoc)
  case object HNSP extends DistanceClass(0, 3, loc.dcHNSPLoc)
  
  val values = List[DistanceClass](H, HN, N, NS, S, SP, P, HNS, NSP, HNSP)

  implicit lazy val DCLocEnum = LocEnum.tagged(H, values.tail: _*)("dk")
  implicit lazy val DCArb = DCLocEnum.arbitrary
}
