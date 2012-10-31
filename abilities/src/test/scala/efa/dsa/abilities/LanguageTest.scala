package efa.dsa.abilities

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object LanguageItemTest extends ToXmlProps[LanguageItem] ("LanguageItemTest") {
  property("validateComplexity") =
    Prop forAll validated(LanguageItem.complexity.set)(Complexity.validate)
}

// vim: set ts=2 sw=2 et:
