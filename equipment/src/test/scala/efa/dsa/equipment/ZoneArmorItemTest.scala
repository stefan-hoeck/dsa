package efa.dsa.equipment

import efa.dsa.world.Be
import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object ZoneArmorItemTest extends ToXmlProps[ZoneArmorItem]("ZoneArmorItem") {

  property("validateBe") =
    Prop forAll validated(ZoneArmorItem.be.set)(Be.validate)
}

// vim: set ts=2 sw=2 et:
