package efa.dsa.being.skills

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop
import scalaz._, Scalaz._

object MeleeTalentDataTest extends ToXmlProps[MeleeTalentData]("MeleeTalentData") {

  property("validateAt") = Prop.forAll {p: (TalentData, Int) ⇒ 
    val (sd, i) = p
    val res = try {
      val s = MeleeTalentData(sd, i)
      true
    } catch {case e: IllegalArgumentException ⇒ false}

    res ≟ (MeleeTalentData atI sd  validate i isRight)
  }

}

// vim: set ts=2 sw=2 et:
