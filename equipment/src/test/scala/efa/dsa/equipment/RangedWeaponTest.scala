package efa.dsa.equipment

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object RangedWeaponItemTest extends ToXmlProps[RangedWeaponItem]("RangedWeaponItem") {
    
  property("validateTtl") = Prop forAll validated(
    RangedWeaponItem.timeToLoad.set)(Ttl.validate)
}

// vim: set ts=2 sw=2 et:
