package efa.dsa.rules.humanoid

import efa.dsa.rules.{loc ⇒ Loc, FADRules}
import efa.dsa.being._
import efa.dsa.being.abilities.{HasAbilities ⇒ HA}
import efa.dsa.being.equipment.{HasEquipment ⇒ HE, Equipment, ZoneArmor}
import efa.dsa.world._, mittelreich.Weight.ST
import efa.rpg.core._, efa.rpg.core.{Modified ⇒ M}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules depend on KK and must therefore be calculated
 * after the Attributes of a Being are calculated. They modify
 * RS and BE.
 */
object EquippedRules extends FADRules {
  //beFromOverload must come after carryingCapacity and carriedWeight,
  //Ruestungsgewöhnung must come after be and zoneBe
  def equipped[A:HE:M]: DList[Rule[A]] = DList (
    carryingCapacity, carriedWeight, rs, be, zoneRs, zoneBe, beFromOverload 
  )

  def all[A:HE:HA:M]: DList[Rule[A]] =
    equipped[A] ++
    DList(rustungsgewohnungII, rustungsgewohnungIII, rustungsgewohnungIIIIni)
  
  def beFromOverload[A:M]: Rule[A] = oModRule[A](Loc.beFromOverloadL,
    WeightKey.loc.locName, BeKey, cc2be)
  
  def carryingCapacity[A:M]: Rule[A] = oModRule[A](Loc.carryingCapacityL,
    KkKey.loc.locName, CarryingCapacityKey, prop(_, KkKey) * ST.multiplier)

  /**
   * Calcs the carried weight of a being. The weight of equipped pieces of armor
   * is divided by 2 before being added. One modifier for each piece of armor.
   */
  def carriedWeight[A:HE:M]: Rule[A] = {
    def equippedM (e: Equipment[_,_]) = Modifier(e.name, e.fullWeight rdiv 2L)
    def unequippedM (e: Equipment[_,_]) = Modifier(e.name, e.fullWeight)
    def all (a: A) =
      (allEquipped(a) map equippedM) ::: (allUnequipped(a) map unequippedM)

    Rule(Loc.carriedWeightL.name, a ⇒ addMods(a, WeightKey, all(a)))
  }
  
  /**
   * Sums the rs-values of equipped pieces of armor. One modifier per piece of
   * armor
   */
  def rs[A:HE:M]: Rule[A] = {
    def all (a: A) = equippedArmor(a) map (e ⇒ Modifier(e.name, e.data.rs))

    Rule(Loc.armorRsL.name, a ⇒ addMods(a, RsKey, all(a)))
  }
  
  def rustungsgewohnungII[A:HE:HA:M]: Rule[A] = featRule[A] (
    Loc.rustungsgewohnungIIL, Loc.rustungsgewohnungII,
    a ⇒ -(calcArmorBe(a) min 1L), BeKey
  )
  
  def rustungsgewohnungIII[A:HE:HA:M]: Rule[A] = featRule[A] (
    Loc.rustungsgewohnungIIIL, Loc.rustungsgewohnungIII,
    a ⇒ -(calcArmorBe(a) min 1L), BeKey
  )
  
  def rustungsgewohnungIIIIni[A:HE:HA:M]: Rule[A] = {
    def ini(a: A) = {
      val beIs = calcArmorBe(a) + cc2be(a) - 2L
      val beShould = (beIs rdiv 2L) max 0L
      beIs - beShould
    }

    featRule[A] (Loc.rustungsgewohnungIIIIniL,
      Loc.rustungsgewohnungIII, ini, IniKey)
  }
  
  /**
   * Sums the be-values of equipped pieces of armor. One modifier per piece of
   * armor
   */
  def be[A:HE:M]: Rule[A] = {
    def all (a: A) = equippedArmor(a) map (e ⇒ Modifier(e.name, e.data.be))

    Rule(Loc.armorBeL.name, a ⇒ addMods(a, BeKey, all(a)))
  }
  
  def zoneRs[A:HE:M]: Rule[A] = {
    def calc(a: A): A = {
      val averageMod = oModAdd(a, Loc.zoneArmor, calcZoneRs(a), RsKey)

      def addZa (za: ZoneArmor)(a: A, b: BodyPart) =
        oModAdd(a, za.name, za.data.rs(b), zoneRsKeyFor(b))

      def zoneMods (a: A, za: ZoneArmor) = (a /: BodyPart.values)(addZa(za))

      (averageMod /: equippedZoneArmor(a))(zoneMods)
    }

    Rule(Loc.zoneArmorRsL.name, calc)
  }
  
  def zoneBe[A:HE:M]: Rule[A] = 
    oModRule[A](Loc.zoneArmorBeL, Loc.zoneArmor, BeKey, calcZoneBe)

  private val KkKey = attributeKeyFor(Attribute.Kk)
  
  private def calcArmorBe[A:HE](a: A): Long = 
    calcZoneBe(a) + (equippedArmor(a) foldMap (_.data.be))
  
  private def calcZoneRs[A:HE](a: A): Int = {
    def rs (za: ZoneArmor) =
      BodyPart.values foldMap (b ⇒ za.data.rs(b) * b.factor)

    equippedZoneArmor(a) foldMap rs rdiv BodyPart.factorSum
  }
  
  private def calcZoneBe[A:HE](a: A): Long =
    equippedZoneArmor(a) foldMap (_.data.be) rdiv 10 

  private def cc2be[A:M](a: A): Long = {
    val cc = prop(a, CarryingCapacityKey)
    val cw = prop(a, WeightKey)
    def add = if ((cw - cc) % (cc / 2L) == 0L) 0L else 1L
    def be = (cw - cc) / (cc / 2L) + add

    if (cc ≟ 0L) 0L else be
  }
}

// vim: set ts=2 sw=2 et:
