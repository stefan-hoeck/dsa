package efa.dsa.being.equipment

import efa.dsa.world.Be
import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object ZoneArmorDataTest extends ToXmlProps[ZoneArmorData]("ZoneArmorData"){
  property("validateBe") =
    Prop forAll validated(ZoneArmorData.be.set)(Be.validate)
}

// vim: set ts=2 sw=2 et:

