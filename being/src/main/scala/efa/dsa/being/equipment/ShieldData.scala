package efa.dsa.being.equipment

import efa.core.{TaggedToXml, Efa}, Efa._
import efa.dsa.equipment.{EquipmentItemData, Bf, Ini}
import efa.dsa.world.Wm
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class ShieldData(
  eData: EquipmentItemData,
  parentId: Int,
  ini: Int,
  bf: Int,
  wm: Wm
) extends EquipmentLike[ShieldData] {
  require(Bf validate bf isRight)
  require(Ini validate ini isRight)

  def eData_= (v: EquipmentItemData) = copy (eData = v)

  def parentId_= (v: Int) = copy (parentId = v)

  override def equipped (h: HandData): Boolean = h match {
    case HandData.Shield(x) if x ≟ id ⇒ true
    case _ ⇒ false
  }

  override def handData: Option[HandData] = Some(HandData.Shield(id))
}

object ShieldData extends EquipmentLikes[ShieldData] {
  lazy val default = ShieldData(eData(_.shield), 0, 0, 0, !!)

  implicit lazy val ShieldDataArbitrary = Arbitrary (
    ^^^^(eDataGen, parentIdGen, Ini.gen, Bf.gen, a[Wm])(ShieldData.apply)
  )

  implicit lazy val ShieldDataToXml = new TaggedToXml[ShieldData] {
    val tag = "dsa_shield"

    def fromXml (ns: Seq[Node]) =
      ^^^^(readEData (ns),
        readParentId (ns),
        Ini read ns,
        Bf read ns,
        ns.tagged[Wm])(ShieldData.apply)

    def toXml (a: ShieldData) = 
      eDataNodes(a) ++ Ini.write(a.ini) ++ Bf.write(a.bf) ++ Efa.toXml(a.wm)
  }

  val eData: ShieldData @> EquipmentItemData =
    Lens.lensu((a,b) ⇒ a.copy(eData = b), _.eData)

  val parentId: ShieldData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val ini: ShieldData @> Int = Lens.lensu((a,b) ⇒ a.copy(ini = b), _.ini)

  val bf: ShieldData @> Int = Lens.lensu((a,b) ⇒ a.copy(bf = b), _.bf)

  val wm: ShieldData @> Wm = Lens.lensu((a,b) ⇒ a.copy(wm = b), _.wm)
}

// vim: set ts=2 sw=2 et:
