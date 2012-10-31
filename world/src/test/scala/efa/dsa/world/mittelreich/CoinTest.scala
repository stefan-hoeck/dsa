package efa.dsa.world.mittelreich

import efa.core.Efa._
import efa.rpg.core.specs.UnitEnumProps
import org.scalacheck._, Prop._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

object CoinTest extends UnitEnumProps[Coin] ("Coin") {
  import Coin._
  val pairs = List[(Coin,Int)]((D, 3), (S, 2), (H, 1), (K, 0))
  val longGen = Gen choose (0L, 1000000000000000L)
  val gen =  ^(Gen.oneOf(pairs), longGen)(Tuple2.apply)


  property("showPretty") = Prop.forAll(gen) {p ⇒ 
    val ((c, nod), l) = p
    val read = readPretty(c)(showPretty(c, nod)(l))

    (read ≟ l.success) :| ("Exp: %s, found: %s" format (l, read))
  }
}

// vim: set ts=2 sw=2 et:
