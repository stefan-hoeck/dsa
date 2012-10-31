package efa.dsa.abilities

import efa.dsa.abilities.services.ui._
import efa.rpg.items.controller.editable

package object services {
  implicit val AdvantageE = editable(AdvantagePanel.apply)
  implicit val FeatE = editable(FeatPanel.apply)
  implicit val HandicapE = editable(HandicapPanel.apply)
  implicit val LanguageE = editable(LanguagePanel.apply)
  implicit val MeleeTalentE = editable(MeleePanel.apply)
  implicit val RangedTalentE = editable(RangedPanel.apply)
  implicit val RitualE = editable(RitualPanel.apply)
  implicit val ScriptureE = editable(ScripturePanel.apply)
  implicit val SpellE = editable(SpellPanel.apply)
  implicit val TalentE = editable(TalentPanel.apply)
}

// vim: set ts=2 sw=2 et:
