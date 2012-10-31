package efa.dsa.abilities

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object ScriptureItemTest extends ToXmlProps[ScriptureItem] ("ScriptureItemTest") {
  property("validateComplexity") =
    Prop forAll validated(ScriptureItem.complexity.set)(Complexity.validate)
}

// vim: set ts=2 sw=2 et:
