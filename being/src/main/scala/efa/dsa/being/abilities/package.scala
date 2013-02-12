package efa.dsa.being

import efa.dsa.abilities.{AdvantageItem, HandicapItem, FeatItem}
import efa.dsa.world.AbilityMaps
import efa.rpg.core.RangeVals
import scalaz.@>

package object abilities {
  def Value = efa.dsa.generation.Value

  type Advantage = Ability[AdvantageItem,AdvantageData]

  type Handicap = Ability[HandicapItem,AdvantageData]

  type Feat = Ability[FeatItem,FeatData]

  type AbilityDatas = AbilityMaps[AdvantageData, AdvantageData, FeatData]

  type Abilities = AbilityMaps[Advantage, Handicap, Feat]

  private[abilities] def abilityData[A] (l: A @> FeatData, a: A)
  : AbilityData[A] = new AbilityData[A] {
    val dataL = l
    val default = a
  }
}

// vim: set ts=2 sw=2 et:
