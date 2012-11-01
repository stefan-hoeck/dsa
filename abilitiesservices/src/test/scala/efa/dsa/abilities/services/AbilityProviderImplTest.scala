package efa.dsa.abilities.services

import efa.core.std.lookup.lookupHead
import efa.dsa.being.services.spi.AbilityProvider
import org.scalacheck._, Prop._

object AbilityProviderImplTest extends Properties("AbilityProviderImpl") {
  property("registered") = lookupHead[AbilityProvider] map (
    _.get.isInstanceOf[AbilityProviderImpl]) unsafePerformIO
}

// vim: set ts=2 sw=2 et:
