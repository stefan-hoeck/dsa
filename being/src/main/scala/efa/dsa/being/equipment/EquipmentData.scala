package efa.dsa.being.equipment

import efa.core.{ToXml, Efa, Default}, Efa._
import efa.dsa.equipment.EquipmentItemData
import efa.dsa.equipment.spi.EquipmentLocal
import efa.rpg.core.{WithId, Util, ItemData}, ItemData.itemDataLenses
import org.scalacheck.Gen
import scala.xml.Node
import scalaz._, Scalaz._

trait EquipmentData[A] extends WithId[A] with Default[A] {
  def eData: A @> EquipmentItemData
  def parentIdL: A @> Int
  def parentId (a: A): Int = parentIdL get a
  def name: A @> String = eData.data.name
  def idL: A @> Int = eData.data.id
  def id (a: A): Int = idL get a
  def desc: A @> String = eData.data.desc
  def price: A @> Long = eData.price
  def weight: A @> Long = eData.weight
}

trait EquipmentLike[+A] {
  def eData: EquipmentItemData
  def eData_= (v: EquipmentItemData): A
  
  def parentId: Int
  def parentId_= (i: Int): A

  def name = eData.data.name
  def desc = eData.data.desc
  def id = eData.data.id
  def price = eData.price
  def weight = eData.weight
}

trait EquipmentLikes[A <: EquipmentLike[A]] extends Util {
  self ⇒ 

  def default: A

  private def eDataToXml = implicitly[ToXml[EquipmentItemData]]

  protected def readEData (ns: Seq[Node]) = eDataToXml fromXml ns

  protected def readParentId (ns: Seq[Node]) = ns.readTag[Int]("parentId")

  protected def eDataNodes (a: A) =
    (eDataToXml toXml a.eData) ++ ("parentId" xml a.parentId)

  protected val eDataGen: Gen[EquipmentItemData] = !![EquipmentItemData]

  protected val parentIdGen: Gen[Int] = !![Int]

  protected def eData (name: EquipmentLocal ⇒ String) =
    EquipmentItemData fromName name(efa.dsa.equipment.loc)

  implicit lazy val AEquipment = new EquipmentData[A] {
    val default = self.default
    val eData: A @> EquipmentItemData = Lens.lensu(_ eData_= _, _.eData)
    def parentIdL: A @> Int = Lens.lensu(_ parentId_= _, _.parentId)
  }

  implicit lazy val AEqual = Equal.equalA[A]
}

// vim: set ts=2 sw=2 et:
