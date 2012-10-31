package efa.dsa.being.services.spi

import efa.react.SIn
import efa.dsa.abilities.AbilityItems
import scalaz._, Scalaz._

trait AbilityProvider {
  def abilities: SIn[AbilityItems]
}

object AbilityProvider extends AbilityProvider {
  def abilities = AbilityItems.default.η[SIn]
}

// vim: set ts=2 sw=2 et:
