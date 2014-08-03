package efa.dsa.equipment

import efa.core.ToXml
import efa.dsa.world.mittelreich.{Coin, Weight ⇒ MWeight}
import efa.rpg.core._
import scala.xml.Node
import scalaz._, Scalaz._

trait EquipmentItem[A] extends RpgItem[A] {
  def price (a: A): Long
  def weight (a: A): Long
}

trait EquipmentLike[+A] extends RpgItemLike[A] {
  def eData: EquipmentItemData
  def eData_= (v: EquipmentItemData): A

  override def data_= (data: ItemData) = eData_= (eData copy (data = data))
  override def data = eData.data
  def weight: Long = eData.weight
  def price: Long = eData.price
}

trait EquipmentItemLikes[A<:EquipmentLike[A]] extends RpgItemLikes[A] {
  self ⇒ 

  protected def priceTag (a: A): (String, String) =
    (loc.price, UnitEnum[Coin].showPretty(Coin.S, 2)(a.price))

  protected def weightTag (a: A): (String, String) =
    (loc.price, UnitEnum[MWeight].showPretty(MWeight.U, 4)(a.weight))

  private def equipmentToXml = implicitly[ToXml[EquipmentItemData]]

  override protected def dataToNode (a: A): Seq[Node] = 
    equipmentToXml toXml a.eData

  protected def readEData (ns: Seq[Node]) =
    equipmentToXml fromXml ns

  protected def eData (name: String) = EquipmentItemData fromName name

  override implicit lazy val asRpgItem = new EquipmentItem[A] {
    lazy val dataL = Lens.lensu[A,ItemData](_ data_= _, _.data)
    def price (a: A) = a.price
    def weight (a: A) = a.weight

    def shortDesc (a: A) = self shortDesc a
    def fullDesc (a: A) = self fullDesc a
  }
}

// vim: set ts=2 sw=2 et:
