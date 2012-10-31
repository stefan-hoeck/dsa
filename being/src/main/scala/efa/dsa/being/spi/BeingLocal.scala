package efa.dsa.being.spi

import efa.core.Localization

trait BeingLocal {
  lazy val aeLoc = Localization("ae", ae, shortAe, ae)
  lazy val atLoc = Localization("at", at, shortAt, at)
  lazy val atfkLoc = Localization("atfk", atfk, shortAtFk, atfk)
  lazy val auLoc = Localization("au", au, shortAu, au)
  lazy val awLoc = Localization("aw", aw, shortAw, aw)
  lazy val beLoc = Localization("be", be, shortBe, be)
  lazy val carriedWeightLoc =
    new Localization("carriedWeight", carriedWeight)
  lazy val carryingCapacityLoc =
    new Localization("carryingCapacity", carryingCapacity)
  lazy val fkLoc = Localization("fk", fk, shortFk, fk)
  lazy val gsLoc = Localization("gs", gs, shortGs, gs)
  lazy val iniLoc = Localization("ini", ini, shortIni, ini)
  lazy val keLoc = Localization("ke", ke, shortKe, ke)
  lazy val leLoc = Localization("le", le, shortLe, le)
  lazy val mrLoc = Localization("mr", mr, shortMr, mr)
  lazy val mrBodyLoc = Localization("mrBody", mrBody, shortMrBody, mrBody)
  lazy val mrMindLoc = Localization("mrMind", mrMind, shortMrMind, mrMind)
  lazy val overstrainLoc = Localization("overstrain", overstrain,
    shortOverstrain, overstrain)
  lazy val paLoc = Localization("pa", pa, shortPa, pa)
  lazy val rsLoc = Localization("rs", rs, shortRs, rs)
  lazy val tpLoc = Localization("tp", tp, shortTp, tp)
  lazy val wsLoc = Localization("ws", ws, shortWs, ws)

  def ae: String
  def at: String
  def atfk: String
  def au: String
  def aw: String
  def base: String
  def be: String
  def bought: String
  def carriedWeight: String
  def carryingCapacity: String
  def fk: String
  def gs: String
  def hero: String
  def ini: String
  def initial: String
  def ke: String
  def le: String
  def mr: String
  def mrBody: String
  def mrMind: String
  def overstrain: String
  def pa: String
  def raufen: String
  def ringen: String
  def rs: String
  def shortAe: String
  def shortAt: String
  def shortAtFk: String
  def shortAu: String
  def shortAw: String
  def shortBe: String
  def shortFk: String
  def shortGs: String
  def shortIni: String
  def shortKe: String
  def shortLe: String
  def shortMr: String
  def shortMrBody: String
  def shortMrMind: String
  def shortOverstrain: String
  def shortPa: String
  def shortRs: String
  def shortTp: String
  def shortWs: String
  def subdual: String
  def tp: String
  def unknownHandString (s: String): String
  def ws: String
}

object BeingLocal extends BeingLocal {
  def ae = "Astralenergie"
  def at = "Attackewert"
  def atfk = "Attacke-/Fernkampfwert"
  def au = "Ausdauer"
  def aw = "Ausweichen"
  def base = "Grundwert"
  def be = "Behinderung"
  def bought = "gekauft"
  def calculated = "berechnet"
  def carriedWeight = "Gewicht"
  def carryingCapacity = "Tragkraft"
  def culture = "Kultur"
  def fk = "Fernkampfwert"
  def gs = "Geschwindigkeit"
  def hero = "Held"
  def ini = "Initiative"
  def initial = "Startwert"
  def ke = "Karmalenergie"
  def le = "Lebensenergie"
  def mr = "Magieresistenz"
  def mrBody = "Magieresistenz Körper"
  def mrMind = "Magieresistenz Geist"
  def overstrain = "Überanstrengung"
  def pa = "Paradewert"
  def profession = "Profession"
  def race = "Rasse"
  def raufen = "Raufen"
  def ringen = "Ringen"
  def rs = "Rüstungsschutz"
  def shortAe = "AE"
  def shortAu = "AU"
  def shortAt = "AT"
  def shortAtFk = "AT/FK"
  def shortAw = "AW"
  def shortBe = "BE"
  def shortFk = "FK"
  def shortGs = "GS"
  def shortIni = "INI"
  def shortKe = "KE"
  def shortLe = "LE"
  def shortMr = "MR"
  def shortMrBody = "MR K."
  def shortMrMind = "MR G."
  def shortOverstrain = "ÜA"
  def shortPa = "PA"
  def shortRs = "RS"
  def shortTp = "TP"
  def shortWs = "WS"
  def start = "Startwert"
  def subdual = "Erschöpfungsschaden"
  def tp = "Trefferpunkte"
  def unknownHandString (s: String) = "Unbekannte Eingabe: " + s
  def ws = "Wundschwelle"

}

// vim: set ts=2 sw=2 et:
