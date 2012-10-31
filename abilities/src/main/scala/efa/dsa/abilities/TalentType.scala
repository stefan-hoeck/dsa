package efa.dsa.abilities

import efa.core.{Localization, IsLocalized}
import efa.rpg.core.LocEnum

sealed abstract class TalentType (val loc: Localization) extends IsLocalized

object TalentType {
  import loc._

  case object Gabe extends TalentType(gabeLoc)
  case object Gesellschaft extends TalentType(gesellschaftLoc)
  case object Handwerk extends TalentType(handwerkLoc)
  case object Natur extends TalentType(naturLoc)
  case object Koerper extends TalentType(koerperLoc)
  case object Wissen extends TalentType(wissenLoc)
  
  val values = List[TalentType](
    Gabe, Gesellschaft, Handwerk, Natur, Koerper, Wissen)

  implicit lazy val TTLocEnum =
    LocEnum.tagged(Gabe, values.tail: _*)("talentType")
  implicit lazy val TTArb = TTLocEnum.arbitrary
}

// vim: set ts=2 sw=2 et:
