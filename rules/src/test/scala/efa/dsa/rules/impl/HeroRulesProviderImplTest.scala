package efa.dsa.rules.impl

import efa.core.std.lookup.lookupHead
import efa.dsa.being.services.spi.HeroRulesProvider
import org.scalacheck._, Prop._
import scalaz._, Scalaz._

object HeroRulesProviderImplTest extends Properties("HeroRulesProviderImpl") {
  property("registered") = lookupHead[HeroRulesProvider] map (
    _.get.isInstanceOf[HeroRulesProviderImpl]) unsafePerformIO

  property("ruleIds") = {
    val hrp = new HeroRulesProviderImpl
    val rp = new RulesProviderImpl

    val names = rp.get.toList >>= (_.allData.toList ∘ (_.name)) toSet

    hrp.get.toList ∀ (names apply _.id)
  }
}

// vim: set ts=2 sw=2 et:
