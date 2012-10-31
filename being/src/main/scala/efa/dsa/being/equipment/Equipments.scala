package efa.dsa.being.equipment

import efa.core.Default
import efa.rpg.core.DB
import scalaz._, Scalaz._

case class Equipments (
  ammunition: DB[Ammunition],
  armor: DB[Armor],
  articles: DB[Article],
  meleeWeapons: DB[MeleeWeapon],
  rangedWeapons: DB[RangedWeapon],
  shields: DB[Shield],
  zoneArmor: DB[ZoneArmor]
)

object Equipments {
  private def e[A]: DB[A] = Map.empty

  lazy val default = Equipments(e, e, e, e, e, e, e)

  implicit lazy val EquipmentsDefault = Default default default

  implicit val EquipmentsEqual  = Equal.equalA[Equipments]

  val ammunition: Equipments @> DB[Ammunition] =
    Lens.lensu((a,b) ⇒ a.copy(ammunition = b), _.ammunition)

  val armor: Equipments @> DB[Armor] =
    Lens.lensu((a,b) ⇒ a.copy(armor = b), _.armor)

  val articles: Equipments @> DB[Article] =
    Lens.lensu((a,b) ⇒ a.copy(articles = b), _.articles)

  val meleeWeapons: Equipments @> DB[MeleeWeapon] =
    Lens.lensu((a,b) ⇒ a.copy(meleeWeapons = b), _.meleeWeapons)

  val rangedWeapons: Equipments @> DB[RangedWeapon] =
    Lens.lensu((a,b) ⇒ a.copy(rangedWeapons = b), _.rangedWeapons)

  val shields: Equipments @> DB[Shield] =
    Lens.lensu((a,b) ⇒ a.copy(shields = b), _.shields)

  val zoneArmor: Equipments @> DB[ZoneArmor] =
    Lens.lensu((a,b) ⇒ a.copy(zoneArmor = b), _.zoneArmor)
}

// vim: set ts=2 sw=2 et:
