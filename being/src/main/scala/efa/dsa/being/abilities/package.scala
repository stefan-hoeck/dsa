package efa.dsa.being

import efa.dsa.abilities.{AdvantageItem, HandicapItem, FeatItem}
import efa.rpg.core.RangeVals
import scalaz.@>

package object abilities extends RangeVals {
  def Value = efa.dsa.generation.Value

  type Advantage = Ability[AdvantageItem,AdvantageData]

  type Handicap = Ability[HandicapItem,AdvantageData]

  type Feat = Ability[FeatItem,FeatData]

  private[abilities] def abilityData[A] (l: A @> FeatData): AbilityData[A] =
    new AbilityData[A] { val dataL = l}
}

// vim: set ts=2 sw=2 et:
