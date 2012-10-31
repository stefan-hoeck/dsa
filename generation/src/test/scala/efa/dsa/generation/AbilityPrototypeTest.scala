package efa.dsa.generation

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object AbilityPrototypeTest
  extends ToXmlProps[AbilityPrototype]("AbilityPrototype") {

  property("validateValue") =
    Prop forAll validated(AbilityPrototype.value.set)(Value.validate)

}

// vim: set ts=2 sw=2 et:
