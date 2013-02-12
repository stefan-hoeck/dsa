package efa.dsa.generation

import efa.core.{Default, TaggedToXml, ToXml, Efa, UniqueIdL}, Efa._
import efa.rpg.core.Util
import org.scalacheck.{Gen, Arbitrary}, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class Choice[A](
    id: Int,
    name: String,
    values: NonEmptyList[Int],
    items: NonEmptyList[A])

object Choice extends Util {
  implicit def ChoiceDefault[A:Default]: Default[Choice[A]] =
    Default default Choice[A](0, "", 0.wrapNel, !![A].wrapNel)

  implicit def ChoiceEqual[A:Equal]: Equal[Choice[A]] =
    Equal equalBy (Choice unapply _)

  implicit def ChoiceIdL[A] = UniqueIdL lens idLens[A]

  implicit def ChoiceStringIdL[A] = UniqueIdL lens name[A]

  implicit def ChoiceArbitrary[A:Arbitrary] = Arbitrary (
    for {
      id   ← a[Int]
      name ← Gen.identifier
      v    ← Gen choose (0, 100)
      vs   ← Gen listOf Gen.choose(0, 100)
      arb  ← a[A]
      as   ← Gen listOf a[A]
    } yield
      Choice(id, name, NonEmptyList(v, vs: _*), NonEmptyList(arb, as: _*))
  )

  implicit def ChoiceToXml[A:TaggedToXml] = new TaggedToXml[Choice[A]] {
    val tag = "choice"
    val vToXml = ToXml.nelToXml[Int]("value")
    val aToXml = ToXml.nelToXml[A](TaggedToXml[A].tag)

    def fromXml(ns: Seq[Node]) = ^^^(
      ns.readTag[Int]("id"),
      ns.readTag[String]("name"),
      vToXml.readTag(ns, "values"),
      aToXml.readTag(ns, "items")
    )(Choice.apply)

    def toXml(c: Choice[A]) =
      ("id" xml c.id) ++
      ("name" xml c.name) ++
      vToXml.writeTag("values", c.values) ++ 
      aToXml.writeTag("items", c.items)
  }

  def idLens[A]: Choice[A] @> Int = Lens.lensu((a,b) ⇒ a.copy(id = b), _.id)
  
  def name[A]: Choice[A] @> String =
    Lens.lensu((a,b) ⇒ a.copy(name = b), _.name)
  
  def values[A]: Choice[A] @> NonEmptyList[Int] =
    Lens.lensu((a,b) ⇒ a copy (values = b), _.values)

  def items[A]: Choice[A] @> NonEmptyList[A] =
    Lens.lensu((a,b) ⇒ a copy (items = b), _.items)
  
  implicit class Lenses[A,B](val l: A @> Choice[B]) extends AnyVal {
    def id = l >=> Choice.idLens
    def name = l >=> Choice.name
    def values = l >=> Choice.values
    def items = l >=> Choice.items
  }
}

// vim: set ts=2 sw=2 et:
