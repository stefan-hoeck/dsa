package efa.dsa.abilities

import efa.core._, Efa._
import efa.rpg.core.RangeVals
import org.scalacheck._, Prop._
import scalaz._, Scalaz._

sealed trait HandicapGp

object HandicapGp extends RangeVals {
  case object Gp32 extends HandicapGp {
    override def toString = "3/2"
  }

  case object Gp12 extends HandicapGp {
    override def toString = "1/2"
  }

  case class Value (v: Int) extends HandicapGp {
    require (value validate v isRight)
    override def toString = v.toString
  }

  val value = genInfo(-99, 0)

  implicit val HandicapGpArbitrary = Arbitrary[HandicapGp] (
    Gen frequency (
      (5, Gp32),
      (5, Gp12),
      (90, value.gen map Value.apply)
    )
  )

  implicit val HandicapGpShow = Show.shows[HandicapGp](_.toString)

  implicit val HandicapGpRead = Read.readV[HandicapGp] {_ match {
      case "3/2" ⇒ Gp32.success
      case "1/2" ⇒ Gp12.success
      case "32" ⇒ Gp32.success
      case "12" ⇒ Gp12.success
      case x ⇒ value read x map Value.apply
    }
  }

  implicit val HandicapGpEqual = Equal.equalA[HandicapGp]

  implicit val HandicapGpToXml = TaggedToXml.readShow[HandicapGp]("gp")

  implicit val HandicapGpDefault = Default.default[HandicapGp](Value(0))
}

// vim: set ts=2 sw=2 et:
