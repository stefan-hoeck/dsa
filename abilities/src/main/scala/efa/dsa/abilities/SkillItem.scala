package efa.dsa.abilities

import efa.dsa.world.RaisingCost
import efa.rpg.core.{RpgItem, RpgItemLike, RpgItemLikes, ItemData, DB}
import scalaz.Lens

trait SkillItem[A] extends RpgItem[A] {
  def raisingCost (a: A): RaisingCost
}

trait SkillItemLike[+A] extends RpgItemLike[A] {
  def raisingCost: RaisingCost
}

trait SkillItemLikes[A<:SkillItemLike[A]] extends RpgItemLikes[A] {
  self ⇒

  override implicit lazy val asRpgItem = new SkillItem[A] {
    lazy val dataL = Lens.lensu[A,ItemData](_ data_= _, _.data)
    def raisingCost(a: A) = a.raisingCost
    def shortDesc (a: A) = self shortDesc a
    def fullDesc (a: A) = self fullDesc a
  }
}

// vim: set ts=2 sw=2 et:
