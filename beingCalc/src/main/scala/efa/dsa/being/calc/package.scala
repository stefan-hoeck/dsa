package efa.dsa.being

import efa.core.Service
import efa.dsa.abilities.AbilityItems
import efa.dsa.being.equipment.{AttackMode, Hands}
import efa.dsa.being.calc.spi.BeingCalcLocal
import efa.dsa.equipment.EquipmentItems
import scalaz._, Scalaz._

package object calc {
  lazy val loc = Service.unique[BeingCalcLocal](BeingCalcLocal)

  def calcHero (hd: HeroData, ai: AbilityItems, ei: EquipmentItems): Hero = {
    val es = EquipmentLinker heroEquipment (hd, ei)
    val hands = hd.humanoid.hands.toHands(es) | Hands.Empty

    Hero (
      AbilityLinker heroAbilities (hd, ai),
      AttackMode fromHands hands,
      HeroAttributes fromHeroData hd,
      hd,
      HeroDerived.default,
      es,
      hd.modifiers,
      SkillLinker heroSkills (hd, ai)
    )
  }
}

// vim: set ts=2 sw=2 et:
