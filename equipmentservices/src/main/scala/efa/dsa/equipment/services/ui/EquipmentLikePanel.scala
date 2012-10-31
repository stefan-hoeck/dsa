package efa.dsa.equipment.services.ui

import efa.core.Efa._
import efa.core.Read
import efa.nb.VSIn
import efa.dsa.equipment.{EquipmentItem, Price, Weight, EquipmentItemData}
import efa.dsa.world.mittelreich.{Coin, Weight ⇒ MWeight}
import efa.rpg.core.{RpgItem, UnitEnum}
import efa.rpg.items.ItemPair
import efa.rpg.items.controller.ItemPanel
import scala.swing.Component
import scalaz._, Scalaz._

abstract class EquipmentLikePanel[A:EquipmentItem](p: ItemPair[A])
   extends ItemPanel[A](p) {

  protected def equipment: EquipmentItem[A] = implicitly

  protected def pricePrecision = 2
  protected def baseCoin: Coin = Coin.S
  final protected lazy val formatP =
    UnitEnum[Coin] showPretty (baseCoin, pricePrecision)

  final protected lazy val parseP =
    Read readV (UnitEnum[Coin] readPretty baseCoin)

  protected def weightPrecision = 4
  protected def baseWeight: MWeight = MWeight.U
  final protected lazy val formatW =
    UnitEnum[MWeight] showPretty (baseWeight, weightPrecision)

  final protected lazy val parseW =
    Read readV (UnitEnum[MWeight] readPretty baseWeight)

  protected def ul = efa.core.loc
  protected def el = efa.dsa.equipment.loc

  protected def lbls: List[String]
  protected def fields: List[Component]
    
  lazy val priceC = numField(equipment price item, formatP)
  lazy val weightC = numField(equipment weight item, formatW)

  def elems = {
    def allLbls = ul.name :: (lbls ::: List(el.price, el.weight))
    def lblElems = (allLbls foldMap (x ⇒ x: Elem)) above descLbl
    def allFields = nameC :: (fields ::: List(priceC, weightC))
    def fieldElems = allFields foldMap (x ⇒ x: Elem)

    lblElems beside (fieldElems above descElem)
  }

  protected def eqIn: VSIn[EquipmentItemData] =
    ^(dataIn,
    textIn(priceC, Price.validate)(parseP),
    textIn(weightC, Weight.validate)(parseW))(EquipmentItemData.apply)
}

// vim: set ts=2 sw=2 et:
