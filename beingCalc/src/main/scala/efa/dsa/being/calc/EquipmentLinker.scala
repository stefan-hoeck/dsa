package efa.dsa.being.calc

import efa.dsa.being.HeroData
import efa.dsa.being.equipment._
import efa.dsa.equipment._
import efa.rpg.core.DB
import scalaz._, Scalaz._

sealed abstract class EquipmentLinker[I,D](
  implicit val EI:EquipmentItem[I],
  val ED:EquipmentData[D]
){

  type E = Equipment[I,D]

  def items: Lens[EquipmentItems,DB[I]]

  def data: Lens[EquipmentDatas,DB[D]]

  def equipment: Lens[Equipments,DB[E]]
  
  def delete(d: D): State[EquipmentDatas,DB[D]] = data -= (ED id d)

  def update(d: D): State[EquipmentDatas,DB[D]] = data += (ED.id(d) → d)

  def heroEquipment (h: HeroData, es: EquipmentItems): DB[E] = {
    def toEquipment(p: (Int,D)) = p match {
      case (id,d) ⇒ items get es get id map (i ⇒ (id, Equipment(i,d)))
    }

    data get h.equipment flatMap toEquipment
  }
}

object EquipmentLinker {

  def heroEquipment (h: HeroData, es: EquipmentItems) = Equipments (
    AmmunitionLinker heroEquipment (h, es),
    ArmorLinker heroEquipment (h, es),
    ArticleLinker heroEquipment (h, es),
    MeleeWeaponLinker heroEquipment (h, es),
    RangedWeaponLinker heroEquipment (h, es),
    ShieldLinker heroEquipment (h, es),
    ZoneArmorLinker heroEquipment (h, es)
  )

  private def el[I:EquipmentItem,D:EquipmentData](
    il: Lens[EquipmentItems,DB[I]],
    dl: Lens[EquipmentDatas,DB[D]],
    el: Lens[Equipments,DB[Equipment[I,D]]]
  ) = new EquipmentLinker[I,D]{
    val items = il
    val data = dl
    val equipment = el
  }

  implicit val AmmunitionLinker = el[AmmunitionItem,AmmunitionData](
    EquipmentItems.ammunition,
    EquipmentDatas.ammunition,
    Equipments.ammunition
  )

  implicit val ArmorLinker = el[ArmorItem,ArmorData](
    EquipmentItems.armor,
    EquipmentDatas.armor,
    Equipments.armor
  )

  implicit val ArticleLinker = el[ArticleItem,ArticleData](
    EquipmentItems.articles,
    EquipmentDatas.articles,
    Equipments.articles
  )

  implicit val MeleeWeaponLinker = el[MeleeWeaponItem,MeleeWeaponData](
    EquipmentItems.meleeWeapons,
    EquipmentDatas.meleeWeapons,
    Equipments.meleeWeapons
  )

  implicit val RangedWeaponLinker = el[RangedWeaponItem,RangedWeaponData](
    EquipmentItems.rangedWeapons,
    EquipmentDatas.rangedWeapons,
    Equipments.rangedWeapons
  )

  implicit val ShieldLinker = el[ShieldItem,ShieldData](
    EquipmentItems.shields,
    EquipmentDatas.shields,
    Equipments.shields
  )

  implicit val ZoneArmorLinker = el[ZoneArmorItem,ZoneArmorData](
    EquipmentItems.zoneArmor,
    EquipmentDatas.zoneArmor,
    Equipments.zoneArmor
  )
}

// vim: set ts=2 sw=2 et:
