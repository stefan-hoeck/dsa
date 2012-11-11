package efa.dsa.being.equipment

import efa.core.{Efa, ToXml}, Efa._
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
}

object AmmunitionData extends EquipmentLikes[AmmunitionData] {
  lazy val default = AmmunitionData(eData(_.ammunition), 0, !!, 1)

  implicit lazy val AmmunitionDataArbitrary = Arbitrary (
    ^^^(eDataGen, parentIdGen, a[DieRoller], Count.gen)(AmmunitionData.apply)
  )

  implicit lazy val AmmunitionDataToXml = new ToXml[AmmunitionData] {
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
