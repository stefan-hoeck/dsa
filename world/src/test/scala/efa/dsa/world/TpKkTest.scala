package efa.dsa.world

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck._, Prop._

object TpKkTest extends ToXmlProps[TpKk] ("TpKk") {

  property("valdateTp") = Prop forAll validated[TpKk,Int](
    (a,i) ⇒ TpKk(i, a.kk))(TpKk.tpkk.validate)

  property("valdateKk") = Prop forAll validated[TpKk,Int](
    (a,i) ⇒ TpKk(a.tp, i))(TpKk.tpkk.validate)
}

// vim: set ts=2 sw=2 et:
