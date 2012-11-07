package efa.dsa.equipment

import efa.core.{ToXml, Validators, Efa}, Efa._
import efa.dsa.world.{Rs, Be}
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class ArmorItem (
  eData: EquipmentItemData,
  rs: Int,
  be: Int,
  isAddition: Boolean
) extends EquipmentLike[ArmorItem] {
  require(Rs validate rs isRight)
  require(Be validate be isRight)
  
  def eData_= (v: EquipmentItemData) = copy (eData = v)
}

object ArmorItem extends EquipmentItemLikes[ArmorItem] {
  lazy val default = ArmorItem (eData(loc.armor), 0, 0, false)

  def shortDesc (i: ArmorItem) = {
    def rsTag = (loc.rs, i.rs.toString)
    def beTag = (loc.be, i.be.toString)

    tagShortDesc (i, rsTag, beTag, priceTag(i), weightTag(i))
  }

  implicit lazy val ArmorItemToXml = new ToXml[ArmorItem] {
    def fromXml (ns: Seq[Node]) =
      ^^^(readEData(ns), Rs read ns, Be read ns, Addition read ns)(
        ArmorItem.apply)

    def toXml (a: ArmorItem) = 
      dataToNode(a) ++
      Rs.write(a.rs) ++
      Be.write(a.be) ++
      Addition.write(a.isAddition)
  }

  implicit lazy val ArmorItemArbitrary = Arbitrary (
    ^^^(a[EquipmentItemData], Rs.gen, Be.gen, a[Boolean])(ArmorItem.apply)
  )

  val rs: ArmorItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(rs = b), _.rs)

  val be: ArmorItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(be = b), _.be)

  val isAddition: ArmorItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(isAddition = b), _.isAddition)
}

// vim: set ts=2 sw=2 et:
