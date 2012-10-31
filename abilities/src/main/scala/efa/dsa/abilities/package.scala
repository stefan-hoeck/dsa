package efa.dsa

import efa.core.{Service, Validators, Efa}, Efa._
import efa.dsa.abilities.spi.AbilitiesLocal
import efa.rpg.core.RangeVals
import org.scalacheck.Gen

package object abilities extends RangeVals {
  lazy val loc = Service.unique[AbilitiesLocal] (AbilitiesLocal)

  val Gp = fullInfo(0, 99, "gp")
  val Ap = fullInfo(0, 99999, "ap")
  val Complexity = fullInfo(0, 30, "complexity")

  val BaseTalent = xmlInfo[Boolean](Validators.dummy, "baseTalent")
}

// vim: set ts=2 sw=2 et:
