package efa.dsa.abilities

import efa.dsa.world.Attribute
import efa.core.{Efa, TaggedToXml, Read, Validators, Default}, Efa._
import efa.core.syntax.{string, nodeSeq}
import efa.rpg.core.RangeVals
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import org.scalacheck.{Gen, Arbitrary}

case class Attributes(a: Attribute, b: Attribute, c: Attribute) 
   extends IndexedSeq[Attribute]{
  override def toString =
    "%s %s %s" format (a.shortName, b.shortName, c.shortName)

  override def apply(idx: Int) = idx match {
    case 0 ⇒ a
    case 1 ⇒ b
    case 2 ⇒ c
    case _ ⇒ throw new IndexOutOfBoundsException(idx.toString)
  }

  override def length = 3
}

object Attributes {
  val default = Attributes (Attribute.Ch, Attribute.Ch, Attribute.Ch)

  //type classes
  //type class Equal inherited from Seq
  implicit val AttributesEqual = Equal.equalA[Attributes]

  implicit val AttributesDefault = Default default default

  implicit val AttributesShow = Show.shows[Attributes](_.toString)

  implicit val AttributesRead = Read.readV[Attributes] {
    val c = "[a-zA-Zäöü]"
    val reg = "(%s+) (%s+) (%s+)" format (c, c, c) r

    _ match {
      case reg (a, b, c) ⇒ ^^(a.read[Attribute],
                             b.read[Attribute],
                             c.read[Attribute])(Attributes.apply)
      case _ ⇒ efa.dsa.abilities.loc.invalidAttributes.failureNel
    }
  }

  implicit val AttributesToXml =
    TaggedToXml.readShow[Attributes]("attributes")

  implicit val AttributesArbitrary = {
    val arb = Arbitrary.arbitrary[Attribute]

    Arbitrary (^^(arb, arb, arb)(Attributes.apply))
  }
}

// vim: set ts=2 sw=2 et:
