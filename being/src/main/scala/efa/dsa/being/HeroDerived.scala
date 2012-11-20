package efa.dsa.being

import efa.core.Default
import scalaz.{Equal, Lens, @>}

case class HeroDerived (
  maxBoughtAe: Long,
  maxBoughtAu: Long,
  maxBoughtLe: Long,
  maxBoughtMr: Long,
  iniBase: Long
)

object HeroDerived {
  lazy val default = HeroDerived(maxBought, maxBought, maxBought,
    maxBought, 0L)

  implicit lazy val HeroDerivedDefault = Default default default

  implicit lazy val HeroDerivedEqual = Equal.equalA[HeroDerived]

  val maxBoughtAe: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtAe = b), _.maxBoughtAe)

  val maxBoughtAu: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtAu = b), _.maxBoughtAu)

  val maxBoughtLe: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtLe = b), _.maxBoughtLe)

  val maxBoughtMr: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (maxBoughtMr = b), _.maxBoughtMr)

  val iniBase: HeroDerived @> Long =
    Lens.lensu((a,b) ⇒ a copy (iniBase = b), _.iniBase)
  
  case class HeroDerivedLenses[A] (l: A @> HeroDerived) {
    lazy val maxBoughtAe = l >=> HeroDerived.maxBoughtAe
    lazy val maxBoughtAu = l >=> HeroDerived.maxBoughtAu
    lazy val maxBoughtLe = l >=> HeroDerived.maxBoughtLe
    lazy val maxBoughtMr = l >=> HeroDerived.maxBoughtMr
    lazy val iniBase = l >=> HeroDerived.iniBase
  }
  
  implicit def ToLenses[A] (l: A @> HeroDerived) =
    HeroDerivedLenses[A](l)
}

// vim: set ts=2 sw=2 et:
