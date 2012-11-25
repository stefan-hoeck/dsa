package efa.dsa.being

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop
import scalaz._, Scalaz._

object HumanoidDataTest
   extends ToXmlProps[HumanoidData]("HumanoidData"){

  property("validateLostAe") = Prop forAll validated(
    HumanoidData.lostAe.set)(LostAe.validate)

  property("validateLostAu") = Prop forAll validated(
    HumanoidData.lostAu.set)(LostAu.validate)

  property("validateLostKe") = Prop forAll validated(
    HumanoidData.lostKe.set)(LostKe.validate)

  property("validateLostLe") = Prop forAll validated(
    HumanoidData.lostLe.set)(LostLe.validate)

  property("validateExhaustion") = Prop forAll validated(
    HumanoidData.exhaustion.set)(Exhaustion.validate)

  property("validateWounds") = Prop forAll validated(
    HumanoidData.wounds.set)(Wounds.validate)
}

// vim: set ts=2 sw=2 et:
