package efa.dsa.rules.impl

import efa.dsa.being.Hero
import efa.dsa.being.services.spi.HeroRulesProvider
import efa.dsa.rules.hero._
import efa.dsa.rules.humanoid._
import scalaz.DList

class HeroRulesProviderImpl extends HeroRulesProvider {
  def get =
    AdvantagesRules.all[Hero] ++
    HerausragendRule.all ++
    EquippedRules.all ++
    CalculatedRules.all ++
    WoundRules.all ++
    ExhaustionRules.all ++
    BeRules.all
}

// vim: set ts=2 sw=2 et:
