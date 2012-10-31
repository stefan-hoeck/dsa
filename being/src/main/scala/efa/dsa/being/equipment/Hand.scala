package efa.dsa.being.equipment

import scalaz._, Scalaz._

sealed trait Hand

object Hand {
  case object Empty extends Hand

  case class Melee(w: MeleeWeapon) extends Hand

  case class Ranged(w: RangedWeapon) extends Hand

  case class Shield(s: efa.dsa.being.equipment.Shield) extends Hand

  case class Ammo(a: Ammunition) extends Hand

  implicit lazy val HandEqual = Equal.equalA[Hand]
}

// vim: set ts=2 sw=2 et:
