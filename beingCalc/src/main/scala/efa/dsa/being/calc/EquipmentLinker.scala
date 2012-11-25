package efa.dsa.being.calc

import efa.dsa.being.{HumanoidData ⇒ HD}
import efa.dsa.being.equipment._
import efa.dsa.equipment._
import efa.rpg.core.DB
import scalaz._, Scalaz._

sealed abstract class EquipmentLinker[I,D](
  implicit val EI:EquipmentItem[I],
  val ED:EquipmentData[D]
){

  type E = Equipment[I,D]

  def itemToData (i: I): D

  def items: Lens[EquipmentItems,DB[I]]

  def data: Lens[EquipmentDatas,DB[D]]

  def equipment: Lens[Equipments,DB[E]]

  final def equipmentList: Equipments ⇒ List[E] = es ⇒ 
    equipment.get(es).toList map (_._2) sortBy (_.name)

  final def equipLeft (e: E, b: Boolean): State[HD,Unit] = 
    ED equipLeft (e.data, b)

  final def equipRight (e: E, b: Boolean): State[HD,Unit] = 
    ED equipRight (e.data, b)
  
  final def delete(e: E): State[EquipmentDatas,Unit] =
    data -= e.id void

  final def addI (i: I): State[EquipmentDatas,Unit] = for {
    ed ← init[EquipmentDatas]
    id = ((data get ed).keySet + 0).max + 1 //new id is highest id + 1
    _  ← add (ED.idL set (itemToData(i), id))
  } yield ()

  final def addIHD (i: I): State[HD,Unit] = HD.equipment lifts addI(i)

  final def add(d: D): State[EquipmentDatas,Unit] =
    data += (ED.id(d) → d) void

  final def set[X] (l: D @> X): (E,X) ⇒ State[EquipmentDatas,Unit] =
    (e,x) ⇒ add(l set (e.data, x))

  final def equipment (
    ed: EquipmentDatas,
    hd: HandsData,
    es: EquipmentItems
  ): DB[E] = {
    def toEquipment(p: (Int,D)) = p match {
      case (id,d) ⇒ items get es get ED.parentId(d) map (
        i ⇒ (id, Equipment(i,d,hd)))
    }

    data get ed flatMap toEquipment
  }
}

object EquipmentLinker {

  def equipment (
    ed: EquipmentDatas,
    hd: HandsData, 
    es: EquipmentItems
  ) = Equipments (
    AmmunitionLinker equipment (ed, hd, es),
    ArmorLinker equipment (ed, hd, es),
    ArticleLinker equipment (ed, hd, es),
    MeleeWeaponLinker equipment (ed, hd, es),
    RangedWeaponLinker equipment (ed, hd, es),
    ShieldLinker equipment (ed, hd, es),
    ZoneArmorLinker equipment (ed, hd, es)
  )

  private def el[I:EquipmentItem,D:EquipmentData](
    il: Lens[EquipmentItems,DB[I]],
    dl: Lens[EquipmentDatas,DB[D]],
    el: Lens[Equipments,DB[Equipment[I,D]]],
    toD: I ⇒ D
  ) = new EquipmentLinker[I,D]{
    val items = il
    val data = dl
    val equipment = el
    def itemToData (i: I) = toD(i)
  }

  implicit val AmmunitionLinker = el[AmmunitionItem,AmmunitionData](
    EquipmentItems.ammunition,
    EquipmentDatas.ammunition,
    Equipments.ammunition,
    i ⇒ AmmunitionData(i.eData, i.id, i.tp, 1)
  )

  implicit val ArmorLinker = el[ArmorItem,ArmorData](
    EquipmentItems.armor,
    EquipmentDatas.armor,
    Equipments.armor,
    i ⇒ ArmorData(i.eData, i.id, i.rs, i.be, false)
  )

  implicit val ArticleLinker = el[ArticleItem,ArticleData](
    EquipmentItems.articles,
    EquipmentDatas.articles,
    Equipments.articles,
    i ⇒ ArticleData(i.eData, i.id, 1)
  )

  implicit val MeleeWeaponLinker = el[MeleeWeaponItem,MeleeWeaponData](
    EquipmentItems.meleeWeapons,
    EquipmentDatas.meleeWeapons,
    Equipments.meleeWeapons,
    i ⇒ MeleeWeaponData(i.eData, i.id, i.tp, i.talent, i.bf,
      i.tpkk, i.ini, i.wm)
  )

  implicit val RangedWeaponLinker = el[RangedWeaponItem,RangedWeaponData](
    EquipmentItems.rangedWeapons,
    EquipmentDatas.rangedWeapons,
    Equipments.rangedWeapons,
    i ⇒ RangedWeaponData(i.eData, i.id, i.tp, i.talent, i.tpkk,
      i.reach, i.tpPlus, i.timeToLoad)
  )

  implicit val ShieldLinker = el[ShieldItem,ShieldData](
    EquipmentItems.shields,
    EquipmentDatas.shields,
    Equipments.shields,
    i ⇒ ShieldData(i.eData, i.id, i.ini, i.bf, i.wm)
  )

  implicit val ZoneArmorLinker = el[ZoneArmorItem,ZoneArmorData](
    EquipmentItems.zoneArmor,
    EquipmentDatas.zoneArmor,
    Equipments.zoneArmor,
    i ⇒ ZoneArmorData(i.eData, i.id, i.rs, i.be, false)
  )
}

// vim: set ts=2 sw=2 et:
