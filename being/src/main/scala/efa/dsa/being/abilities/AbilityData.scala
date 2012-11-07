package efa.dsa.being.abilities

import efa.core.Default
import scalaz.@>

trait AbilityData[A] extends Default[A] {
  def dataL: A @> FeatData
  def nameL: A @> String = dataL >=> FeatData.name
  def descL: A @> String = dataL >=> FeatData.desc
  def idL: A @> Int = dataL >=> FeatData.id
  def isActiveL: A @> Boolean = dataL >=> FeatData.isActive
  def data (a: A): FeatData = dataL get a
  def desc (a: A): String = descL get a
  def id (a: A) = data(a).id
  def name (a: A) = data(a).name
  def isActive (a: A) = data(a).isActive
}

object AbilityData {
  def apply[A:AbilityData]: AbilityData[A] = implicitly
}

// vim: set ts=2 sw=2 et:
