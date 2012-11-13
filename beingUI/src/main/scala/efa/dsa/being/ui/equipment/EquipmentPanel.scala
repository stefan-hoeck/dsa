package efa.dsa.being.ui.equipment

import efa.core._
import efa.dsa.being.equipment._
import efa.dsa.being.ui.DescribedPanel
import efa.dsa.equipment.{Price, Weight, EquipmentItemData}
import efa.dsa.world.mittelreich.{Coin, Weight ⇒ MWeight}
import efa.nb.VSIn
import efa.rpg.core.{UnitEnum, ItemData}
import scala.swing.Component
import scalaz._, Scalaz._

abstract class EquipmentPanel[A,B] (e: Equipment[A,B]) 
   extends DescribedPanel[Equipment[A,B]](e){

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
  protected def bl = efa.dsa.being.loc

  protected def lbls: List[String]
  protected def fields: List[Component]
    
  lazy val priceC = numField(e.price, formatP)
  lazy val weightC = numField(e.weight, formatW)

  def elems = {
    def allLbls = ul.name :: (lbls ::: List(el.price, el.weight))
    def lblElems = (allLbls foldMap (x ⇒ x: Elem)) above descLbl
    def allFields = nameC :: (fields ::: List(priceC, weightC))
    def fieldElems = allFields foldMap (x ⇒ x: Elem)

    lblElems beside (fieldElems above descElem)
  }

  protected def eqIn: VSIn[EquipmentItemData] =
    ^^(
      ^^(e.id.η[VSIn], stringIn(nameC), stringIn(descC))(ItemData.apply),
      textIn(priceC, Price.validate)(parseP),
      textIn(weightC, Weight.validate)(parseW))(EquipmentItemData.apply)

  protected def parentIn: VSIn[Int] = e.parentId.η[VSIn]
}

// vim: set ts=2 sw=2 et:
