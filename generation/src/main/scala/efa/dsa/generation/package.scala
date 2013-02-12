package efa.dsa

import efa.core.Service
import efa.dsa.generation.spi.GenerationLocal
import efa.dsa.world.{SkillMaps, AbilityMaps, EquipmentMaps}
import efa.rpg.core.RangeVals

package object generation extends RangeVals {
  lazy val loc = Service.unique[GenerationLocal](GenerationLocal)

  val Count = fullInfo(0, Int.MaxValue, "count")
  val Tap = fullInfo(-99, 999, "value")
  val Value = fullInfo(0, 99, "value")

  type AbilityChoice = Choice[AbilityPrototype]

  type EquipmentChoice = Choice[EquipmentPrototype]

  type SkillChoice = Choice[SkillPrototype]

  type AbilityPrototypes = AbilityMaps[AbilityPrototype, AbilityPrototype,
                                       AbilityPrototype]

  type EquipmentPrototypes =
    EquipmentMaps[EquipmentPrototype, EquipmentPrototype, EquipmentPrototype,
                  EquipmentPrototype, EquipmentPrototype, EquipmentPrototype,
                  EquipmentPrototype]

  type SkillPrototypes = SkillMaps[SkillPrototype, SkillPrototype,
                                   SkillPrototype, SkillPrototype,
                                   SkillPrototype, SkillPrototype,
                                   SkillPrototype]

  type AbilityChoices = AbilityMaps[AbilityChoice, AbilityChoice,
                                       AbilityChoice]

  type EquipmentChoices =
    EquipmentMaps[EquipmentChoice, EquipmentChoice, EquipmentChoice,
                  EquipmentChoice, EquipmentChoice, EquipmentChoice,
                  EquipmentChoice]

  type SkillChoices = SkillMaps[SkillChoice, SkillChoice,
                                   SkillChoice, SkillChoice,
                                   SkillChoice, SkillChoice,
                                   SkillChoice]
}

// vim: set ts=2 sw=2 et:
