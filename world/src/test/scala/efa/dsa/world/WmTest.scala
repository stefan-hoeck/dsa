package efa.dsa.world

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck._, Prop._

object WmTest extends ToXmlProps[Wm] ("Wm") {

  property("valdateAt") = Prop forAll validated[Wm,Int](
    (a,i) ⇒ Wm(i, a.at))(Wm.wm.validate)

  property("valdatePa") = Prop forAll validated[Wm,Int](
    (a,i) ⇒ Wm(a.at, i))(Wm.wm.validate)
}

// vim: set ts=2 sw=2 et:
