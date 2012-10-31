package efa.dsa.abilities.services.ui

import efa.rpg.core.RpgItem
import efa.rpg.items.ItemPair
import efa.rpg.items.controller.ItemPanel
import scala.swing.Component
import scalaz._, Scalaz._

abstract class AbilityPanel[A:RpgItem] (p: ItemPair[A]) extends ItemPanel[A](p) {
  protected def ul = efa.core.loc
  protected def il = efa.dsa.abilities.loc

  protected def lbls: List[String]
  protected def fields: List[Component]

  protected def elems = {
    def allLbls = ul.name +: lbls
    def lblElems = allLbls foldMap (stringElem(_): Elem) above descLbl
    def fieldElems = (nameC +: fields) foldMap (compElem(_): Elem)

    lblElems beside (fieldElems above descElem)
  }
}

// vim: set ts=2 sw=2 et:
