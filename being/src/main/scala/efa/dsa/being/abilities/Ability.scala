package efa.dsa.being.abilities

import efa.core.{UniqueId, EndoVal, Validators}
import efa.dsa.being.loc
import efa.rpg.core.{RpgItem, Described}
import scalaz._, Scalaz._

case class Ability[A,B](item: A, data: B, names: Set[String]) (
  implicit RI: RpgItem[A], AD: AbilityData[B]
) {
  def name = AD name data
  def id = RI id item
  def desc = AD desc data
  def shortDesc = RI shortDesc item
  def fullDesc = RI fullDesc item
  def isActive = AD isActive data

  lazy val nameVal: EndoVal[String] = Validators.endo (
    _ match {
      case x if x ≟ name ⇒ x.right
      case x if names(x) ⇒ (loc nameExists x).wrapNel.left
      case x ⇒ x.right
    }
  )
}

object Ability {
  implicit def AbilityEqual[A:Equal,B:Equal] = Equal.equalA[Ability[A,B]]

  implicit def AbilityItem[A,B] =
    new Described[Ability[A,B]] with UniqueId[Ability[A,B],String]{
      def name (a: Ability[A,B]) = a.name
      def id (a: Ability[A,B]) = name(a)
      def desc (a: Ability[A,B]) = a.desc
      def shortDesc (a: Ability[A,B]) = a.shortDesc
      def fullDesc (a: Ability[A,B]) = a.fullDesc
    }
}

// vim: set ts=2 sw=2 et:
