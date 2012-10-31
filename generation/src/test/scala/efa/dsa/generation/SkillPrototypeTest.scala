package efa.dsa.generation

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object SkillPrototypeTest extends ToXmlProps[SkillPrototype]("SkillPrototype") {

  property("validateValue") =
    Prop forAll validated(SkillPrototype.value.set)(Tap.validate)

}

// vim: set ts=2 sw=2 et:
