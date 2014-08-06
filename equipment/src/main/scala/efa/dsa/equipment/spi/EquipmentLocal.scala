package efa.dsa.equipment.spi

import efa.core.{Localization, Default}

trait EquipmentLocal {
  lazy val beLoc = Localization("be", be, shortBe, be)
  lazy val bfLoc = Localization("bf", bf, shortBf, bf)
  lazy val dkLoc = Localization("dk", dk, shortDk, dk)
  lazy val iniLoc = Localization("ini", ini, shortIni, ini)
  lazy val rsLoc = Localization("rs", rs, shortRs, rs)
  lazy val talentLoc = new Localization("talent", talent)
  lazy val tpLoc = Localization("tp", tp, shortTp, tp)
  lazy val tpkkLoc = new Localization("tpkk", tpkk)
  lazy val wmLoc = Localization("wm", wm, shortWm, wm)

  def ammunition: String
  def ammunitions: String
  def armor: String
  def armors: String
  def article: String
  def articles: String
  def be: String
  def bf: String
  def dk: String
  def equipment: String
  def equipments: String
  def improvised: String
  def ini: String
  def isAddition: String
  def length: String
  def makesWound: String
  def meleeWeapon: String
  def meleeWeapons: String
  def price: String
  def rangedWeapon: String
  def rangedWeapons: String
  def reach: String
  def rs: String
  def shield: String
  def shields: String
  def shieldType: String
  def shortBe: String
  def shortBf: String
  def shortDk: String
  def shortIni: String
  def shortRs: String
  def shortTp: String
  def shortWm: String
  def size: String
  def talent: String
  def timeToLoad: String
  def twoHanded: String
  def tp: String
  def tpkk: String
  def tpPlus: String
  def usesAmmo: String
  def weight: String
  def wm: String
  def zoneArmor: String
  def zoneArmors: String
  def zoneRs: String
}

object EquipmentLocal extends EquipmentLocal {
  implicit val defImpl: Default[EquipmentLocal] = Default.default(this)
  def ammunition = "Geschoss"
  def ammunitions = "Geschosse"
  def armor = "Rüstung"
  def armors = "Rüstungen"
  def article = "Gegenstand"
  def articles = "Gegenstände"
  def be = "Behinderung"
  def bf = "Bruchfaktor"
  def dk = "Distanzklasse"
  def equipment = "Ausrüstung"
  def equipments = "Ausrüstung"
  def improvised = "improvisiert"
  def ini = "Initiative"
  def isAddition = "Rüstungszusatz"
  def length = "Länge"
  def makesWound = "Verursacht Wunde"
  def meleeWeapon = "Nahkampfwaffe"
  def meleeWeapons = "Nahkampfwaffen"
  def price = "Preis"
  def rangedWeapon = "Fernkampfwaffe"
  def rangedWeapons = "Fernkampfwaffen"
  def reach = "Reichweite"
  def rs = "Rüstungsschutz"
  def shield = "Schild"
  def shields = "Schilde"
  def shieldType = "Typ"
  def shortBe = "BE"
  def shortBf = "BF"
  def shortDk = "DK"
  def shortIni = "Ini"
  def shortRs = "RS"
  def shortTp = "TP"
  def shortWm = "WM"
  def size = "Grösse"
  def timeToLoad = "Ladezeit"
  def talent = "Talent"
  def tp = "Trefferpunkte"
  def tpkk = "TP/KK"
  def tpPlus = "TP-Plus"
  def twoHanded = "Zweihandwaffe"
  def usesAmmo = "Benötigt Geschosse"
  def weight = "Gewicht"
  def wm = "Waffenmodifikator"
  def zoneArmor = "Zonenrüstung"
  def zoneArmors = "Zonenrüstungen"
  def zoneRs = "Zonen-RS"
}

// vim: set ts=2 sw=2 et:
