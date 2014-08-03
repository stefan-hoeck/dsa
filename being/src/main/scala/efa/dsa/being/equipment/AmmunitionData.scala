package efa.dsa.being.equipment

import efa.core.{Efa, TaggedToXml}, Efa._
import efa.core.syntax.{string, nodeSeq}
import efa.dsa.equipment.{EquipmentItemData, Tp}
import efa.rpg.core.DieRoller
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class AmmunitionData(
  eData: EquipmentItemData,
  parentId: Int,
  tp: DieRoller,
  count: Int
) extends EquipmentLike[AmmunitionData] {
  require(Count validate count isRight)

  def eData_= (v: EquipmentItemData) = copy (eData = v)

  def parentId_= (v: Int) = copy (parentId = v)

  override lazy val fullPrice = count * price
  override lazy val fullWeight = count * weight

  override def equipped (h: HandData): Boolean = h match {
    case HandData.Ammo(x) if x ≟ id ⇒ true
    case _ ⇒ false
  }

  override def handData: Option[HandData] = Some(HandData.Ammo(id))
}

object AmmunitionData extends EquipmentLikes[AmmunitionData] {
  lazy val default = AmmunitionData(eData(_.ammunition), 0, !!, 1)

  implicit lazy val AmmunitionDataArbitrary = Arbitrary (
    ^^^(eDataGen, parentIdGen, a[DieRoller], Count.gen)(AmmunitionData.apply)
  )

  implicit lazy val AmmunitionDataToXml = new TaggedToXml[AmmunitionData] {
    val tag = "dsa_ammunition"

    def fromXml (ns: Seq[Node]) =
      ^^^(readEData (ns),
        readParentId (ns),
        Tp read ns,
        Count read ns)(AmmunitionData.apply)

    def toXml (a: AmmunitionData) = 
      eDataNodes(a) ++ Tp.write(a.tp) ++ Count.write(a.count)
  }

  val eData: AmmunitionData @> EquipmentItemData =
    Lens.lensu((a,b) ⇒ a.copy(eData = b), _.eData)

  val parentId: AmmunitionData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val tp: AmmunitionData @> DieRoller =
    Lens.lensu((a,b) ⇒ a.copy(tp = b), _.tp)

  val count: AmmunitionData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(count = b), _.count)
}

// vim: set ts=2 sw=2 et:
