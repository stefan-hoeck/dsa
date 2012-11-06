package efa.dsa.being.abilities

import scalaz.@>

trait AbilityData[A] {
  def dataL: A @> FeatData
  def nameL: A @> String = dataL >=> FeatData.name
  def descL: A @> String = dataL >=> FeatData.desc
  def isActiveL: A @> Boolean = dataL >=> FeatData.isActive
  def data (a: A): FeatData = dataL get a
  def id (a: A) = data(a).id
  def name (a: A) = data(a).name
  def isActive (a: A) = data(a).isActive
}

object AbilityData {
  def apply[A:AbilityData]: AbilityData[A] = implicitly
}

// vim: set ts=2 sw=2 et:
