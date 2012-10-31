package efa.dsa.being.equipment

import efa.core.{ToXml, Efa}, Efa._
import efa.dsa.equipment.{EquipmentItemData, ZoneRs}
import efa.dsa.world.Be
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class ZoneArmorData(
  eData: EquipmentItemData,
  parentId: Int,
  rs: ZoneRs,
  be: Int,
  equipped: Boolean
) extends EquipmentLike[ZoneArmorData] {
  require(Be validate be isRight)

  def eData_= (v: EquipmentItemData) = copy (eData = v)
}

object ZoneArmorData extends EquipmentLikes[ZoneArmorData] {
  lazy val default =
    ZoneArmorData(eData(_.zoneArmor), 0, ZoneRs.!!, 0, false)

  implicit lazy val ZoneArmorDataArbitrary = Arbitrary (
    ^(eDataGen, parentIdGen, ZoneRs.gen, Be.gen, a[Boolean])(
      ZoneArmorData.apply)
  )

  implicit lazy val ZoneArmorDataToXml = new ToXml[ZoneArmorData] {
    def fromXml (ns: Seq[Node]) =
      ^(readEData (ns),
        readParentId (ns),
        ZoneRs read ns,
        Be read ns,
        Equipped read ns)(ZoneArmorData.apply)

    def toXml (a: ZoneArmorData) = 
      eDataNodes(a) ++
      ZoneRs.write(a.rs) ++
      Be.write(a.be) ++
      Equipped.write(a.equipped)
  }

  val eData: ZoneArmorData @> EquipmentItemData =
    Lens.lensu((a,b) ⇒ a.copy(eData = b), _.eData)

  val parentId: ZoneArmorData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val rs: ZoneArmorData @> ZoneRs =
    Lens.lensu((a,b) ⇒ a.copy(rs = b), _.rs)

  val be: ZoneArmorData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(be = b), _.be)

  val equipped: ZoneArmorData @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(equipped = b), _.equipped)
}

// vim: set ts=2 sw=2 et:
