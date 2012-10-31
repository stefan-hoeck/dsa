package efa.dsa.test

import efa.dsa.equipment._
import efa.dsa.being.equipment._
import efa.rpg.core.{RpgItem, DB}
import org.scalacheck._, Prop._
import scalaz._, Scalaz._

object EquipmentGensTest extends Properties("EquipmentGens") with ItemGensTest {
  import EquipmentGens._
 
  val dataGen: Gen[(EquipmentItems, EquipmentDatas, HandsData)] = for {
    i ← equipmentGen
    d ← equipmentDatasGen(i)
    h ← handsDataGen(d)
  } yield (i, d, h)

  property("specs") = Prop.forAll(dataGen) {t ⇒ 
    val (i, d, h) = t

    ammunition(i) &&
    armor(i) &&
    articles(i) &&
    meleeWeapons(i) &&
    rangedWeapons(i) &&
    shields(i) &&
    zoneArmor(i) &&
    ammunitionDatas(i, d) &&
    armorDatas(i, d) &&
    articleDatas(i, d) &&
    meleeWeaponDatas(i, d) &&
    rangedWeaponDatas(i, d) &&
    shieldDatas(i, d) &&
    zoneArmorDatas(i, d) &&
    hands(d, h)
  }

  def ammunition (as: EquipmentItems) =
    testDB(as.ammunition, ammunitionNames) :| "ammunition"

  def armor (as: EquipmentItems) =
    testDB(as.armor, armorNames) :| "armor"

  def articles (as: EquipmentItems) =
    testDB(as.articles, articleNames) :| "articles"

  def meleeWeapons (as: EquipmentItems) =
    testDB(as.meleeWeapons, meleeWeaponNames) :| "meleeWeapons"

  def rangedWeapons (as: EquipmentItems) =
    testDB(as.rangedWeapons, rangedWeaponNames) :| "rangedWeapons"

  def shields (as: EquipmentItems) =
    testDB(as.shields, shieldNames) :| "shields"

  def zoneArmor (as: EquipmentItems) =
    testDB(as.zoneArmor, zoneArmorNames) :| "zoneArmor"

  def ammunitionDatas (i: EquipmentItems, d: EquipmentDatas) =
    d.ammunition forall {case (n,a) ⇒ i.ammunition get a.id nonEmpty}

  def armorDatas (i: EquipmentItems, d: EquipmentDatas) =
    d.armor forall {case (n,a) ⇒ i.armor get a.id nonEmpty}

  def articleDatas (i: EquipmentItems, d: EquipmentDatas) =
    d.articles forall {case (n,a) ⇒ i.articles get a.id nonEmpty}

  def meleeWeaponDatas (i: EquipmentItems, d: EquipmentDatas) =
    d.meleeWeapons forall {case (n,a) ⇒ i.meleeWeapons get a.id nonEmpty}

  def rangedWeaponDatas (i: EquipmentItems, d: EquipmentDatas) =
    d.rangedWeapons forall {case (n,a) ⇒ i.rangedWeapons get a.id nonEmpty}

  def shieldDatas (i: EquipmentItems, d: EquipmentDatas) =
    d.shields forall {case (n,a) ⇒ i.shields get a.id nonEmpty}

  def zoneArmorDatas (i: EquipmentItems, d: EquipmentDatas) =
    d.zoneArmor forall {case (n,a) ⇒ i.zoneArmor get a.id nonEmpty}

  import HandsData._
  import efa.dsa.being.equipment.{HandData ⇒ H}

  def hands (d: EquipmentDatas, h: HandsData): Prop = h match {
    case Empty ⇒ true
    case OneHanded(H.Empty, H.Empty) ⇒ true
    case TwoHanded(id) ⇒ d.meleeWeapons.get(id).nonEmpty :| "two-handed"
    case OneHanded(H.Empty, H.Melee(id)) ⇒ 
      d.meleeWeapons.get(id).nonEmpty :| "one-handed melee"
    case OneHanded(H.Melee(id), H.Empty) ⇒ 
      d.meleeWeapons.get(id).nonEmpty :| "one-handed melee wrong hand"
    case OneHanded(H.Shield(id), H.Empty) ⇒ 
      d.shields.get(id).nonEmpty :| "one-handed shield"
    case OneHanded(H.Shield(s), H.Melee(m)) ⇒ 
      (d.meleeWeapons.get(m).nonEmpty &&
      d.shields.get(s).nonEmpty) :| "shield weapon"
    case OneHanded(H.Empty, H.Ranged(id)) ⇒ 
      d.rangedWeapons.get(id).nonEmpty :| "one-handed ranged"
    case OneHanded(H.Ranged(id), H.Empty) ⇒ 
      d.rangedWeapons.get(id).nonEmpty :| "one-handed ranged wrong hand"
    case OneHanded(H.Ammo(id), H.Empty) ⇒ 
      d.ammunition.get(id).nonEmpty :| "one-handed ammunition"
    case OneHanded(H.Ammo(a), H.Ranged(r)) ⇒ 
      (d.rangedWeapons.get(r).nonEmpty &&
      d.ammunition.get(a).nonEmpty) :| "ranged ammo"
    case _ ⇒ false :| ("Hands: " + h)
  }
}

// vim: set ts=2 sw=2 et:
