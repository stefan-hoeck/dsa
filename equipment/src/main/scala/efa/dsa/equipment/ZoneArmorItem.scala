package efa.dsa.equipment

import efa.core.{ToXml, Efa}, Efa._
import efa.dsa.world.Be
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class ZoneArmorItem (
  eData: EquipmentItemData,
  rs: ZoneRs,
  be: Int,
  isAddition: Boolean
) extends EquipmentLike[ZoneArmorItem] {
  require(Be validate be isRight)

  def eData_= (v: EquipmentItemData) = copy (eData = v)
  
}

object ZoneArmorItem extends EquipmentItemLikes[ZoneArmorItem] {
  lazy val default =
    ZoneArmorItem (eData(loc.zoneArmor), ZoneRs.!!, Be.min, false)

  def shortDesc (i: ZoneArmorItem) = {
    def zoneRsTag = (loc.rs, ZoneRs shows i.rs)
    def beTag = (loc.be, i.be.toString)

    tagShortDesc (i, zoneRsTag, beTag, priceTag(i), weightTag(i))
  }

  implicit lazy val ZoneArmorItemToXml = new ToXml[ZoneArmorItem] {
    def fromXml (ns: Seq[Node]) =
      ^^^(readEData (ns),
        ZoneRs read ns,
        Be read ns,
        Addition read ns)(ZoneArmorItem.apply)

    def toXml (a: ZoneArmorItem) = 
      dataToNode (a) ++
      ZoneRs.write(a.rs) ++
      Be.write(a.be) ++
      Addition.write(a.isAddition)
  }

  implicit lazy val ZoneArmorItemArbitrary = Arbitrary (
    ^^^(a[EquipmentItemData],
      ZoneRs.gen,
      Be.gen,
      a[Boolean])(ZoneArmorItem.apply)
  )

  val rs: ZoneArmorItem @> ZoneRs =
    Lens.lensu((a,b) ⇒ a.copy(rs = b), _.rs)

  val be: ZoneArmorItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(be = b), _.be)

  val isAddition: ZoneArmorItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(isAddition = b), _.isAddition)
}

// vim: set ts=2 sw=2 et:
