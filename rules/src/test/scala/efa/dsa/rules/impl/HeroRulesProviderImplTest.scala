package efa.dsa.rules.impl

import efa.core.std.lookup.lookupHead
import efa.dsa.being.services.spi.HeroRulesProvider
import org.scalacheck._, Prop._

object HeroRulesProviderImplTest extends Properties("HeroRulesProviderImpl") {
  property("registered") = lookupHead[HeroRulesProvider] map (
    _.get.isInstanceOf[HeroRulesProviderImpl]) unsafePerformIO
}

// vim: set ts=2 sw=2 et:
