package efa.dsa.abilities.services 

import dire.{SIn}
import efa.dsa.abilities._
import efa.rpg.items.controller.{ItemsInfo, ItemController, Factory}
import efa.rpg.items.spi.ItemsInfoProvider
import scalaz._, Scalaz._, effect.IO

private[services] object AbilitiesController extends Factory {
  import implicits._

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
  val advantageC = singleton[AdvantageItem](advantageNames)
  val handicapC = singleton[HandicapItem](handicapNames)
  val featC = singleton[FeatItem](featNames)
  val languageC = singleton[LanguageItem](languageNames)
  val scriptureC = singleton[ScriptureItem](scriptureNames)
  val talentC = singleton[TalentItem](talentNames)
  val meleeC = singleton[MeleeTalentItem](meleeNames)
  val rangedC = singleton[RangedTalentItem] (rangedNames)
  val spellC = singleton[SpellItem](spellNames)
  val ritualC = singleton[RitualItem](ritualNames)

  lazy val infos: Map[String, ItemsInfo] = Map (
    advantageNames._1 → advantageC.info,
    handicapNames._1 → handicapC.info,
    featNames._1 → featC.info,
    languageNames._1 → languageC.info,
    scriptureNames._1 → scriptureC.info,
    talentNames._1 → talentC.info,
    meleeNames._1 → meleeC.info,
    rangedNames._1 → rangedC.info,
    spellNames._1 → spellC.info,
    ritualNames._1 → ritualC.info
  )

  lazy val abilities: SIn[AbilityItems] =
    advantageC.dbIn ⊛
    handicapC.dbIn ⊛
    featC.dbIn ⊛
    languageC.dbIn ⊛
    meleeC.dbIn ⊛
    rangedC.dbIn ⊛
    ritualC.dbIn ⊛
    scriptureC.dbIn ⊛
    spellC.dbIn ⊛
    talentC.dbIn apply AbilityItems.apply
}

class AbilityItemsInfoProvider extends ItemsInfoProvider {
  def infos = AbilitiesController.infos
}

class AbilityProviderImpl
   extends efa.dsa.being.services.spi.AbilityProvider {
  def abilities = AbilitiesController.abilities
}

// vim: set ts=2 sw=2 et:
