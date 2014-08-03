package efa.dsa.equipment

import efa.rpg.core.DieRoller
import efa.core.{ToXml, Efa}, Efa._
import efa.core.syntax.{string, nodeSeq}
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.{Arbitrary, Gen}

case class AmmunitionItem(eData: EquipmentItemData, tp: DieRoller)
   extends EquipmentLike[AmmunitionItem] {
  def eData_= (v: EquipmentItemData) = copy (eData = v)
}

object AmmunitionItem extends EquipmentItemLikes[AmmunitionItem] {
  lazy val default = AmmunitionItem (eData(loc.ammunition), !!)

  def shortDesc (i: AmmunitionItem) = {
    def tpTag = (loc.tp, i.tp.toString)

    tagShortDesc (i, tpTag, priceTag(i), weightTag(i))
  }

  implicit lazy val AmmunitionItemToXml = new ToXml[AmmunitionItem] {
    def fromXml (ns: Seq[Node]) =
      ^(readEData(ns), Tp read ns)(AmmunitionItem.apply)

    def toXml (a: AmmunitionItem) = dataToNode(a) ++ Tp.write(a.tp)
  }

  implicit lazy val AmmunitionItemArbitrary = Arbitrary (
    ^(a[EquipmentItemData], a[DieRoller])(AmmunitionItem.apply)
  )

  val tp: AmmunitionItem @> DieRoller =
    Lens.lensu((a,b) ⇒ a.copy(tp = b), _.tp)
}

// vim: set ts=2 sw=2 et:
