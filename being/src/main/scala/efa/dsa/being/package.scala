package efa.dsa

import efa.core._
import efa.dsa.world.{Attribute, BodyPart}
import efa.dsa.being.spi.BeingLocal
import efa.rpg.core._
import org.scalacheck.Gen
import scalaz._, Scalaz._

package object being extends RangeVals {
  lazy val loc = Service.unique[BeingLocal](BeingLocal)

  type Attributes = EnumMap[Attribute,Int]
  type ZoneWounds = EnumMap[BodyPart, Int]

  val (minBought, maxBought) = (0L, 999L)
  val (minBoughtAtt, maxBoughtAtt) = (0, 99)
  val (minLost, maxLost) = (0L, Long.MaxValue)

  val Ap = fullInfo(0, Int.MaxValue, "ap")

  val BoughtAttributes = EnumMaps.int[Attribute](
    minBoughtAtt, maxBoughtAtt, 0, "boughtAttributes")

  val BoughtAe = fullInfo(minBought, maxBought, "boughtAe")
  val BoughtAu = fullInfo(minBought, maxBought, "boughtAu")
  val BoughtKe = fullInfo(minBought, maxBought, "boughtKe")
  val BoughtLe = fullInfo(minBought, maxBought, "boughtLe")
  val BoughtMr = fullInfo(minBought, maxBought, "boughtMr")
  val Exhaustion = fullInfo(0L, 99L, "exhaustion")
  val Height = fullInfo(0L, Long.MaxValue, "height")

  val InitialAttributes =
    EnumMaps.int[Attribute](0, 99, 8, "baseAttributes")

  val LostAe = fullInfo(minLost, maxLost, "damageAe")
  val LostAu = fullInfo(minLost, maxLost, "damageAu")
  val LostKe = fullInfo(minLost, maxLost, "damageKe")
  val LostLe = fullInfo(minLost, maxLost, "damageLe")
  val So = fullInfo(1, 23, "so")
  val Weight = fullInfo(0L, Long.MaxValue, "weight")
  val Wounds = fullInfo(0L, 99L, "wounds")
  val (zoneWoundsMax, zoneWoundsMin) = (3, 0)

  val ZoneWounds = EnumMaps.int[BodyPart](
    zoneWoundsMin, zoneWoundsMax, 0, "zoneWounds")

  def apUsedI(ap: Int) = fullInfo(0, ap, "apUsed")

  val (min, max) = (-999, 999)
  val AeKey = ModifierKey(loc.aeLoc, min, max)
  val AtKey = ModifierKey(loc.atLoc, min, max)
  val AtFkKey = ModifierKey(loc.atfkLoc, min, max)
  val AuKey = ModifierKey(loc.auLoc, min, max)
  val AwKey = ModifierKey(loc.awLoc, min, max)
  val BeKey = ModifierKey(loc.beLoc, min, max)

  val CarryingCapacityKey =
    ModifierKey(loc.carryingCapacityLoc, 0L, Long.MaxValue)

  val FkKey = ModifierKey(loc.fkLoc, min, max)
  val GsKey = ModifierKey(loc.gsLoc, min, max)
  val IniKey = ModifierKey(loc.iniLoc, min, max)
  val KeKey = ModifierKey(loc.keLoc, min, max)
  val MrKey = ModifierKey(loc.mrLoc, min, max)
  val MrBodyKey = ModifierKey(loc.mrBodyLoc, min, max)
  val MrMindKey = ModifierKey(loc.mrMindLoc, min, max)
  val LeKey = ModifierKey(loc.leLoc, min, max)
  val OverstrainKey = ModifierKey(loc.overstrainLoc, min, max)
  val PaKey = ModifierKey(loc.paLoc, min, max)
  val RsKey = ModifierKey(loc.rsLoc, min, max)
	val TpKey = ModifierKey(efa.dsa.equipment.loc.tpLoc, -999L, 999L)
  val WeightKey = ModifierKey(loc.carriedWeightLoc, 0L, Long.MaxValue)
  val WsKey = ModifierKey(loc.wsLoc, min, max)

  val AttributeKeys = Attribute.values ∘ (a ⇒ ModifierKey(a.loc, min, max))
  val attributeKeyFor = Attribute.values zip AttributeKeys toMap

  val ZoneRsKeys = BodyPart.values ∘ (a ⇒ ModifierKey(a.loc, min, max))
  val zoneRsKeyFor = BodyPart.values zip ZoneRsKeys toMap

  def modSeq (value: Long, name: String): List[Modifier] =
    (value ≠ 0L) ? List(Modifier(name, value)) | Nil

  def attributeMods(em: EnumMap[Attribute,Int], name: String): Modifiers = {
    def aMods (a: Attribute) =  modSeq(em(a), name)
    def aPair (a: Attribute) = (attributeKeyFor(a), aMods(a))

    Modifiers (Attribute.values ∘ aPair: _*)
  }

  def setDamage[A] (max: Long, i: Long, l: A @> Long): ValSt[A] =
    setLong(Long.MinValue + max, max, max - i, l)

  @deprecated("use Long not Int", "1.0.0-SNAPSHOT")
  def setInt[A] (min: Int, max: Int, i: Int, l: A @> Int)
  : ValSt[A] = {
    val validate = Validators.interval(min, max)

    (validate run i validation) as (l := i void)
  }

  def setLong[A] (min: Long, max: Long, i: Long, l: A @> Long)
  : ValSt[A] = {
    val validate = Validators.interval(min, max)

    (validate run i validation) as (l := i void)
  }
}

// vim: set ts=2 sw=2 et:
