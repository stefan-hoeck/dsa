package efa.dsa.generation

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.Util
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class EquipmentPrototype(parentId: Int, count: Int) {
  require(Count validate count isRight)
}

object EquipmentPrototype extends Util {
  val default = EquipmentPrototype(0, 1)

  implicit val EquipmentPrototypeDefault = Default default default

  implicit val EquipmentPrototypeEqual = Equal.equalA[EquipmentPrototype]

  implicit val EquipmentPrototypeArbitrary =
    Arbitrary(^(a[Int], Count.gen)(EquipmentPrototype.apply))

  implicit val EquipmentPrototypeToXml = new ToXml[EquipmentPrototype] {
    def fromXml (ns: Seq[Node]) =
      ^(ns.readTag[Int]("id"), Count read ns)(EquipmentPrototype.apply)

    def toXml (a: EquipmentPrototype) = 
      ("id" xml a.parentId) ++ Count.write(a.count)
  }

  implicit val EquipmentPrototypeWithId =
    withId[EquipmentPrototype](_.parentId)

  val parentId: EquipmentPrototype @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)
  
  val count: EquipmentPrototype @> Int =
    Lens.lensu((a,b) ⇒ a.copy(count = b), _.count)
}

// vim: set ts=2 sw=2 et:
