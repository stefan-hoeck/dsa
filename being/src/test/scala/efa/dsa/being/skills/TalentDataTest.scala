package efa.dsa.being.skills

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object TalentDataTest extends ToXmlProps[TalentData]("TalentData") {

  property("validateTap") =
    Prop forAll validated(TalentData.tap.set)(Tap.validate)

}

// vim: set ts=2 sw=2 et:
