package efa.dsa.equipment

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object EquipmentItemDataTest extends ToXmlProps[EquipmentItemData]("EquipmentItemData") {

  property("validatePrice") = Prop forAll validated(
    EquipmentItemData.price.set)(Price.validate)

  property("validateWeight") = Prop forAll validated(
    EquipmentItemData.weight.set)(Weight.validate)
}

// vim: set ts=2 sw=2 et:
