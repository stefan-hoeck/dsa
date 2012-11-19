package efa.dsa.rules.impl

import efa.core.std.lookup.lookupHead
import efa.rpg.rules.spi.RulesProvider
import org.scalacheck._, Prop._

object RulesProviderImplTest extends Properties("RulesProviderImpl") {
  property("registered") = lookupHead[RulesProvider] map (
    _.get.isInstanceOf[RulesProviderImpl]) unsafePerformIO
}

// vim: set ts=2 sw=2 et:

