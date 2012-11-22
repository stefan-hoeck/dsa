package efa.dsa.world

import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import org.scalacheck._, Prop._

object RoundedDivisibleIntTest extends Properties ("RoundedDivisible") {

  val maxI = 100
  val aiGen = Gen choose (0, maxI)
  val biGen = Gen choose (1, maxI)
  val intsGen = ^(aiGen, biGen)((_,_))
  
  property ("halfInt") = Prop.forAll (biGen) { i ⇒ 
    val half = RoundedDiv halfI i
    half * 2 >= i
  }

  property ("rdivInt") = Prop.forAll (intsGen) { p ⇒ 
    val (a, b) = p
    val rd = a rdiv b
    val half = b / 2
    val rest5 = ((b % 2) ≟ 0) && ((a % b) ≟ half)
    val diff = rd * b - a

    //if (rest5) println ("%d / %d = %d" format (a, b, rd))
    (diff.abs <= half) :| "diff smaller than half" &&
    (rest5 ? (diff ≟ half) | true) :| "x.5 but wasn't rounded up"
  }

  val maxL = 100000000L
  val alGen = Gen choose (0L, maxL)
  val blGen = Gen choose (1L, maxL)
  val longsGen = ^(alGen, blGen)((_,_))
  
  property ("halfLong") = Prop.forAll (blGen) { i ⇒ 
    val half = RoundedDiv halfL i
    half * 2L >= i
  }

  property ("rdivLong") = Prop.forAll (longsGen) { p ⇒ 
    val (a, b) = p
    val rd = a rdiv b
    val half = b / 2L
    val rest5 = ((b % 2L) ≟ 0L) && ((a % b) ≟ half)
    val diff = rd * b - a

    //if (rest5) println ("%d / %d = %d" format (a, b, rd))
    (diff.abs <= half) :| "diff smaller than half" &&
    (rest5 ? (diff ≟ half) | true) :| "x.5 but wasn't rounded up"
  }

}
