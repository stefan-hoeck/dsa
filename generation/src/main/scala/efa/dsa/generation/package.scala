package efa.dsa

import efa.core.Service
import efa.dsa.generation.spi.GenerationLocal
import efa.dsa.world.SkillMaps
import efa.rpg.core.RangeVals

package object generation extends RangeVals {
  lazy val loc = Service.unique[GenerationLocal](GenerationLocal)

  val Count = fullInfo(0, Int.MaxValue, "count")
  val Tap = fullInfo(-99, 999, "value")
  val Value = fullInfo(0, 99, "value")

  type SkillPrototypes = SkillMaps[SkillPrototype, SkillPrototype,
                                   SkillPrototype, SkillPrototype,
                                   SkillPrototype, SkillPrototype,
                                   SkillPrototype]

  type EquipmentPrototypes = SkillMaps[EquipmentPrototype, EquipmentPrototype,
                                       EquipmentPrototype, EquipmentPrototype,
                                       EquipmentPrototype, EquipmentPrototype,
                                       EquipmentPrototype]
}

// vim: set ts=2 sw=2 et:
