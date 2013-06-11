package efa.dsa.being.services.spi

import dire.{SIn, SF}
import efa.dsa.abilities.AbilityItems

trait AbilityProvider {
  def abilities: SIn[AbilityItems]
}

object AbilityProvider extends AbilityProvider {
  def abilities = SF const AbilityItems.default
}

// vim: set ts=2 sw=2 et:
