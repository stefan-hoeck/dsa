package efa.dsa.abilities.spi

import efa.core.{Localization, Default}

trait AbilitiesLocal {
  //abilities
  def gp: String
  def ap: String
  def featType: String

  //skills
  def baseTalent: String
  def raisingCost: String
  def ebe: String
  def family: String
  def attributes: String
  def complexity: String
  def talentType: String

  //default names
  def advantage: String
  def feat: String
  def handicap: String
  def language: String
  def meleeTalent: String
  def rangedTalent: String
  def ritual: String
  def scripture: String
  def spell: String
  def talent: String

  final def gabeLoc = new Localization ("gabe", gabe)
  final def gesellschaftLoc = new Localization ("gesellschaft", gesellschaft)
  final def handwerkLoc = new Localization ("handwerk", handwerk)
  final def koerperLoc = new Localization ("koerper", koerper)
  final def naturLoc = new Localization ("natur", natur)
  final def wissenLoc = new Localization ("wissen", wissen)
  def gabe: String
  def gesellschaft: String
  def handwerk: String
  def koerper: String
  def natur: String
  def wissen: String

  def invalidAttributes: String
}

object AbilitiesLocal extends AbilitiesLocal {
  implicit val defImpl: Default[AbilitiesLocal] = Default.default(this)
  def gp = "GP"
  def ap = "AP"
  def featType = "Typ"

  def baseTalent = "Basistalent"
  def raisingCost = "Steigerungsklasse"
  def ebe = "EBE"
  def family = "Sprachfamilie"
  def attributes = "Eigenschaften"
  def complexity = "Komplexität"
  def gabe = "Gabe"
  def gesellschaft = "Gesellschaft"
  def handwerk = "Handwerk"
  def koerper = "Körper"
  def natur = "Natur"
  def wissen = "Wissen"
  def talentType = "Talenttyp"

  def invalidAttributes = "Unbekanntes Format für Eigenschaften"

  def advantage = "Vorteil"
  def feat = "Sonderfertigkeit"
  def handicap = "Nachteil"
  def language = "Sprache"
  def meleeTalent = "Nahkampftalent"
  def rangedTalent = "Fernkampftalent"
  def ritual = "Ritual"
  def scripture = "Schrift"
  def spell = "Zauber"
  def talent = "Talent"
}

// vim: set ts=2 sw=2 et:
