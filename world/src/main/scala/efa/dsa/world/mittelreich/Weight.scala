package efa.dsa.world.mittelreich

import efa.dsa.world.loc
import efa.core.Localization
import efa.rpg.core.{UnitEnum, IsUnit}

sealed abstract class Weight(
  val multiplier: Long, val loc: Localization, val plural: String
) extends IsUnit

object Weight {
  /**
   * Quader = 1000 kg
   */
  case object Q extends Weight(25000000L, loc.weightQLoc, loc.pluralWeightQ)

  /**
  * Stein = 1000 g
  */
  case object ST extends Weight(25000L, loc.weightSTLoc, loc.pluralWeightST)

  /**
   * Unze = 25 g
   */
  case object U extends Weight(625, loc.weightULoc, loc.pluralWeightU)

  /**
   * Skrupel = 1 g
   */
  case object SK extends Weight(25L, loc.weightSKLoc, loc.pluralWeightSK)

  /**
   * Karat = 200 mg
   */
  case object K extends Weight(5L, loc.weightKLoc, loc.pluralWeightK)

  /**
   * Gran = 40 mg
   */
  case object G extends Weight(1L, loc.weightGLoc, loc.pluralWeightG)
  
  val values = List[Weight](G, K, SK, U, ST, Q)

  implicit lazy val WUnitEnum = UnitEnum.values(G, values.tail: _*)
  implicit lazy val WArb = WUnitEnum.arbitrary
}

// vim: set ts=2 sw=2 et:
