package efa.dsa.being.abilities

import scalaz._, Scalaz._

trait HasAbilities[-A] {
  def abilities (a: A): Abilities
  def advantages (a: A): Map[String,Advantage] = abilities(a).advantages
  def handicaps (a: A): Map[String,Handicap] = abilities(a).handicaps
  def feats (a: A): Map[String,Feat] = abilities(a).feats

  def hasAdvantage (name: String, a: A): Boolean =
    hasAbility(name, advantages(a))

  def hasFeat (name: String, a: A): Boolean =
    hasAbility(name, feats(a))

  def hasHandicap (name: String, a: A): Boolean =
    hasAbility(name, handicaps(a))

  private def getActive[A,B] (
    name: String, as: Map[String,Ability[A,B]]
  ) = as get name flatMap (a ⇒ a.isActive ? a.some | none)

  private def hasAbility[A,B] (
    name: String, as: Map[String,Ability[A,B]]
  ) = getActive(name, as).nonEmpty
}

trait HasAbilitiesFunctions {

  def hasAdvantage[A:HasAbilities] (name: String, a: A): Boolean =
    HasAbilities[A] hasAdvantage (name, a)

  def hasFeat[A:HasAbilities] (name: String, a: A): Boolean =
    HasAbilities[A] hasFeat (name, a)

  def hasHandicap[A:HasAbilities] (name: String, a: A): Boolean =
    HasAbilities[A] hasHandicap (name, a)
}

object HasAbilities extends HasAbilitiesFunctions {
  def apply[A:HasAbilities]: HasAbilities[A] = implicitly
}

// vim: set ts=2 sw=2 et:
