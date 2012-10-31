package efa.dsa.world

import efa.rpg.core.specs.ReadProps
import org.scalacheck._, Prop._
import scalaz._, Scalaz._

object EbeTest extends ReadProps[Ebe]("Ebe") {
  import Ebe._
  
  property ("calcNone") = Prop.forAll { i: Long ⇒ 
    NoEbe.calcEbe (i) ≟ 0L
  }

  val addGen = Ebe.add.gen map Add.apply

  val addBeGen = for {
    add ← addGen
    be  ← Gen choose (0L, 100L)
  } yield (add, be)

  property ("calcAdd") = Prop.forAll (addBeGen) { p ⇒ 
    val (add, be) = p
    add.calcEbe (be) ≟ ((be + add.add) max 0L)
  }

  val facGen = Ebe.fac.gen map Factor.apply

  val facBeGen = for {
    fac ← facGen
    be  ← Gen choose (0L, 100L)
  } yield (fac, be)

  property ("calcFac") = Prop.forAll (facBeGen) { p ⇒ 
    val (fac, be) = p
    fac.calcEbe (be) ≟ (be * fac.f)
  }

  implicit val AddEqual = Equal.equalA[Add]
  implicit val AddArbitrary = Arbitrary(addGen)

  implicit val FacEqual = Equal.equalA[Factor]
  implicit val FacArbitrary = Arbitrary(facGen)

  property("validateAdd") =
    Prop forAll validated[Add,Long]((a,i) ⇒ Add(i))(add.validate)

  property("validateFactor") =
    Prop forAll validated[Factor,Long]((a,i) ⇒ Factor(i))(fac.validate)
}

// vim: set ts=2 sw=2 et:
