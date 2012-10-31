package efa.dsa.test

import efa.dsa.abilities.AbilityItems
import efa.dsa.being.HeroData
import efa.dsa.equipment.EquipmentItems
import org.scalacheck.Arbitrary
import scalaz.Equal

case class CompleteData (
  hero: HeroData,
  abilities: AbilityItems,
  equipment: EquipmentItems
)

object CompleteData {
  implicit val CompleteDataEqual = Equal.equalA[CompleteData]

  implicit val CompleteDataArbitrary = Arbitrary(
    for {
      as ← AbilityGens.abilitiesGen
      es ← EquipmentGens.equipmentGen
      h ← HeroGens.heroDataGen(as, es)
    } yield CompleteData(h, as, es)
  )
}

// vim: set ts=2 sw=2 et:
