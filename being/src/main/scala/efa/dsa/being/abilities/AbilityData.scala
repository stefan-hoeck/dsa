package efa.dsa.being.abilities

import efa.core.{Default, UniqueIdL, NamedL}
import scalaz.@>

trait AbilityData[A]
   extends Default[A] 
   with UniqueIdL[A,String] 
   with NamedL[A] {
  def dataL: A @> FeatData
  lazy val nameL: A @> String = dataL >=> FeatData.name
  lazy val descL: A @> String = dataL >=> FeatData.desc
  lazy val parentIdL: A @> Int = dataL >=> FeatData.parentId
  lazy val isActiveL: A @> Boolean = dataL >=> FeatData.isActive
  def idL = nameL
  def data (a: A): FeatData = dataL get a
  def desc (a: A): String = descL get a
  def isActive (a: A) = data(a).isActive
  def parentId (a: A): Int = data (a).parentId
}

object AbilityData {
  def apply[A:AbilityData]: AbilityData[A] = implicitly
}

// vim: set ts=2 sw=2 et:
