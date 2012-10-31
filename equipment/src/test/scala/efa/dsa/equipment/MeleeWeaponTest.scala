package efa.dsa.equipment

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop
import scalaz._, Scalaz._

object MeleeWeaponItemTest extends ToXmlProps[MeleeWeaponItem]("MeleeWeaponItem") {

  property("validateIni") = Prop forAll validated(
    MeleeWeaponItem.ini.set)(Ini.validate)

  property("validateBf") = Prop forAll validated(
    MeleeWeaponItem.bf.set)(Bf.validate)

  property("validateLength") = Prop forAll validated(
    MeleeWeaponItem.length.set)(Length.validate)
}

// vim: set ts=2 sw=2 et:
