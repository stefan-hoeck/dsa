package efa.dsa.equipment

import efa.core.{ToXml, Efa}, Efa._
import efa.dsa.world.{ShieldSize, ShieldType, Wm}
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.{Arbitrary, Gen}

case class ShieldItem (
  eData: EquipmentItemData,
  size: ShieldSize,
  shieldType: ShieldType,
  ini: Int,
  bf: Int,
  wm: Wm
) extends EquipmentLike[ShieldItem] {
  require(Ini validate ini isRight)
  require(Bf validate bf isRight)
  
  def eData_= (v: EquipmentItemData) = copy (eData = v)
}

object ShieldItem extends EquipmentItemLikes[ShieldItem] {
  lazy val default = ShieldItem (eData(loc.shield), !!, !!, 0, 0, !!)

  def shortDesc (i: ShieldItem) = {
    def iniTag = (loc.ini, i.ini.toString)
    def bfTag = (loc.bf, i.bf.toString)
    def wmTag = (loc.wm, i.wm.shows)

    tagShortDesc (i, iniTag, bfTag, wmTag, priceTag(i), weightTag(i))
  }

  implicit lazy val ShieldItemToXml = new ToXml[ShieldItem] {
    def fromXml (ns: Seq[Node]) =
      ^(readEData (ns),
        ns.tagged[ShieldSize],
        ns.tagged[ShieldType],
        Ini read ns,
        Bf read ns,
        ns.tagged[Wm])(ShieldItem.apply)

    def toXml (a: ShieldItem) = 
      dataToNode (a) ++
      xml(a.size) ++
      xml(a.shieldType) ++
      Ini.write(a.ini) ++
      Bf.write(a.bf) ++
      xml(a.wm)
  }

  implicit lazy val ShieldItemArbitrary = Arbitrary (
    ^(a[EquipmentItemData],
      a[ShieldSize],
      a[ShieldType],
      Ini.gen,
      Bf.gen,
      a[Wm])(ShieldItem.apply)
  )

  val size: ShieldItem @> ShieldSize =
    Lens.lensu((a,b) ⇒ a.copy(size = b), _.size)

  val shieldType: ShieldItem @> ShieldType =
    Lens.lensu((a,b) ⇒ a.copy(shieldType = b), _.shieldType)

  val ini: ShieldItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(ini = b), _.ini)

  val bf: ShieldItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(bf = b), _.bf)

  val wm: ShieldItem @> Wm = Lens.lensu((a,b) ⇒ a.copy(wm = b), _.wm)
}

// vim: set ts=2 sw=2 et:
