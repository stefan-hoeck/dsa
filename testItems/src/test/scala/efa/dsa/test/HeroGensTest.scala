package efa.dsa.test

import efa.core.Efa._
import efa.dsa.being.HeroData
import org.scalacheck._, Prop._
import scalaz._, Scalaz._

object HeroGensTest extends Properties("HeroGens") {
  val pretty = new scala.xml.PrettyPrinter(80, 2)

  val dataGen = for {
    as ← AbilityGens.abilitiesGen
    es ← EquipmentGens.equipmentGen
    h ← HeroGens heroDataGen (as, es)
  } yield h

  property("xml") = Prop.forAll(dataGen) {h ⇒ 
    val xml = "hero" xml h
    //println(pretty format xml)

    xml.read[HeroData] ≟ h.success
  }
}

// vim: set ts=2 sw=2 et:
