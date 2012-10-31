package efa.dsa.being.generation

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object GenDataTest extends ToXmlProps[GenData]("GenData"){

  property("validateAe") = Prop forAll validated(GenData.ae.set)(Ae.validate)

  property("validateAu") = Prop forAll validated(GenData.au.set)(Au.validate)

  property("validateLe") = Prop forAll validated(GenData.le.set)(Le.validate)

  property("validateMr") = Prop forAll validated(GenData.mr.set)(Mr.validate)
}

// vim: set ts=2 sw=2 et:
