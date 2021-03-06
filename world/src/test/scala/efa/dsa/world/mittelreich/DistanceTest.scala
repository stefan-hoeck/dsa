package efa.dsa.world.mittelreich

import efa.rpg.core.specs.UnitEnumProps
import org.scalacheck._, Prop._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

object DistanceTest extends UnitEnumProps[Distance] ("Distance") {
  import Distance._
  val pairs = List[(Distance,Int)]((M, 5), (S, 2), (SP, 2), (F, 1), (HF, 0))
  val longGen = Gen choose (0L, 1000000000000000L)
  val gen = ^(Gen.oneOf(pairs), longGen)(Tuple2.apply)


  property("showPretty") = Prop.forAll(gen) {p ⇒ 
    val ((c, nod), l) = p
    val read = readPretty(c)(showPretty(c, nod)(l))

    (read ≟ l.right) :| ("Exp: %s, found: %s" format (l, read))
  }
}

// vim: set ts=2 sw=2 et:
