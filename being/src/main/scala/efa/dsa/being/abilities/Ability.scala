package efa.dsa.being.abilities

import efa.rpg.core.RpgItem
import scalaz.{Equal, Scalaz}

case class Ability[A,B](item: A, data: B) (implicit RI: RpgItem[A]) {
  def name = RI name item
  def id = RI id item
}

object Ability {
  implicit def AbilityEqual[A:Equal,B:Equal] = Equal.equalA[Ability[A,B]]
}

// vim: set ts=2 sw=2 et:
