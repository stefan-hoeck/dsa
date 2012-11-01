package efa.dsa.equipment.services

import efa.core.std.lookup.lookupHead
import efa.dsa.being.services.spi.EquipmentProvider
import org.scalacheck._, Prop._

object EquipmentProviderImplTest extends Properties("EquipmentProviderImpl") {
  property("registered") = lookupHead[EquipmentProvider] map (
    _.get.isInstanceOf[EquipmentProviderImpl]) unsafePerformIO
}

// vim: set ts=2 sw=2 et:
