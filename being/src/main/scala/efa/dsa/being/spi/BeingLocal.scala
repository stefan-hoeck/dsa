package efa.dsa.being.spi

import efa.core.Localization

trait BeingLocal {
  lazy val aeLoc = Localization("ae", ae, shortAe, ae)
  lazy val atLoc = Localization("at", at, shortAt, at)
  lazy val atfkLoc = Localization("atfk", atfk, shortAtFk, atfk)

  lazy val attributesLoc =
    Localization("attributes", attributes, shortAttributes, attributes)

  lazy val auLoc = Localization("au", au, shortAu, au)
  lazy val awLoc = Localization("aw", aw, shortAw, aw)
  lazy val beLoc = Localization("be", be, shortBe, be)

  lazy val carriedWeightLoc =
    new Localization("carriedWeight", carriedWeight)

  lazy val carryingCapacityLoc =
    new Localization("carryingCapacity", carryingCapacity)

  lazy val countLoc = Localization("count", count, shortCount, count)

  lazy val ebeLoc = Localization("ebe", ebe, shortEbe, ebe)

  lazy val equippedLoc =
    Localization("equipped", equipped, shortEquipped, equipped)

  lazy val fkLoc = Localization("fk", fk, shortFk, fk)
  lazy val gsLoc = Localization("gs", gs, shortGs, gs)

  lazy  val houseSpellLoc =
    Localization("houseSpell", houseSpell, shortHouseSpell, houseSpell)

  lazy val iniLoc = Localization("ini", ini, shortIni, ini)

  lazy val isActiveLoc =
    Localization("active", isActive, shortIsActive, descIsActive)

  lazy val keLoc = Localization("ke", ke, shortKe, ke)
  lazy val leLoc = Localization("le", le, shortLe, le)
  lazy val lhLoc = Localization("lh", lh, shortLh, descLh)
  lazy val mrLoc = Localization("mr", mr, shortMr, mr)
  lazy val mrBodyLoc = Localization("mrBody", mrBody, shortMrBody, mrBody)
  lazy val mrMindLoc = Localization("mrMind", mrMind, shortMrMind, mrMind)

  lazy val overstrainLoc = Localization("overstrain", overstrain,
    shortOverstrain, overstrain)

  lazy val paLoc = Localization("pa", pa, shortPa, pa)

  lazy val raisingCostLoc = Localization(
    "raisingCost", raisingCost, shortRaisingCost, descRaisingCost)

  lazy val representationLoc = Localization("representation",
    representation, shortRepresentation, representation)

  lazy val rhLoc = Localization("rh", rh, shortRh, descRh)
  lazy val rsLoc = Localization("rs", rs, shortRs, rs)

  lazy val specialExpLoc =
    Localization("specialExp", specialExp, shortSpecialExp, descSpecialExp)

  lazy val tawLoc =
    Localization("taw", taw, shortTaw, descTaw)

  lazy val wsLoc = Localization("ws", ws, shortWs, ws)

  def advantages: String
  def ae: String
  def at: String
  def atfk: String
  def attributes: String
  def au: String
  def aw: String
  def base: String
  def be: String
  def birthday: String
  def bought: String
  def carriedWeight: String
  def carryingCapacity: String
  def count: String
  def culture: String
  def descIsActive: String
  def descLh: String
  def descRaisingCost: String
  def descRh: String
  def descSpecialExp: String
  def descTaw: String
  def ebe: String
  def equipped: String
  def exhaustion: String
  def eyes: String
  def feats: String
  def fk: String
  def gender: String
  def gs: String
  def hair: String
  def handicaps: String
  def height: String
  def hero: String
  def houseSpell: String
  def ini: String
  def initial: String
  def isActive: String
  def ke: String
  def languages: String
  def le: String
  def lh: String
  def lower: String
  def meleeTalents: String
  def mr: String
  def mrBody: String
  def mrMind: String
  def nameExists (name: String): String
  def overstrain: String
  def pa: String
  def position: String
  def profession: String
  def race: String
  def raise: String
  def raisingCost: String
  def rangedTalents: String
  def raufen: String
  def representation: String
  def rh: String
  def ringen: String
  def rituals: String
  def rs: String
  def scriptures: String
  def shortAe: String
  def shortAt: String
  def shortAtFk: String
  def shortAttributes: String
  def shortAu: String
  def shortAw: String
  def shortBe: String
  def shortCount: String
  def shortEbe: String
  def shortEquipped: String
  def shortFk: String
  def shortGs: String
  def shortHouseSpell: String
  def shortIni: String
  def shortIsActive: String
  def shortKe: String
  def shortLe: String
  def shortLh: String
  def shortMr: String
  def shortMrBody: String
  def shortMrMind: String
  def shortOverstrain: String
  def shortPa: String
  def shortRaisingCost: String
  def shortRepresentation: String
  def shortRh: String
  def shortRs: String
  def shortSo: String
  def shortSpecialExp: String
  def shortTaw: String
  def shortWs: String
  def so: String
  def specialExp: String
  def spells: String
  def subdual: String
  def talent: String
  def talents: String
  def taw: String
  def title: String
  def unknownHandString (s: String): String
  def weight: String
  def wounds: String
  def ws: String
}

object BeingLocal extends BeingLocal {
  def advantages = "Vorteile"
  def ae = "Astralenergie"
  def at = "Attackewert"
  def atfk = "Attacke-/Fernkampfwert"
  def attributes = "Eigenschaften"
  def au = "Ausdauer"
  def aw = "Ausweichen"
  def base = "Grundwert"
  def be = "Behinderung"
  def birthday = "Geburtstag"
  def bought = "gekauft"
  def calculated = "berechnet"
  def carriedWeight = "Gewicht"
  def carryingCapacity = "Tragkraft"
  def count = "Anzahl"
  def culture = "Kultur"
  def descIsActive = "Derzeit aktiv?"
  def descLh = "in linker Hand getragen"
  def descRaisingCost = "Spalte in der Steigerungskostentabelle"
  def descRh = "in rechter Hand getragen"
  def descSpecialExp = "Hat eine spezielle Erfahrung in diesem Talent gemacht"
  def descTaw = "Talentwert (inklusive Modifikatoren)"
  def ebe = "Effektive Behinderung"
  def equipped = "getragen"
  def exhaustion = "Erschöpfung"
  def eyes = "Augenfarbe"
  def feats = "Sonderfertigkeiten"
  def fk = "Fernkampfwert"
  def gender = "Geschlecht"
  def gs = "Geschwindigkeit"
  def hair = "Haarfarbe"
  def handicaps = "Nachteile"
  def height = "Grösse"
  def hero = "Held"
  def houseSpell = "Hauszauber"
  def ini = "Initiative"
  def initial = "Startwert"
  def isActive = "aktiv"
  def ke = "Karmalenergie"
  def languages = "Sprachen"
  def le = "Lebensenergie"
  def lh = "Linke Hand"
  def lower = "Senken"
  def meleeTalents = "Nahkampftalente"
  def mr = "Magieresistenz"
  def mrBody = "Magieresistenz Körper"
  def mrMind = "Magieresistenz Geist"
  def nameExists (name: String) = "Name bereits vorhanden: " + name
  def overstrain = "Überanstrengung"
  def pa = "Paradewert"
  def position = "Stand"
  def profession = "Profession"
  def race = "Rasse"
  def raise = "Steigern"
  def raisingCost = "Spalte"
  def rangedTalents = "Fernkampftalente"
  def raufen = "Raufen"
  def representation = "Repräsentation"
  def rh = "Rechte Hand"
  def ringen = "Ringen"
  def rituals = "Rituale"
  def rs = "Rüstungsschutz"
  def scriptures = "Schriften"
  def shortAe = "AE"
  def shortAu = "AU"
  def shortAt = "AT"
  def shortAtFk = "AT/FK"
  def shortAttributes = "Eig."
  def shortAw = "AW"
  def shortBe = "BE"
  def shortCount = "Anz."
  def shortEbe = "EBE"
  def shortEquipped = "getr."
  def shortFk = "FK"
  def shortGs = "GS"
  def shortHouseSpell = "HZ"
  def shortIni = "INI"
  def shortIsActive = "aktiv"
  def shortKe = "KE"
  def shortLe = "LE"
  def shortLh = "LH"
  def shortMr = "MR"
  def shortMrBody = "MR K."
  def shortMrMind = "MR G."
  def shortOverstrain = "ÜA"
  def shortPa = "PA"
  def shortRaisingCost = "SK"
  def shortRepresentation = "Rep."
  def shortRh = "RH"
  def shortRs = "RS"
  def shortSo = "SO"
  def shortSpecialExp = "SE"
  def shortTaw = "TaW"
  def shortWs = "WS"
  def so = "Sozialstatus"
  def specialExp = "Spezielle Erfahrung"
  def spells = "Zauber"
  def start = "Startwert"
  def subdual = "Erschöpfungsschaden"
  def talent = "Talent"
  def talents = "Talente"
  def taw = "Talentwert"
  def title = "Titel"
  def unknownHandString (s: String) = "Unbekannte Eingabe: " + s
  def wounds = "Wunden"
  def ws = "Wundschwelle"
  def weight = "Gewicht"

}

// vim: set ts=2 sw=2 et:
