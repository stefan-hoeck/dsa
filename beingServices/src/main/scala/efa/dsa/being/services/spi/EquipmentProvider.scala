package efa.dsa.being.services.spi

import efa.react.SIn
import efa.dsa.equipment.EquipmentItems
import scalaz._, Scalaz._

trait EquipmentProvider {
  def equipment: SIn[EquipmentItems]
}

object EquipmentProvider extends EquipmentProvider {
  def equipment = EquipmentItems.default.η[SIn]
}

// vim: set ts=2 sw=2 et:
