package efa.dsa.being.abilities

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object AdvantageDataTest extends ToXmlProps[AdvantageData]("AdvantageData") {

  property("validateValue") =
    Prop forAll validated(AdvantageData.value.set)(Value.validate)

}

// vim: set ts=2 sw=2 et:
