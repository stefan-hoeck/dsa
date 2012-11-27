package efa.dsa.being.ui.spi

import efa.core.Localization

trait UILocal {
  lazy val priceLoc =
    Localization("price", price, shortPrice, descPrice)

  lazy val weightLoc =
    Localization("weight", weight, shortWeight, descWeight)

  def abilities: String
  def actual: String
  def ap: String
  def armor: String
  def attackModes: String
  def attributes: String
  def basePanel: String
  def battlePanel: String
  def battleTalents: String
  def battleValues: String
  def bought: String
  def carryingCapacity: String
  def carriedWeight: String
  def descPrice: String
  def descWeight: String
  def derived: String
  def equipment: String
  def equipmentPanel: String
  def generationPanel: String
  def languages: String
  def load: String
  def mainPanel: String
  def max: String
  def maxBought (a: String): String
  def price: String
  def rest: String
  def shortPrice: String
  def shortWeight: String
  def spells: String
  def spellsPanel: String
  def start: String
  def talents: String
  def talentsPanel: String
  def total: String
  def used: String
  def weapons: String
  def weight: String
  def zones: String
}

object UILocal extends UILocal {
  def abilities = "Vor-, Nachteile und Sonderfertigkeiten"
  def actual = "Aktuell"
  def ap = "Erfahrung"
  def armor = "Rüstungen"
  def attackModes = "Attackemodi"
  def attributes = "Eigenschaften"
  def basePanel = "Held"
  def battlePanel = "Kampfwerte"
  def battleTalents = "Kampftalente"
  def battleValues = "Kampfwerte"
  def bought = "Gekauft"
  def carryingCapacity = "Tragkraft [st]"
  def carriedWeight = "Getragenes Gewicht [st]"
  def descWeight = "Gewicht in Unzen"
  def descPrice = "Preis in Silbertalern"
  def derived = "Grundwerte"
  def equipment = "Ausrüstung"
  def equipmentPanel = "Ausrüstung"
  def generationPanel = "Erschaffung"
  def languages = "Sprachen und Schriften"
  def load = "Last"
  def mainPanel = "Grundwerte"
  def max = "Max."
  def maxBought (s: String) = "Max: " + s
  def price = "Preis"
  def rest = "Guthaben"
  def shortPrice = "[S]"
  def shortWeight = "[u]"
  def spells = "Zauberfertikeiten & Ritualkenntnisse"
  def spellsPanel = "Zauber"
  def start = "Startwert"
  def talents = "Allgemeine Talente"
  def talentsPanel = "Talente"
  def total = "Total"
  def used = "Gebraucht"
  def weapons = "Waffen & Schilde"
  def weight = "Gewicht"
  def zones = "Körperzonen"
}

// vim: set ts=2 sw=2 et:
