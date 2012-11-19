package efa.dsa.being.abilities

trait HasAbilities[-A] {
  def abilities (a: A): Abilities
  def advantages (a: A): Map[String,Advantage] = abilities(a).advantages
  def handicaps (a: A): Map[String,Handicap] = abilities(a).handicaps
  def feats (a: A): Map[String,Feat] = abilities(a).feats
}

object HasAbilities {
  def apply[A:HasAbilities]: HasAbilities[A] = implicitly
}

// vim: set ts=2 sw=2 et:
