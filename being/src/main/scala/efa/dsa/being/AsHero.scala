package efa.dsa.being

import abilities.HasAbilities
import efa.rpg.core.Modified
import scalaz.@>

trait AsHero[A] extends Modified[A] with HasAbilities[A] {
  def attributes: A @> HeroAttributes
  def derived: A @> HeroDerived
}

object AsHero {
  def apply[A:AsHero]: AsHero[A] = implicitly
}

// vim: set ts=2 sw=2 et:
