package efa.dsa.being.services.spi

import dire.{SF, SIn}
import efa.core.Default.!!!
import efa.dsa.equipment.EquipmentItems

trait EquipmentProvider {
  def equipment: SIn[EquipmentItems]
}

object EquipmentProvider extends EquipmentProvider {
  def equipment = SF const !!![EquipmentItems]
}

// vim: set ts=2 sw=2 et:
