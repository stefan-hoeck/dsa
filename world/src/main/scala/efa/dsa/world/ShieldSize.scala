package efa.dsa.world

import efa.core.{IsLocalized, Localization}
import efa.rpg.core.LocEnum

sealed abstract class ShieldSize (val loc: Localization) 
extends IsLocalized

object ShieldSize {
  import efa.dsa.world.loc

  case object VeryLarge extends ShieldSize (loc.ssVeryLargeLoc)
  case object Large extends ShieldSize (loc.ssLargeLoc)
  case object Small extends ShieldSize (loc.ssSmallLoc)
  case object Parier extends ShieldSize (loc.ssParierLoc)
  
  val values = List[ShieldSize](Parier, Small, Large, VeryLarge)

  implicit lazy val SSLocEnum =
    LocEnum.tagged(Parier, values.tail: _*)("size")
  implicit lazy val SSArb = SSLocEnum.arbitrary
}
