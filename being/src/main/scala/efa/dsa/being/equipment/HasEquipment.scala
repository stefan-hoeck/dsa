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

object HasEquipment {
  def apply[A:HasEquipment]: HasEquipment[A] = implicitly
}

// vim: set ts=2 sw=2 et:
