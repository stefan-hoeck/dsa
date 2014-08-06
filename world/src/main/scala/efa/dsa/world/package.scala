package efa.dsa

import efa.core.Service
import efa.dsa.world.spi.WorldLoc
import efa.rpg.core.RangeVals
import org.scalacheck.Arbitrary
import scalaz._, Scalaz._

package object world {
  type StringMap[+A] = Map[String,A]

  implicit class RoundedDivisibleInt (val value: Int) extends AnyVal {
    def rdiv (d: Int): Int = RoundedDiv roundedDivI (value, d)
  }

  implicit class RoundedDivisibleLong (val value: Long) extends AnyVal { 
    def rdiv (d: Long): Long = RoundedDiv roundedDivL (value, d)
  }
   
  lazy val loc = Service.unique[WorldLoc]

  val Be = RangeVals fullInfo (0, 999, "be")
  val Rs = RangeVals fullInfo (0, 999, "rs")
}

// vim: set ts=2 sw=2 et:
