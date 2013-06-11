package efa.dsa.abilities.services

import efa.dsa.abilities._
import efa.rpg.items.specs.EditableProps
import implicits._

object AdvantagePanelTest extends EditableProps[AdvantageItem]("AdvantagePanel")

object FeatPanelTest extends EditableProps[FeatItem]("FeatEditable")

object HandicapPanelTest extends EditableProps[HandicapItem]("HandicapEditable")

object LanguagePanelTest extends EditableProps[LanguageItem]("LanguageEditable")

object MeleeTalentPanelTest extends EditableProps[MeleeTalentItem]("MeleeEditable")

object RangedTalentPanelTest extends EditableProps[RangedTalentItem]("RangedEditable")

object RitualPanelTest extends EditableProps[RitualItem]("RitualEditable")

object ScripturePanelTest extends EditableProps[ScriptureItem]("ScriptureEditable")

object SpellPanelTest extends EditableProps[SpellItem]("SpellEditable")

object TalentPanelTest extends EditableProps[TalentItem]("TalentEditable")

// vim: set ts=2 sw=2 et nowrap:
