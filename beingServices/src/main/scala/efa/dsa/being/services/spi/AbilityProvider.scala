package efa.dsa.being.services.spi

import dire.{SIn, SF}
import efa.core.Default
import efa.dsa.abilities.AbilityItems

trait AbilityProvider {
  def abilities: SIn[AbilityItems]
}

object AbilityProvider extends AbilityProvider {
  implicit val defImpl: Default[AbilityProvider] = Default.default(this)
  def abilities = SF const AbilityItems.default
}

// vim: set ts=2 sw=2 et:
