package efa.dsa.being

import efa.dsa.abilities.{AdvantageItem, HandicapItem, FeatItem}
import efa.rpg.core.RangeVals

package object abilities extends RangeVals {
  def Value = efa.dsa.generation.Value

  type Advantage = Ability[AdvantageItem,AdvantageData]

  type Handicap = Ability[HandicapItem,AdvantageData]

  type Feat = Ability[FeatItem,FeatData]

  private[abilities] def abilityData[A] (f: A ⇒ FeatData): AbilityData[A] =
    new AbilityData[A] { def data (a: A) = f(a) }
}

// vim: set ts=2 sw=2 et:
