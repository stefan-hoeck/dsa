package efa.dsa.being

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop
import scalaz._, Scalaz._

object HumanoidBaseDataTest
   extends ToXmlProps[HumanoidBaseData]("HumanoidBaseData"){

  property("validateLostAe") = Prop forAll validated(
    HumanoidBaseData.lostAe.set)(LostAe.validate)

  property("validateBoughtAe") = Prop forAll validated(
    HumanoidBaseData.boughtAe.set)(BoughtAe.validate)

  property("validateLostAu") = Prop forAll validated(
    HumanoidBaseData.lostAu.set)(LostAu.validate)

  property("validateBoughtAu") = Prop forAll validated(
    HumanoidBaseData.boughtAu.set)(BoughtAu.validate)

  property("validateLostKe") = Prop forAll validated(
    HumanoidBaseData.lostKe.set)(LostKe.validate)

  property("validateBoughtKe") = Prop forAll validated(
    HumanoidBaseData.boughtKe.set)(BoughtKe.validate)

  property("validateLostLe") = Prop forAll validated(
    HumanoidBaseData.lostLe.set)(LostLe.validate)

  property("validateBoughtLe") = Prop forAll validated(
    HumanoidBaseData.boughtLe.set)(BoughtLe.validate)

  property("validateBoughtMr") = Prop forAll validated(
    HumanoidBaseData.boughtMr.set)(BoughtMr.validate)

  property("validateExhaustion") = Prop forAll validated(
    HumanoidBaseData.exhaustion.set)(Exhaustion.validate)

  property("validateWounds") = Prop forAll validated(
    HumanoidBaseData.wounds.set)(Wounds.validate)
}

// vim: set ts=2 sw=2 et:
