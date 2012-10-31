package efa.dsa.being

import efa.core.Default
import scalaz.Equal

case class HeroDerived (
  maxBoughtAe: Long,
  maxBoughtAu: Long,
  maxBoughtLe: Long,
  maxBoughtMr: Long,
  iniBase: Long
)

object HeroDerived {
  lazy val default = HeroDerived(0L, 0L, 0L, 0L, 0L)

  implicit lazy val HeroDerivedDefault = Default default default

  implicit lazy val HeroDerivedEqual = Equal.equalA[HeroDerived]
}

// vim: set ts=2 sw=2 et:
