package efa.dsa.being.services.spi

import dire.{SF, SIn}
import efa.core.Default
import efa.dsa.equipment.EquipmentItems

trait EquipmentProvider {
  def equipment: SIn[EquipmentItems]
}

object EquipmentProvider extends EquipmentProvider {
  implicit val defImpl: Default[EquipmentProvider] = Default.default(this)
  def equipment = SF const Default.!!![EquipmentItems]
}

// vim: set ts=2 sw=2 et:
