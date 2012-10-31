package efa.dsa.world

import efa.core.{IsLocalized, Localization}
import efa.rpg.core.LocEnum

sealed abstract class RangedDistance(val modifier: Int, val loc: Localization) 
extends IsLocalized

object RangedDistance {
  import efa.dsa.world.loc

  case object VeryClose extends RangedDistance(-2, loc.rdVeryCloseLoc)
  case object Close extends RangedDistance(0, loc.rdCloseLoc)
  case object Medium extends RangedDistance(4, loc.rdMediumLoc)
  case object Far extends RangedDistance(8, loc.rdFarLoc)
  case object VeryFar extends RangedDistance(12, loc.rdVeryFarLoc)
  
  val values = List[RangedDistance](VeryClose, Close, Medium, Far, VeryFar)

  implicit lazy val RDLocEnum = LocEnum.values(VeryClose, values.tail: _*)
  implicit lazy val RDArp = RDLocEnum.arbitrary
}
