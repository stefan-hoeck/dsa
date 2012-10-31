package efa.dsa.equipment.services.ui

import efa.dsa.equipment.ArticleItem
import efa.rpg.items.ItemPair
import scalaz._, Scalaz._, effect.IO

class ArticlePanel (p: ItemPair[ArticleItem]) extends EquipmentLikePanel (p) {
  def in = eqIn

  protected def lbls = Nil
  protected def fields = Nil
}

object ArticlePanel {
  def apply(p: ItemPair[ArticleItem]) = IO(new ArticlePanel(p))
}

// vim: set ts=2 sw=2 et:
