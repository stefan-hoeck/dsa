package efa.dsa.being.equipment

import efa.rpg.core.DB
import scalaz._, Scalaz._

trait HasEquipment[-A] {
  def equipment (a: A): Equipments
  def ammunition (a: A): DB[Ammunition] = equipment(a).ammunition
  def armor (a: A): DB[Armor] = equipment(a).armor
  def articles (a: A): DB[Article] = equipment(a).articles
  def meleeWeapons (a: A): DB[MeleeWeapon] = equipment(a).meleeWeapons
  def rangedWeapons (a: A): DB[RangedWeapon] = equipment(a).rangedWeapons
  def shields (a: A): DB[Shield] = equipment(a).shields
  def zoneArmor (a: A): DB[ZoneArmor] = equipment(a).zoneArmor

  def allEquipped (a: A): List[Equipment[_,_]] =
    equippedArmor(a) ::: equippedZoneArmor(a)

  def allUnequipped (a: A): List[Equipment[_,_]] =
    unequippedArmor(a) :::
    db2lE(ammunition(a)) :::
    db2lE(articles(a)) :::
    db2lE(meleeWeapons(a)) :::
    db2lE(rangedWeapons(a)) :::
    db2lE(shields(a)) :::
    unequippedZoneArmor(a)

  def equippedArmor (a: A): List[Armor] =
    db2l(armor(a)) filter (_.data.equipped)

  def equippedZoneArmor (a: A): List[ZoneArmor] =
    db2l(zoneArmor(a)) filter (_.data.equipped)

  def unequippedArmor (a: A): List[Armor] =
    db2l(armor(a)) filterNot (_.data.equipped)

  def unequippedZoneArmor (a: A): List[ZoneArmor] =
    db2l(zoneArmor(a)) filterNot (_.data.equipped)

  private def db2l[A] (as: DB[A]): List[A] = as.toList ∘ (_._2)

  private def db2lE (as: DB[Equipment[_,_]]): List[Equipment[_,_]] =
    as.toList ∘ (_._2)
}

trait HasEquipmentFunctions {
  import efa.dsa.being.equipment.{HasEquipment ⇒ HE}

  def equipment[A:HE] (a: A): Equipments = HE[A] equipment a
  def ammunition[A:HE] (a: A): DB[Ammunition] =HE[A] ammunition a 
  def armor[A:HE] (a: A): DB[Armor] = HE[A] armor a
  def articles[A:HE] (a: A): DB[Article] = HE[A] articles a
  def meleeWeapons[A:HE] (a: A): DB[MeleeWeapon] = HE[A] meleeWeapons a
  def rangedWeapons[A:HE] (a: A): DB[RangedWeapon] = HE[A] rangedWeapons a
  def shields[A:HE] (a: A): DB[Shield] = HE[A] shields a
  def zoneArmor[A:HE] (a: A): DB[ZoneArmor] = HE[A] zoneArmor a
  def allEquipped[A:HE] (a: A): List[Equipment[_,_]] = HE[A] allEquipped a

  def allUnequipped[A:HE] (a: A): List[Equipment[_,_]] =
    HE[A] allUnequipped a

  def equippedArmor[A:HE] (a: A): List[Armor] = HE[A] equippedArmor a

  def equippedZoneArmor[A:HE] (a: A): List[ZoneArmor] =
    HE[A] equippedZoneArmor a

  def unequippedArmor[A:HE] (a: A): List[Armor] = HE[A] unequippedArmor a

  def unequippedZoneArmor[A:HE] (a: A): List[ZoneArmor] =
    HE[A] unequippedZoneArmor a
}

object HasEquipment {
  def apply[A:HasEquipment]: HasEquipment[A] = implicitly
}

// vim: set ts=2 sw=2 et:
