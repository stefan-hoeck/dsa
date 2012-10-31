package efa.dsa.equipment

import efa.core.{ToXml, Efa}, Efa._
import efa.rpg.core.ItemData
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class EquipmentItemData (
  override val data: ItemData,
  override val price: Long,
  override val weight: Long
) extends EquipmentLike[EquipmentItemData] {
  require(Price validate price isRight)
  require(Weight validate weight isRight)

  def eData: EquipmentItemData = this
  def eData_= (v: EquipmentItemData) = v
}

object EquipmentItemData extends EquipmentItemLikes[EquipmentItemData]  {
  lazy val default = EquipmentItemData(!!, Price.min, Weight.min)

  def fromName (n: String): EquipmentItemData =
    EquipmentItemData(ItemData(n), Price.min, Weight.min)

  def shortDesc (i: EquipmentItemData) = 
    tagShortDesc (i, priceTag(i), weightTag(i))


  implicit lazy val EquipmentItemDataToXml = new ToXml[EquipmentItemData] {
    lazy val dataToXml = ToXml[ItemData]

    def fromXml (ns: Seq[Node]) =
     ^(dataToXml fromXml ns,
       Price read ns,
       Weight read ns)(EquipmentItemData.apply)

    def toXml (a: EquipmentItemData) = 
      dataToXml.toXml(a.data) ++ Price.write(a.price) ++ Weight.write(a.weight)
  }

  implicit lazy val EquipmentItemDataArbitrary = Arbitrary (
    ^(a[ItemData], Price.gen, Weight.gen)(EquipmentItemData.apply)
  )

  val data: EquipmentItemData @> ItemData =
    Lens.lensu((a,b) ⇒ a.copy(data = b), _.data)

  val price: EquipmentItemData @> Long =
    Lens.lensu((a,b) ⇒ a.copy(price = b), _.price)

  val weight: EquipmentItemData @> Long =
    Lens.lensu((a,b) ⇒ a.copy(weight = b), _.weight)

  implicit def EIDLenses[A] (l: A @> EquipmentItemData) = new {
    def data = l >=> EquipmentItemData.data
    def price = l >=> EquipmentItemData.price
    def weight = l >=> EquipmentItemData.weight
  }
}

// vim: set ts=2 sw=2 et:
