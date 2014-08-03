package efa.dsa.being.equipment

import efa.core.{ToXml, Efa, Default, UniqueIdL, NamedL}, Efa._
import efa.core.syntax.{string, nodeSeq}
import efa.dsa.being.{HumanoidData ⇒ HD}
import efa.dsa.equipment.EquipmentItemData
import efa.dsa.equipment.spi.EquipmentLocal
import efa.rpg.core.{Util, ItemData}, ItemData.itemDataLenses
import org.scalacheck.Gen
import scala.xml.Node
import scalaz._, Scalaz._

trait EquipmentData[A] extends UniqueIdL[A,Int] with Default[A] with NamedL[A] {
  def eData: A @> EquipmentItemData
  def parentIdL: A @> Int
  def parentId (a: A): Int = parentIdL get a
  def nameL: A @> String = eData.data.name
  def idL: A @> Int = eData.data.id
  def desc: A @> String = eData.data.desc
  def price: A @> Long = eData.price
  def weight: A @> Long = eData.weight

  def fullWeight (a: A): Long
  def fullPrice (a: A): Long

  def leftEquipped (a: A, h: HandsData): Boolean
  def rightEquipped (a: A, h: HandsData): Boolean

  def equipLeft (a: A, b: Boolean): State[HD,Unit]
  def equipRight (a: A, b: Boolean): State[HD,Unit]
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

  def fullPrice = price
  def fullWeight = weight

  def equipped (h: HandData): Boolean = false

  def handData: Option[HandData] = None 
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
    def fullPrice (a: A) = a.fullPrice
    def fullWeight (a: A) = a.fullWeight
    def leftEquipped (a: A, hs: HandsData): Boolean = a equipped hs.left
    def rightEquipped (a: A, hs: HandsData): Boolean = a equipped hs.right

    def equipLeft (a: A, b: Boolean): State[HD,Unit] =
      equip(b ? a.handData | none, true)

    def equipRight (a: A, b: Boolean): State[HD,Unit] =
      equip(b ? a.handData | none, false)

    private def equip (ho: Option[HandData], left: Boolean)
      : State[HD,Unit] = {
        val newH = ho | HandData.Empty

        HD.hands mods_ (hd ⇒ left ? hd.setLeft(newH) | hd.setRight(newH))
      }
  }

  implicit lazy val AEqual = Equal.equalA[A]
}

// vim: set ts=2 sw=2 et:
