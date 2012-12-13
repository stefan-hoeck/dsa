package efa.dsa.world

import scalaz._, Scalaz._

private[world] object RoundedDiv {

  private[world] def halfI (i: Int) = i / 2 + (((i & 1) ≟ 1) ? 1 | 0)

  private[world] def addI (a: Int, b: Int) = (a % b >= halfI (b)) ? 1 | 0

  private[world] def divI (a: Int, b: Int) = a / b + addI (a, b)

  private[world] def roundedDivI (a: Int, b: Int) = {
    def res = divI (a.abs, b.abs)
    (a < 0 ^ b < 0) ? -res | res
  }

  private[world] def halfL (i: Long) = i / 2L + (((i & 1L) ≟ 1L) ? 1L | 0L)

  private[world] def addL (a: Long, b: Long) = (a % b >= halfL (b)) ? 1L | 0L

  private[world] def divL (a: Long, b: Long) = a / b + addL (a, b)

  private[world] def roundedDivL (a: Long, b: Long) = {
    def res = divL (a.abs, b.abs)
    (a < 0L ^ b < 0L) ? -res | res
  }
}

// vim: set ts=2 sw=2 et:
