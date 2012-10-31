package efa.dsa.world

import scalaz._, Scalaz._
import efa.core.{IsLocalized, Localization}
import efa.rpg.core.LocEnum

sealed abstract class BodyPart(val factor: Int, val loc: Localization)
extends IsLocalized

object BodyPart {
  import efa.dsa.world.loc

  case object Head extends BodyPart(2, loc.headLoc)
  case object Chest extends BodyPart(4, loc.chestLoc)
  case object Back extends BodyPart(4, loc.backLoc)
  case object Stomach extends BodyPart(4, loc.stomachLoc)
  case object ArmL extends BodyPart(1, loc.armlLoc)
  case object ArmR extends BodyPart(1, loc.armrLoc)
  case object LegL extends BodyPart(2, loc.leglLoc)
  case object LegR extends BodyPart(2, loc.legrLoc)
  
  val values =
    List[BodyPart](Head, Chest, Back, Stomach, ArmL, ArmR, LegL, LegR)

  val factorSum = values foldMap (_.factor)

  implicit lazy val BodyPartLocEnum = LocEnum.values(Head, values.tail: _*)

  implicit lazy val BodyPartArbitrary = BodyPartLocEnum.arbitrary
}

// vim: set ts=2 sw=2 et:
