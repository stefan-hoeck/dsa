package efa.dsa.being.abilities

import scalaz._, Scalaz._

trait HasAbilities[-A] {
  def abilities (a: A): Abilities
  def advantages (a: A): Map[String,Advantage] = abilities(a).advantages
  def handicaps (a: A): Map[String,Handicap] = abilities(a).handicaps
  def feats (a: A): Map[String,Feat] = abilities(a).feats

  def activeFeat (name: String, a: A): Option[Feat] =
    getActive(name, feats(a))

  def advantageValue (name: String, a: A): Option[Int] =
    getActive(name, advantages(a)) map (_.data.value)

  def handicapValue (name: String, a: A): Option[Int] =
    getActive(name, handicaps(a)) map (_.data.value)

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
  import efa.dsa.being.abilities.{HasAbilities ⇒ HA}

  def activeFeat[A:HA] (name: String, a: A): Option[Feat] =
    HA[A] activeFeat (name, a)

  def advantageValue[A:HA] (name: String, a: A): Option[Int] =
    HA[A] advantageValue (name, a)

  def handicapValue[A:HA] (name: String, a: A): Option[Int] =
    HA[A] handicapValue (name, a)

  def hasAdvantage[A:HA] (name: String, a: A): Boolean =
    HA[A] hasAdvantage (name, a)

  def hasFeat[A:HA] (name: String, a: A): Boolean =
    HA[A] hasFeat (name, a)

  def hasHandicap[A:HA] (name: String, a: A): Boolean =
    HA[A] hasHandicap (name, a)
}

object HasAbilities extends HasAbilitiesFunctions {
  def apply[A:HasAbilities]: HasAbilities[A] = implicitly
}

// vim: set ts=2 sw=2 et:
