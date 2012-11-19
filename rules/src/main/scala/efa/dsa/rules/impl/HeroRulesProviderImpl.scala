package efa.dsa.rules.impl

import efa.dsa.being.Hero
import efa.dsa.being.services.spi.HeroRulesProvider
import efa.dsa.rules.hero.CalculatedRules
import scalaz.DList

class HeroRulesProviderImpl extends HeroRulesProvider {
  def get = CalculatedRules.all[Hero]
}

// vim: set ts=2 sw=2 et:
