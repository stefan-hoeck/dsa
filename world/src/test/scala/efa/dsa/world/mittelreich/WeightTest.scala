package efa.dsa.world.mittelreich

import efa.core.Efa._
import efa.rpg.core.specs.UnitEnumProps
import org.scalacheck._, Prop._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

object WeightTest extends UnitEnumProps[Weight] ("Weight") {
  import Weight._
  val pairs = List[(Weight,Int)]((Q, 8), (ST, 5), (U, 4), (SK, 2), (K, 1), (G, 0))
  val longGen = Gen choose (0L, 1000000000000000L)
  val gen = ^(Gen oneOf pairs, longGen)(Tuple2.apply)


  property("showPretty") = Prop.forAll(gen) {p ⇒ 
    val ((c, nod), l) = p
    val read = readPretty(c)(showPretty(c, nod)(l))

    (read ≟ l.success) :| ("Exp: %s, found: %s" format (l, read))
  }
}

// vim: set ts=2 sw=2 et:
