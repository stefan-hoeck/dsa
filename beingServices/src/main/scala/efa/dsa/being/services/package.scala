package efa.dsa.being

import efa.core.Efa._
import efa.core.Service.unique
import efa.dsa.abilities.AbilityItems
import efa.dsa.being.services.spi._
import efa.dsa.equipment.EquipmentItems
import efa.io.IOCached
import efa.react.SIn
import efa.rpg.being.UIInfo
import efa.rpg.rules.Rule
import org.openide.util.{Lookup ⇒ L}
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

  lazy val heroRules: IOCached[HeroRules] =
    IOCached(provide[HeroRule,HeroRulesProvider])

  lazy val heroInfo: IOCached[UIInfo[HeroData,Hero]] =
    IOCached(provide[IO[HeroInfo],UIProvider] map (_.suml) μ)
}

// vim: set ts=2 sw=2 et:
