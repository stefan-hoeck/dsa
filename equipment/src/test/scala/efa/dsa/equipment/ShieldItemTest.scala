package efa.dsa.equipment

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object ShieldItemTest extends ToXmlProps[ShieldItem]("ShieldItem") {

  property("validateIni") = Prop forAll validated(
    ShieldItem.ini.set)(Ini.validate)

  property("validateBf") = Prop forAll validated(
    ShieldItem.bf.set)(Bf.validate)
}

// vim: set ts=2 sw=2 et:
