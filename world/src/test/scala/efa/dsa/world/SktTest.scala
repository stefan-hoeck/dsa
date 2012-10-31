package efa.dsa.world

import org.scalacheck._, Prop._
import scalaz._, Scalaz._

object SktTest extends Properties ("Skt") {

  property ("D0") = 20 ≟ Skt.cost (RaisingCost.D, -1)

  property ("H5") = 110 ≟ Skt.cost (RaisingCost.H, 4)

  property ("all success") = {
    val pairs = for (i ← -10 to 40; rc ← RaisingCost.values) yield (rc, i)
    pairs.toList ∀ (p ⇒ Skt.cost(p._1, p._2) > 0)
  }

}

// vim: set ts=2 sw=2 et:
