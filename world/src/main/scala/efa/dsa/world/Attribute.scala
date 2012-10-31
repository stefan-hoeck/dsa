package efa.dsa.world

import efa.core.{Localization, IsLocalized}
import efa.rpg.core.LocEnum

sealed abstract class Attribute(val loc: Localization) extends IsLocalized {
  override def toString = shortName
}

object Attribute {
  case object Ch extends Attribute(loc.chLoc)
  case object Ff extends Attribute(loc.ffLoc)
  case object Ge extends Attribute(loc.geLoc)
  case object In extends Attribute(loc.inLoc)
  case object Kk extends Attribute(loc.kkLoc)
  case object Kl extends Attribute(loc.klLoc)
  case object Ko extends Attribute(loc.koLoc)
  case object Mu extends Attribute(loc.muLoc)

  val values = List[Attribute](Ch, Ff, Ge, In, Kk, Kl, Ko, Mu)

  implicit lazy val AttributeLocEnum =
    LocEnum.values(values.head, values.tail: _*)

  implicit lazy val AttributeArbitrary = AttributeLocEnum.arbitrary
}

// vim: set ts=2 sw=2 et:
