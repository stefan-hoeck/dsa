package efa.dsa.being.equipment

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object AmmunitionDataTest extends ToXmlProps[AmmunitionData]("AmmunitionData"){
  property("validateCount") =
    Prop forAll validated(AmmunitionData.count.set)(Count.validate)
}

// vim: set ts=2 sw=2 et:
