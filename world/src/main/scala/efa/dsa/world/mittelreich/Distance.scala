package efa.dsa.world.mittelreich

import efa.dsa.world.loc
import efa.core.Localization
import efa.rpg.core.{UnitEnum, IsUnit}

sealed abstract class Distance (
  val multiplier: Long, val loc: Localization, val plural: String
) extends IsUnit

object Distance {
  case object M extends Distance(100000L, loc.distanceMLoc, loc.pluralDistanceM)
  case object S extends Distance(100L, loc.distanceSLoc, loc.pluralDistanceS)
  case object SP extends Distance(20L, loc.distanceSPLoc, loc.pluralDistanceSP)
  case object F extends Distance(2L, loc.distanceFLoc, loc.pluralDistanceF)
  case object HF extends Distance(1L, loc.distanceHFLoc, loc.pluralDistanceHF)
  
  val values = List[Distance](HF, F, SP, S, M)

  implicit lazy val CUnitEnum = UnitEnum.values(HF, values.tail: _*)
  implicit lazy val CArb = CUnitEnum.arbitrary
}

// vim: set ts=2 sw=2 et:
