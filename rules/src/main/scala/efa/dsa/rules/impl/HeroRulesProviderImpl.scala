package efa.dsa.rules.impl

import efa.dsa.being.services.spi.HeroRulesProvider
import scalaz.DList

class HeroRulesProviderImpl extends HeroRulesProvider {
  def get = DList()
}

// vim: set ts=2 sw=2 et:
