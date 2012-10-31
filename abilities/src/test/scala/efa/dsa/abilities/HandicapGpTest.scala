package efa.dsa.abilities

import efa.rpg.core.specs.ReadProps
import org.scalacheck._, Prop._
import scalaz.Equal

object HandicapGpTest extends ReadProps[HandicapGp] ("HandicapGp") {

  implicit val ValueArbitrary: Arbitrary[HandicapGp.Value] =
    Arbitrary(HandicapGp.Value(0))  

  implicit val ValueEqual = Equal.equalA[HandicapGp.Value]
 
  property("validateValue") = Prop forAll validated[HandicapGp.Value,Int](
    (v,i) ⇒ HandicapGp.Value(i))(HandicapGp.value.validate)
}

// vim: set ts=2 sw=2 et:
