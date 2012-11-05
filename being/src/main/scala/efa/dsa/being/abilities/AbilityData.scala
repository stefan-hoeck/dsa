package efa.dsa.being.abilities

import scalaz.@>

trait AbilityData[A] {
  def dataL: A @> FeatData
  def nameL: A @> String = dataL >=> FeatData.name
  def isActiveL: A @> Boolean = dataL >=> FeatData.isActive
  def data (a: A): FeatData = dataL get a
  def id (a: A) = data(a).id
  def name (a: A) = data(a).name
  def isActive (a: A) = data(a).isActive
}

// vim: set ts=2 sw=2 et:
