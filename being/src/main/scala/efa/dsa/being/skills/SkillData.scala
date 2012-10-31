package efa.dsa.being.skills

import efa.rpg.core.WithId

trait SkillData[-A] extends WithId[A] {
  def talentData (a: A): TalentData
  def id (a: A) = talentData(a).id
  def tap (a: A) = talentData(a).tap
  def specialExp (a: A) = talentData(a).specialExp
}

// vim: set ts=2 sw=2 et:
