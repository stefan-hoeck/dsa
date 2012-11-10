package efa.dsa.being.skills

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop
import scalaz._, Scalaz._

object MeleeTalentDataTest extends ToXmlProps[MeleeTalentData]("MeleeTalentData") {

  property("validateAt") =
    Prop forAll validated(MeleeTalentData.at.set)(At.validate)

}

// vim: set ts=2 sw=2 et:
