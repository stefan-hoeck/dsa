package efa.dsa.generation

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.Util
import org.scalacheck.{Arbitrary, Gen}, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class AbilityPrototype(parentId: Int, name: String, value: Int) {
  require(Value validate value isRight)
}

object AbilityPrototype extends Util {
  val default = AbilityPrototype(0, "", 0)

  implicit val AbilityPrototypeDefault = Default default default

  implicit val AbilityPrototypeEqual = Equal.equalA[AbilityPrototype]

  implicit val AbilityPrototypeArbitrary = Arbitrary(
    ^^(a[Int], Gen.identifier, Value.gen)(AbilityPrototype.apply)
  )

  implicit val AbilityPrototypeToXml = new ToXml[AbilityPrototype] {
    def fromXml (ns: Seq[Node]) =
      ^^(ns.readTag[Int]("id"),
        ns.readTag[String]("name"),
        Value read ns)(AbilityPrototype.apply)

    def toXml (a: AbilityPrototype) = 
      ("id" xml a.parentId) ++ ("name" xml a.name) ++ Value.write(a.value)
  }

  implicit val AbilityPrototypeWithId =
    withId[AbilityPrototype](_.parentId)

  val parentId: AbilityPrototype @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val name: AbilityPrototype @> String =
    Lens.lensu((a,b) ⇒ a.copy(name = b), _.name)

  val value: AbilityPrototype @> Int =
    Lens.lensu((a,b) ⇒ a.copy(value = b), _.value)
}

// vim: set ts=2 sw=2 et:
