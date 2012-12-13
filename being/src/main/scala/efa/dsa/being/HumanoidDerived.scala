package efa.dsa.being

import efa.core.Default
import scalaz.{Equal, Lens, @>}

case class HeroDerived (
  maxBoughtAe: Long,
  maxBoughtAu: Long,
  maxBoughtKe: Long,
  maxBoughtLe: Long,
  maxBoughtMr: Long,
  iniBase: Long
)

object HeroDerived {
  lazy val default = HeroDerived(maxBought, maxBought, maxBought,
    maxBought, maxBought, 0L)

  implicit lazy val HeroDerivedDefault = Default default default

  implicit lazy val HeroDerivedEqual = Equal.equalA[HeroDerived]

  val maxBoughtAe: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtAe = b), _.maxBoughtAe)

  val maxBoughtAu: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtAu = b), _.maxBoughtAu)

  val maxBoughtKe: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtKe = b), _.maxBoughtKe)

  val maxBoughtLe: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtLe = b), _.maxBoughtLe)

  val maxBoughtMr: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtMr = b), _.maxBoughtMr)

  val iniBase: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (iniBase = b), _.iniBase)
  
  implicit class HeroDerivedLenses[A] (val l: A @> HeroDerived) extends AnyVal {
    def maxBoughtAe = l >=> HeroDerived.maxBoughtAe
    def maxBoughtAu = l >=> HeroDerived.maxBoughtAu
    def maxBoughtKe = l >=> HeroDerived.maxBoughtKe
    def maxBoughtLe = l >=> HeroDerived.maxBoughtLe
    def maxBoughtMr = l >=> HeroDerived.maxBoughtMr
    def iniBase = l >=> HeroDerived.iniBase
  }
}

// vim: set ts=2 sw=2 et:
