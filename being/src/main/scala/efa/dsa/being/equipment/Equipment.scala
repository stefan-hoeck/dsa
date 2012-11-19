package efa.dsa.being.equipment

import efa.core.UniqueId
import efa.dsa.being.{HumanoidBaseData ⇒ HBD}
import efa.dsa.equipment.EquipmentItem
import efa.rpg.core.{Described, HtmlTags}
import scalaz.{Equal, Scalaz, State}

case class Equipment[A,B](item: A, data: B, hands: HandsData)
(implicit EI: EquipmentItem[A], ED:EquipmentData[B]) {
  def name: String = ED.name get data
  def id: Int = ED id data
  def parentId: Int = ED parentId data
  def desc: String = ED.desc get data
  def fullDesc = "<P>%s</P>%s" format (desc, EI desc item)
  def price: Long = ED.price get data
  def weight: Long = ED.weight get data

  def fullPrice: Long = ED fullPrice data
  def fullWeight: Long = ED fullWeight data

  def leftEquipped: Boolean = ED leftEquipped (data, hands)
  def rightEquipped: Boolean = ED rightEquipped (data, hands)
}

object Equipment extends HtmlTags {
  implicit def EquipmentEqual[A:Equal,B:Equal] = Equal.equalA[Equipment[A,B]]

  implicit def EquipmentItem[A,B] =
    new Described[Equipment[A,B]] with UniqueId[Equipment[A,B],Int]{
      def name (a: Equipment[A,B]) = a.name
      def id (a: Equipment[A,B]) = a.id
      def desc (a: Equipment[A,B]) = a.desc
      def shortDesc (a: Equipment[A,B]) = html(a.name, a.desc)
      def fullDesc (a: Equipment[A,B]) = titleBody(a.name, a.fullDesc)
    }
}

// vim: set ts=2 sw=2 et:
