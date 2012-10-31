package efa.dsa.world

import efa.core.{IsLocalized, Localization}
import efa.rpg.core.LocEnum

sealed abstract class ShieldType(val loc: Localization) 
extends IsLocalized

object ShieldType {
  import efa.dsa.world.loc

  case object S extends ShieldType(loc.stSLoc)
  case object SP extends ShieldType(loc.stSPLoc)
  case object P extends ShieldType(loc.stPLoc)
  
  val values = List[ShieldType](S, SP, P)

  implicit lazy val STLocEnum =
    LocEnum.tagged(S, values.tail: _*)("shieldtype")
  implicit lazy val STArb = STLocEnum.arbitrary
}

// vim: set ts=2 sw=2 et:
