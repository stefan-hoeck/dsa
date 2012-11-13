package efa.dsa.being.equipment

import efa.core.{ToXml, Efa}, Efa._
import efa.dsa.equipment.{EquipmentItemData, Tp, Talent, Ttl, Reach, TpPlus}
import efa.dsa.world.{TpKk}
import efa.rpg.core.DieRoller
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class RangedWeaponData(
  eData: EquipmentItemData,
  parentId: Int,
  tp: DieRoller,
  talent: String,
  tpkk: TpKk,
  reach: Reach,
  tpPlus: TpPlus,
  timeToLoad: Int
) extends EquipmentLike[RangedWeaponData] {
  require(Ttl validate timeToLoad isRight)

  def eData_= (v: EquipmentItemData) = copy (eData = v)

  def parentId_= (v: Int) = copy (parentId = v)

  override def equipped (h: HandData): Boolean = h match {
    case HandData.Ranged(x) if x ≟ id ⇒ true
    case _ ⇒ false
  }

  override def handData: Option[HandData] = Some(HandData.Ranged(id))
}

object RangedWeaponData extends EquipmentLikes[RangedWeaponData] {
  lazy val default = RangedWeaponData(
    eData(_.rangedWeapon), 0, !!, "", !!, Reach.!!, TpPlus.!!, 0)

  implicit lazy val RangedWeaponDataArbitrary = Arbitrary (
    eDataGen ⊛ 
    parentIdGen ⊛ 
    a[DieRoller] ⊛ 
    Gen.identifier ⊛
    a[TpKk] ⊛ 
    Reach.gen ⊛ 
    TpPlus.gen ⊛ 
    Ttl.gen apply RangedWeaponData.apply
  )

  implicit lazy val RangedWeaponDataToXml = new ToXml[RangedWeaponData] {
    def fromXml (ns: Seq[Node]) =
      readEData (ns) ⊛
      readParentId (ns) ⊛ 
      Tp.read(ns) ⊛
      Talent.read(ns) ⊛
      ns.tagged[TpKk] ⊛
      Reach.read(ns) ⊛
      TpPlus.read(ns) ⊛
      Ttl.read(ns) apply RangedWeaponData.apply

    def toXml (a: RangedWeaponData) = 
      eDataNodes(a) ++
      Tp.write(a.tp) ++
      Talent.write(a.talent) ++
      xml(a.tpkk) ++
      Reach.write(a.reach) ++
      TpPlus.write(a.tpPlus) ++
      Ttl.write(a.timeToLoad)
  }

  val eData: RangedWeaponData @> EquipmentItemData =
    Lens.lensu((a,b) ⇒ a.copy(eData = b), _.eData)

  val parentId: RangedWeaponData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val tp: RangedWeaponData @> DieRoller =
    Lens.lensu((a,b) ⇒ a.copy(tp = b), _.tp)

  val talent: RangedWeaponData @> String =
    Lens.lensu((a,b) ⇒ a.copy(talent = b), _.talent)

  val tpkk: RangedWeaponData @> TpKk =
    Lens.lensu((a,b) ⇒ a.copy(tpkk = b), _.tpkk)

  val reach: RangedWeaponData @> Reach =
    Lens.lensu((a,b) ⇒ a.copy(reach = b), _.reach)

  val tpPlus: RangedWeaponData @> TpPlus =
    Lens.lensu((a,b) ⇒ a.copy(tpPlus = b), _.tpPlus)

  val timeToLoad: RangedWeaponData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(timeToLoad = b), _.timeToLoad)
}

// vim: set ts=2 sw=2 et:
