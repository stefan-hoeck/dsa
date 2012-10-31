package efa.dsa.equipment

import efa.dsa.world.{Rs, Be}
import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object ArmorItemTest extends ToXmlProps[ArmorItem]("ArmorItem") {

  property("validateRs") = Prop forAll validated(
    ArmorItem.rs.set)(Rs.validate)

  property("validateBe") = Prop forAll validated(
    ArmorItem.be.set)(Be.validate)
}

// vim: set ts=2 sw=2 et:
