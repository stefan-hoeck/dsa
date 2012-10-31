package efa.dsa.abilities

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object FeatItemTest extends ToXmlProps[FeatItem] ("FeatItemTest") {
  property("validateAp") =
    Prop forAll validated(FeatItem.ap.set)(Ap.validate)
}

// vim: set ts=2 sw=2 et:
