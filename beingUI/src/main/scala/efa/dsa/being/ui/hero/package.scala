package efa.dsa.being.ui

import efa.dsa.being.{Hero, HeroData, HeroBaseData ⇒ HBD}
import efa.dsa.being.abilities.{Abilities, AbilityDatas}
import efa.dsa.being.equipment.Equipments
import efa.dsa.being.skills.Skills

package object hero {
  type AbilitiesPanel = NP[Abilities,AbilityDatas]
  type EquipmentPanel = NP[Equipments,HeroData]
  type SpellsPanel = NP[Skills,HeroData]
}

// vim: set ts=2 sw=2 et:
