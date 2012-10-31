package efa.dsa.generation

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object EquipmentPrototypeTest
   extends ToXmlProps[EquipmentPrototype]("EquipmentPrototype") {

  property("validateCount") =
    Prop forAll validated(EquipmentPrototype.count.set)(Count.validate)

}

// vim: set ts=2 sw=2 et:
