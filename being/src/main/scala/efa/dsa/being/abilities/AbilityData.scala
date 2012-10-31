package efa.dsa.being.abilities

trait AbilityData[-A] {
  def data (a: A): FeatData
  def id (a: A) = data(a).id
  def name (a: A) = data(a).name
  def isActive (a: A) = data(a).isActive
}

// vim: set ts=2 sw=2 et:
