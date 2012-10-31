package efa.dsa.equipment.spi

trait EquipmentLocal {
  def weight: String
  def price: String
  def tp: String
  def talent: String
  def improvised: String
  def tpkk: String
  def ini: String
  def wm: String
  def bf: String
  def dk: String
  def twoHanded: String
  def length: String
  def reach: String
  def tpPlus: String
  def makesWound: String
  def timeToLoad: String
  def usesAmmo: String
  def rs: String
  def be: String
  def isAddition: String
  def size: String
  def shieldType: String
  def difference: String
  def zoneRs: String
  def ammunition: String
  def ammunitions: String
  def armor: String
  def armors: String
  def zoneArmor: String
  def zoneArmors: String
  def meleeWeapon: String
  def meleeWeapons: String
  def rangedWeapon: String
  def rangedWeapons: String
  def shield: String
  def shields: String
  def equipment: String
  def equipments: String
}

object EquipmentLocal extends EquipmentLocal {
  def weight = "Gewicht"
  def price = "Preis"
  def tp = "TP"
  def talent = "Talent"
  def improvised = "improvisiert"
  def tpkk = "TP/KK"
  def ini = "INI"
  def wm = "WM"
  def bf = "BF"
  def dk = "Distanzklasse"
  def twoHanded = "Zweihandwaffe"
  def length = "Länge"
  def reach = "Reichweite"
  def tpPlus = "TP+"
  def makesWound = "Verursacht Wunde"
  def timeToLoad = "Ladezeit"
  def usesAmmo = "Benötigt Geschosse"
  def rs = "RS"
  def be = "BE"
  def isAddition = "Rüstungszusatz"
  def size = "Grösse"
  def shieldType = "Typ"
  def difference = "Differenz RS - BE"
  def zoneRs = "Zonen-RS"
  def ammunition = "Geschoss"
  def ammunitions = "Geschosse"
  def armor = "Rüstung"
  def armors = "Rüstungen"
  def zoneArmor = "Zonenrüstung"
  def zoneArmors = "Zonenrüstungen"
  def meleeWeapon = "Nahkampfwaffe"
  def meleeWeapons = "Nahkampfwaffen"
  def rangedWeapon = "Fernkampfwaffe"
  def rangedWeapons = "Fernkampfwaffen"
  def shield = "Schild"
  def shields = "Schilde"
  def equipment = "Ausrüstung"
  def equipments = "Ausrüstung"
}

// vim: set ts=2 sw=2 et:
