package efa.dsa.world.mittelreich

import efa.dsa.world.loc
import efa.core.Localization
import efa.rpg.core.{UnitEnum, IsUnit}

sealed abstract class Coin(
  val multiplier: Long, val loc: Localization, val plural: String
) extends IsUnit

object Coin {
  case object D extends Coin(1000L, loc.coinDLoc, loc.pluralCoinD)
  case object S extends Coin(100L, loc.coinSLoc, loc.pluralCoinS)
  case object H extends Coin(10L, loc.coinHLoc, loc.pluralCoinH)
  case object K extends Coin(1L, loc.coinKLoc, loc.pluralCoinK)
  
  val values = List[Coin](K, H, S, D)

  implicit lazy val CUnitEnum = UnitEnum.values(K, values.tail: _*)
  implicit lazy val CArb = CUnitEnum.arbitrary
}

// vim: set ts=2 sw=2 et:
