package efa.dsa.generation

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.{WithId, Util}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class SkillPrototype(parentId: Int, value: Int) {
  require(Tap validate value isRight)
}

object SkillPrototype extends Util {
  val default = SkillPrototype(0, 0)

  implicit val SkillPrototypeDefault = Default default default

  implicit val SkillPrototypeEqual = Equal.equalA[SkillPrototype]

  implicit val SkillPrototypeArbitrary =
    Arbitrary(^(a[Int], Tap.gen)(SkillPrototype.apply))

  implicit val SkillPrototypeToXml = new ToXml[SkillPrototype] {
    def fromXml (ns: Seq[Node]) =
      ^(ns.readTag[Int]("id"), Tap read ns)(SkillPrototype.apply)

    def toXml (a: SkillPrototype) = 
      ("id" xml a.parentId) ++ Tap.write(a.value)
  }
  
  implicit val SkillPrototypeWithId = withId[SkillPrototype](_.parentId)

  val parentId: SkillPrototype @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val value: SkillPrototype @> Int =
    Lens.lensu((a,b) ⇒ a.copy(value = b), _.value)
}

// vim: set ts=2 sw=2 et:
