package efa.dsa.being.equipment

import efa.dsa.world.{Rs, Be}
import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object ArmorDataTest extends ToXmlProps[ArmorData]("ArmorData"){
  property("validateRs") =
    Prop forAll validated(ArmorData.rs.set)(Rs.validate)

  property("validateBe") =
    Prop forAll validated(ArmorData.be.set)(Be.validate)
}

// vim: set ts=2 sw=2 et:
