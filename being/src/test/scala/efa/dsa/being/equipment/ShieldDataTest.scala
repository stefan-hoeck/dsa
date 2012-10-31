package efa.dsa.being.equipment

import efa.dsa.equipment.{Bf, Ini}
import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object ShieldDataTest extends ToXmlProps[ShieldData]("ShieldData"){
  property("validateBf") =
    Prop forAll validated(ShieldData.bf.set)(Bf.validate)

  property("validateIni") =
    Prop forAll validated(ShieldData.ini.set)(Ini.validate)
}

// vim: set ts=2 sw=2 et:
