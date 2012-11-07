package efa.dsa.being.skills

import efa.dsa.world.RaisingCost
import efa.rpg.core.WithId
import scalaz.@>

trait SkillData[A] extends WithId[A] {
  def talentDataL: A @> TalentData
  def tapL: A @> Int = talentDataL >=> TalentData.tap
  def specialExpL: A @> Boolean = talentDataL >=> TalentData.specialExp
  def raisingCostL: A @> RaisingCost = talentDataL >=> TalentData.raisingCost
  def talentData (a: A): TalentData = talentDataL get a
  def id (a: A) = talentData(a).id
  def tap (a: A) = talentData(a).tap
  def specialExp (a: A) = talentData(a).specialExp
}

object SkillData {
  def apply[A:SkillData]: SkillData[A] = implicitly
}

// vim: set ts=2 sw=2 et:
