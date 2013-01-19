package efa.dsa.generation

import efa.core.{Default, ToXml}
import efa.rpg.core.Util
import org.scalacheck.{Gen, Arbitrary}, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class Choice[A] (values: NonEmptyList[Int], items: NonEmptyList[A])

object Choice extends Util {
  implicit def ChoiceDefault[A:Default]: Default[Choice[A]] =
    Default default Choice[A](0.wrapNel, !![A].wrapNel)

  implicit def ChoiceEqual[A:Equal]: Equal[Choice[A]] =
    Equal equalBy (Choice unapply _)

  implicit def ChoiceArbitrary[A:Arbitrary] = Arbitrary (
    for {
      v ← Gen choose (0, 100)
      vs ← Gen listOf Gen.choose (0, 100)
      arb ← a[A]
      as ← Gen.listOf (a[A])
    } yield (Choice(NonEmptyList(v, vs: _*), NonEmptyList(arb, as: _*)))
  )

  def values[A]: Choice[A] @> NonEmptyList[Int] =
    Lens.lensu((a,b) ⇒ a copy (values = b), _.values)

  def items[A]: Choice[A] @> NonEmptyList[A] =
    Lens.lensu((a,b) ⇒ a copy (items = b), _.items)
  
  implicit class Lenses[A,B](val l: A @> Choice[B]) extends AnyVal {
    def values = l >=> Choice.values[B]
    def items = l >=> Choice.items[B]
  }
}

// vim: set ts=2 sw=2 et:
