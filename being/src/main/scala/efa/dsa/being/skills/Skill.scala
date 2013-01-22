package efa.dsa.being.skills

import efa.data.{UniqueId, DescribedFunctions}
import efa.dsa.world.{Skt, RaisingCost}
import efa.dsa.abilities.SkillItem
import efa.dsa.being.loc
import efa.rpg.core.{Modifier, Described}
import scalaz._, Scalaz._

case class Skill[A,B](
  item: A,
  skill: B,
  permanentTaw: Int,
  modifiers: List[Modifier]
)(implicit SI:SkillItem[A], SL:SkillData[B]) {

  def name = SI name item

  def desc = SI desc item

  def fullDesc = SI fullDesc item

  def data: TalentData = SL talentData skill

  def rc: RaisingCost = data.raisingCost

  def special: Boolean = data.specialExp

  def id: Int = data.id

  def tap: Int = data.tap

  lazy val taw = modifiers foldMap (_.value) toInt

  lazy val raiseAp: Int =
    Skt cost (special? rc.lower | rc , permanentTaw)
  
  lazy val lowerAp: Int =
    (tap ≟ 0) ? 0 | Skt.cost(rc, permanentTaw - 1)
}

object Skill extends DescribedFunctions {

  implicit def SkillEqual[A:Equal,B:Equal]: Equal[Skill[A,B]] =
    Equal.equalA

  def modifiers[A:SkillItem,B:SkillData]: Skill[A,B] @> List[Modifier] =
    Lens.lensu((a,b) ⇒ a.copy(modifiers = b), _.modifiers)
  
  def permanentTaw[A:SkillItem,B:SkillData]: Skill[A,B] @> Int =
    Lens.lensu((a,b) ⇒ a.copy(permanentTaw = b), _.permanentTaw)

  implicit def SkillItem[A,B] =
    new Described[Skill[A,B]] with UniqueId[Skill[A,B],Int]{
      def name (a: Skill[A,B]) = a.name
      def id (a: Skill[A,B]) = a.id
      def desc (a: Skill[A,B]) = a.desc
      def fullDesc (a: Skill[A,B]) = a.fullDesc

      def shortDesc (a: Skill[A,B]) = {
        def rcTag = (loc.raisingCost, a.rc.toString)
        def raiseTag = (loc.raise, a.raiseAp.toString)
        def lowerTag = (loc.lower, a.lowerAp.toString)

        namePlusTags (a.name, rcTag, raiseTag, lowerTag)
      }
    }
}

// vim: set ts=2 sw=2 et:
