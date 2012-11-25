package efa.dsa.being

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop
import scalaz._, Scalaz._

object HeroDataTest extends ToXmlProps[HeroData]("HeroData") {

  property("validateBoughtAe") = Prop forAll validated(
    HeroData.boughtAe.set)(BoughtAe.validate)

  property("validateBoughtAu") = Prop forAll validated(
    HeroData.boughtAu.set)(BoughtAu.validate)

  property("validateBoughtKe") = Prop forAll validated(
    HeroData.boughtKe.set)(BoughtKe.validate)

  property("validateBoughtLe") = Prop forAll validated(
    HeroData.boughtLe.set)(BoughtLe.validate)

  property("validateBoughtMr") = Prop forAll validated(
    HeroData.boughtMr.set)(BoughtMr.validate)
}

// vim: set ts=2 sw=2 et:
