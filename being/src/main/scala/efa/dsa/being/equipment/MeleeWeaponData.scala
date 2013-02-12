package efa.dsa.being.equipment

import efa.core.{TaggedToXml, Efa}, Efa._
import efa.dsa.equipment.{EquipmentItemData, Tp, Talent, Bf, Ini}
import efa.dsa.world.{Wm, TpKk}
import efa.rpg.core.DieRoller
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class MeleeWeaponData(
  eData: EquipmentItemData,
  parentId: Int,
  tp: DieRoller,
  talent: String,
  bf: Int,
  tpkk: TpKk,
  ini: Int,
  wm: Wm
) extends EquipmentLike[MeleeWeaponData] {
  require(Ini validate ini isRight)
  require(Bf validate bf isRight)

  def eData_= (v: EquipmentItemData) = copy (eData = v)

  def parentId_= (v: Int) = copy (parentId = v)

  override def equipped (h: HandData): Boolean = h match {
    case HandData.Melee(x) if x ≟ id ⇒ true
    case _ ⇒ false
  }

  override def handData: Option[HandData] = Some(HandData.Melee(id))
  
}

object MeleeWeaponData extends EquipmentLikes[MeleeWeaponData] {
  lazy val default =
    MeleeWeaponData(eData(_.meleeWeapon), 0, !!, "", 0, !!, 0, !!)

  implicit lazy val MeleeWeaponDataArbitrary = Arbitrary (
    eDataGen ⊛ 
    parentIdGen ⊛ 
    a[DieRoller] ⊛ 
    Gen.identifier ⊛
    Bf.gen ⊛
    a[TpKk] ⊛ 
    Ini.gen ⊛
    a[Wm] apply MeleeWeaponData.apply
  )

  implicit lazy val MeleeWeaponDataToXml = new TaggedToXml[MeleeWeaponData] {
    val tag = "dsa_meleeWeapon"

    def fromXml (ns: Seq[Node]) =
      readEData (ns) ⊛
      readParentId (ns) ⊛ 
      Tp.read(ns) ⊛
      Talent.read(ns) ⊛
      Bf.read(ns) ⊛
      ns.tagged[TpKk] ⊛
      Ini.read(ns) ⊛
      ns.tagged[Wm] apply MeleeWeaponData.apply

    def toXml (a: MeleeWeaponData) = 
      eDataNodes(a) ++
      Tp.write(a.tp) ++
      Talent.write(a.talent) ++
      Bf.write(a.bf) ++
      Efa.toXml(a.tpkk) ++
      Ini.write(a.ini) ++
      Efa.toXml(a.wm)
  }

  val eData: MeleeWeaponData @> EquipmentItemData =
    Lens.lensu((a,b) ⇒ a.copy(eData = b), _.eData)
  
  val parentId: MeleeWeaponData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val tp: MeleeWeaponData @> DieRoller =
    Lens.lensu((a,b) ⇒ a.copy(tp = b), _.tp)

  val talent: MeleeWeaponData @> String =
    Lens.lensu((a,b) ⇒ a.copy(talent = b), _.talent)

  val bf: MeleeWeaponData @> Int = Lens.lensu((a,b) ⇒ a.copy(bf = b), _.bf)

  val tpkk: MeleeWeaponData @> TpKk =
    Lens.lensu((a,b) ⇒ a.copy(tpkk = b), _.tpkk)

  val ini: MeleeWeaponData @> Int = Lens.lensu((a,b) ⇒ a.copy(ini = b), _.ini)

  val wm: MeleeWeaponData @> Wm = Lens.lensu((a,b) ⇒ a.copy(wm = b), _.wm)
}

// vim: set ts=2 sw=2 et:
