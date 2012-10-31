package efa.dsa

import efa.core.Service
import efa.dsa.world.spi.WorldLoc
import efa.rpg.core.RangeVals
import org.scalacheck.Arbitrary
import scalaz._, Scalaz._

package object world {
  implicit def LongRoundedDivisible (l: Long) = new RoundedDivisibleLong (l)
  implicit def IntRoundedDivisible (l: Int) = new RoundedDivisibleInt (l)
   
  lazy val loc = Service.unique[WorldLoc] (WorldLoc)

  val Be = RangeVals fullInfo (0, 999, "be")
  val Rs = RangeVals fullInfo (0, 999, "rs")
}

// vim: set ts=2 sw=2 et:
