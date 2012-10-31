package efa.dsa.being.equipment

import efa.dsa.equipment.EquipmentItem
import scalaz.{Equal, Scalaz}

case class Equipment[A,B](item: A, data: B)
(implicit EI: EquipmentItem[A], ED:EquipmentData[B]) {
  def name: String = ED.name get data
  def id: Int = ED id data
}

object Equipment {
  implicit def EquipmentEqual[A:Equal,B:Equal] = Equal.equalA[Equipment[A,B]]
}

// vim: set ts=2 sw=2 et:
