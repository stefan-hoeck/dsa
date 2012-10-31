package efa.dsa.abilities

import efa.rpg.core.{DB, DBs}
import scalaz.{Equal, Lens, @>}

case class AbilityItems (
  advantages: DB[AdvantageItem],
  handicaps: DB[HandicapItem],
  feats: DB[FeatItem],
  languages: DB[LanguageItem],
  meleeTalents: DB[MeleeTalentItem],
  rangedTalents: DB[RangedTalentItem],
  rituals: DB[RitualItem],
  scriptures: DB[ScriptureItem],
  spells: DB[SpellItem],
  talents: DB[TalentItem]
)

object AbilityItems extends DBs {
  implicit val AbilityItemsEqual:Equal[AbilityItems] = Equal.equalA

  lazy val default = AbilityItems(db, db, db, db, db, db, db, db, db, db)

  val advantages: AbilityItems @> DB[AdvantageItem] =
    Lens.lensu((a,b) ⇒ a copy (advantages = b), _.advantages)

  val handicaps: AbilityItems @> DB[HandicapItem] =
    Lens.lensu((a,b) ⇒ a copy (handicaps = b), _.handicaps)

  val feats: AbilityItems @> DB[FeatItem] =
    Lens.lensu((a,b) ⇒ a copy (feats = b), _.feats)

  val languages: AbilityItems @> DB[LanguageItem] =
    Lens.lensu((a,b) ⇒ a copy (languages = b), _.languages)

  val meleeTalents: AbilityItems @> DB[MeleeTalentItem] =
    Lens.lensu((a,b) ⇒ a copy (meleeTalents = b), _.meleeTalents)

  val rangedTalents: AbilityItems @> DB[RangedTalentItem] =
    Lens.lensu((a,b) ⇒ a copy (rangedTalents = b), _.rangedTalents)

  val rituals: AbilityItems @> DB[RitualItem] =
    Lens.lensu((a,b) ⇒ a copy (rituals = b), _.rituals)

  val scriptures: AbilityItems @> DB[ScriptureItem] =
    Lens.lensu((a,b) ⇒ a copy (scriptures = b), _.scriptures)

  val spells: AbilityItems @> DB[SpellItem] =
    Lens.lensu((a,b) ⇒ a copy (spells = b), _.spells)

  val talents: AbilityItems @> DB[TalentItem] =
    Lens.lensu((a,b) ⇒ a copy (talents = b), _.talents)
}

// vim: set ts=2 sw=2 et:
