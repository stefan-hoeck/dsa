package efa.dsa.being.ui.spi

trait UILocal {
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
  def derived: String
  def equipment: String
  def equipmentPanel: String
  def generationPanel: String
  def languages: String
  def load: String
  def mainPanel: String
  def max: String
  def rest: String
  def spells: String
  def spellsPanel: String
  def start: String
  def talents: String
  def talentsPanel: String
  def total: String
  def used: String
  def weapons: String
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
  def derived = "Grundwerte"
  def equipment = "Ausrüstung"
  def equipmentPanel = "Ausrüstung"
  def generationPanel = "Erschaffung"
  def languages = "Sprachen und Schriften"
  def load = "Last"
  def mainPanel = "Grundwerte"
  def max = "Max."
  def rest = "Guthaben"
  def spells = "Zauberfertikeiten & Ritualkenntnisse"
  def spellsPanel = "Zauber"
  def start = "Startwert"
  def talents = "Allgemeine Talente"
  def talentsPanel = "Talente"
  def total = "Total"
  def used = "Gebraucht"
  def weapons = "Waffen & Schilde"
  def zones = "Körperzonen"
}

// vim: set ts=2 sw=2 et:
