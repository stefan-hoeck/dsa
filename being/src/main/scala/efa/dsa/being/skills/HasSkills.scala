package efa.dsa.being.skills

import efa.rpg.core.DB
import scalaz._, Scalaz._

trait HasSkills[-A] {
  def skills (a: A): Skills

  def meleeAtPa (name: String, a: A): Option[(Int,Int)] = {
    def atpa (m: MeleeTalent) = (m.skill.at, m.taw - m.skill.at)

    melee(name, a) map atpa
  }

  def melee (name: String, a: A): Option[MeleeTalent] =
    byName(name, _.meleeTalents, a)

  def ranged (name: String, a: A): Option[RangedTalent] =
    byName(name, _.rangedTalents, a)

  def rangedTaw (name: String, a: A): Option[Long] =
    ranged(name, a) map (_.taw)

  def byName[B,C](name: String, db: Skills ⇒ DB[Skill[B,C]], a: A)
    : Option[Skill[B,C]] = db(skills(a)) find (_._2.name ≟ name) map (_._2)
}

trait HasSkillsFunctions {
  import efa.dsa.being.skills.{HasSkills ⇒ HS}

  def melee[A:HS] (name: String, a: A): Option[MeleeTalent] =
    HS[A] melee (name, a)

  def meleeAtPa[A:HS] (name: String, a: A): Option[(Int,Int)] =
    HS[A] meleeAtPa (name, a)

  def ranged[A:HS] (name: String, a: A): Option[RangedTalent] =
    HS[A] ranged (name, a)

  def rangedTaw[A:HS] (name: String, a: A): Option[Long] =
    HS[A] rangedTaw (name, a)
}

object HasSkills extends HasSkillsFunctions {
  def apply[A:HasSkills]: HasSkills[A] = implicitly
}

// vim: set ts=2 sw=2 et:
