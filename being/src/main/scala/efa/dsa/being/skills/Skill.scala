package efa.dsa.being.skills

import efa.dsa.world.{Skt, RaisingCost}
import efa.dsa.abilities.SkillItem
import efa.rpg.core.Modifier
import scalaz._, Scalaz._

case class Skill[A,B](
  item: A,
  skill: B,
  permanentTaw: Int,
  modifiers: List[Modifier]
)(implicit SI:SkillItem[A], SL:SkillData[B]) {

  def name = SI name item

  def data: TalentData = SL talentData skill

  def rc: RaisingCost = data.raisingCost

  def specialExp: Boolean = data.specialExp

  def id: Int = data.id

  def tap: Int = data.tap

  lazy val taw = modifiers foldMap (_.value) toInt

  lazy val raiseAp: Int =
    Skt cost (specialExp ? rc.lower | rc , permanentTaw)
  
  lazy val lowerAp: Int =
    (tap ≟ 0) ? 0 | Skt.cost(rc, permanentTaw - 1)
}

object Skill {

  implicit def SkillEqual[A:Equal,B:Equal]: Equal[Skill[A,B]] =
    Equal.equalA

  def modifiers[A,B]: Skill[A,B] @> List[Modifier] =
    Lens.lensu((a,b) ⇒ a.copy(modifiers = b), _.modifiers)
  
  def permanentTaw[A,B]: Skill[A,B] @> Int =
    Lens.lensu((a,b) ⇒ a.copy(permanentTaw = b), _.permanentTaw)
}

// vim: set ts=2 sw=2 et:
