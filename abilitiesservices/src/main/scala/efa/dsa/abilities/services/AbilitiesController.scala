package efa.dsa.abilities.services 

import efa.dsa.abilities._
import efa.react.{SIn, sTrans}
import efa.rpg.items.controller.{ControllerFactory, ItemsInfo}
import efa.rpg.items.spi.ItemsInfoProvider
import scalaz._, Scalaz._, effect.IO

private[services] object AbilitiesController extends ControllerFactory {
  val advantageNames = ("Vorteile", "dsa_advantage_item")
  val handicapNames = ("Nachteile", "dsa_disadvantage_item")
  val featNames = ("Sonderfertigkeiten", "dsa_feat_item")
  val languageNames = ("Sprachen", "dsa_language_item")
  val scriptureNames = ("Schriften", "dsa_scripture_item")
  val talentNames = ("Talente", "dsa_talent_item")
  val meleeNames = ("NahkampfTalente", "dsa_meleeTalent_item")
  val rangedNames = ("FernkampfTalente", "dsa_rangedTalent_item")
  val spellNames = ("Zauber", "dsa_spell_item")
  val ritualNames = ("Rituale", "dsa_ritual_item")
  val advantageC = cached[AdvantageItem](advantageNames)
  val handicapC = cached[HandicapItem](handicapNames)
  val featC = cached[FeatItem](featNames)
  val languageC = cached[LanguageItem](languageNames)
  val scriptureC = cached[ScriptureItem](scriptureNames)
  val talentC = cached[TalentItem](talentNames)
  val meleeC = cached[MeleeTalentItem](meleeNames)
  val rangedC = cached[RangedTalentItem] (rangedNames)
  val spellC = cached[SpellItem](spellNames)
  val ritualC = cached[RitualItem](ritualNames)

  lazy val infos: Map[String, IO[ItemsInfo]] = Map (
    advantageNames._1 → advantageC.map(_.info).get,
    handicapNames._1 → handicapC.map(_.info).get,
    featNames._1 → featC.map(_.info).get,
    languageNames._1 → languageC.map(_.info).get,
    scriptureNames._1 → scriptureC.map(_.info).get,
    talentNames._1 → talentC.map(_.info).get,
    meleeNames._1 → meleeC.map(_.info).get,
    rangedNames._1 → rangedC.map(_.info).get,
    spellNames._1 → spellC.map(_.info).get,
    ritualNames._1 → ritualC.map(_.info).get
  )

  lazy val abilities: SIn[AbilityItems] = {
    val cachedAbilityItems =
      signal(advantageC) ⊛
      signal(handicapC) ⊛
      signal(featC) ⊛
      signal(languageC) ⊛
      signal(meleeC) ⊛
      signal(rangedC) ⊛
      signal(ritualC) ⊛
      signal(scriptureC) ⊛
      signal(spellC) ⊛
      signal(talentC) apply AbilityItems.apply

      sTrans inIO (cachedAbilityItems.get >>= (_.go map (_._2))) 
  }
}

class AbilityItemsInfoProvider extends ItemsInfoProvider {
  def infos = AbilitiesController.infos
}

class AbilityProviderImpl
   extends efa.dsa.being.services.spi.AbilityProvider {
  def abilities = AbilitiesController.abilities
}

// vim: set ts=2 sw=2 et:
