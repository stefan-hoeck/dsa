package efa.dsa.being.equipment

import efa.dsa.equipment.{Bf, Ini}
import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object MeleeWeaponDataTest
  extends ToXmlProps[MeleeWeaponData]("MeleeWeaponData"){
  property("validateBf") =
    Prop forAll validated(MeleeWeaponData.bf.set)(Bf.validate)
}

// vim: set ts=2 sw=2 et:
