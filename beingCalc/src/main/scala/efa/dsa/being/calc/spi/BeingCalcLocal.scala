package efa.dsa.being.calc.spi

trait BeingCalcLocal {
  def race: String
  def culture: String
  def profession: String
  def start: String
  def bought: String
  def calculated: String
  def tp: String
  def shortTp: String
  def unarmed: String
  def subdual: String
  def atfk: String
  def base: String
  def shortAtfk: String
  def raufen: String
  def ringen: String
}

object BeingCalcLocal extends BeingCalcLocal {
  def race = "Rasse"
  def culture = "Kultur"
  def profession = "Profession"
  def start = "Startwert"
  def bought = "gekauft"
  def calculated = "berechnet"
  def tp = "Trefferpunkte"
  def shortTp = "TP"
  def unarmed = "Waffenlos"
  def subdual = "A"
  def atfk = "Attack-/Fernkampf-Wert"
  def base = "Grundwert"
  def shortAtfk = "AT/FK"
  def raufen = "Raufen"
  def ringen = "Ringen"
}

// vim: set ts=2 sw=2 et:
