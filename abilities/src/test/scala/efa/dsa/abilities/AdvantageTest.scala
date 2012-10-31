package efa.dsa.abilities

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object AdvantageItemTest extends ToXmlProps[AdvantageItem]("AdvantageItem") {
  property("validateGp") =
    Prop forAll validated[AdvantageItem,Int](AdvantageItem.gp.set)(Gp.validate)
}

// vim: set ts=2 sw=2 et:
