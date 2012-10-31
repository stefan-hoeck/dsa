package efa.dsa.being

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.Util
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class AttributesData(bought: Attributes, initial: Attributes) {
  require (BoughtAttributes validate bought isRight)
  require (InitialAttributes validate initial isRight)

  lazy val modifiers =
    attributeMods(initial, loc.initial) ⊹
    attributeMods(bought, loc.bought)

}

object AttributesData extends Util {
  val default = AttributesData(BoughtAttributes.!!, InitialAttributes.!!)

  implicit val AttributesDataDefault = Default default default

  implicit val AttributesDataEqual = Equal.equalA[AttributesData]

  implicit val AttributesDataArbitrary = Arbitrary (
    ^(BoughtAttributes.gen, InitialAttributes.gen)(AttributesData.apply)
  )

  implicit val AttributesDataToXml = new ToXml[AttributesData] {
    def fromXml (ns: Seq[Node]) =
      ^(BoughtAttributes read ns,
        InitialAttributes read ns)(AttributesData.apply)

    def toXml (a: AttributesData) = 
      BoughtAttributes.write(a.bought) ++ InitialAttributes.write(a.initial)
  }

  def read (ns: Seq[Node]) = AttributesDataToXml fromXml ns

  def write (h: AttributesData) = AttributesDataToXml toXml h

  val bought: AttributesData @> Attributes =
    Lens.lensu((a,b) ⇒ a copy (bought= b), _.bought)

  val initial: AttributesData @> Attributes =
    Lens.lensu((a,b) ⇒ a copy (initial= b), _.initial)
}

// vim: set ts=2 sw=2 et:
