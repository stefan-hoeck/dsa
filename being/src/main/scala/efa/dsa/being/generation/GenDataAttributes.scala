package efa.dsa.being.generation

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.dsa.being.attributeMods
import efa.rpg.core.{Modifiers, Util}
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class GenDataAttributes(data: GenData, attributes: Attributes) {

  lazy val modifiers = attributeMods(attributes, data.name) ⊹ data.modifiers
}

object GenDataAttributes extends Util {
  val default = GenDataAttributes(!!, !!)

  implicit val GenDataAttributesDefault = Default default default

  implicit val GenDataAttributesEqual = Equal.equalA[GenDataAttributes]

  implicit val GenDataAttributesArbitrary = Arbitrary(
    ^(a[GenData], Attributes.gen)(GenDataAttributes.apply)
  )

  implicit val GenDataAttributesToXml = new ToXml[GenDataAttributes] {
    def fromXml (ns: Seq[Node]) =
      ^(GenData read ns, Attributes read ns)(GenDataAttributes.apply)

    def toXml (a: GenDataAttributes) =
      GenData.write(a.data) ++
      Attributes.write(a.attributes)
  }

  val data: GenDataAttributes @> GenData =
    Lens.lensu((a,b) ⇒ a.copy(data = b), _.data)

  val attributes: GenDataAttributes @> Attributes =
    Lens.lensu((a,b) ⇒ a.copy(attributes = b), _.attributes)
}

// vim: set ts=2 sw=2 et:
