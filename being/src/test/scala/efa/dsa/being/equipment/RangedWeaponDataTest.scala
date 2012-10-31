package efa.dsa.being.equipment

import efa.dsa.equipment.Ttl
import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object RangedWeaponDataTest
extends ToXmlProps[RangedWeaponData]("RangedWeaponData") {
  property("validateTtl") =
    Prop forAll validated(RangedWeaponData.timeToLoad.set)(Ttl.validate)
}

// vim: set ts=2 sw=2 et:
