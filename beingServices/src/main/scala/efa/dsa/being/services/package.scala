package efa.dsa.being

import dire.SIn
import efa.core.Efa._
import efa.core.Service.unique
import efa.dsa.abilities.AbilityItems
import efa.dsa.being.services.spi._
import efa.dsa.equipment.EquipmentItems
import efa.rpg.rules.Rule
import efa.rpg.being.UIInfo
import scalaz._, Scalaz._, effect.IO

package object services {
  type HeroRule = Rule[Hero]

  type HeroRules = List[HeroRule]

  type HeroInfo = UIInfo[HeroData,Hero]

  lazy val abilities: SIn[AbilityItems] =
    unique[AbilityProvider](AbilityProvider).abilities

  lazy val equipment: SIn[EquipmentItems] =
    unique[EquipmentProvider](EquipmentProvider).equipment

  lazy val world: SIn[World] = abilities ⊛ equipment apply World

  lazy val heroRules: IO[HeroRules] = provide[HeroRule,HeroRulesProvider]

  lazy val heroInfo: IO[UIInfo[HeroData,Hero]] =
    provide[IO[HeroInfo],UIProvider] >>= (_.suml)
}

// vim: set ts=2 sw=2 et:
