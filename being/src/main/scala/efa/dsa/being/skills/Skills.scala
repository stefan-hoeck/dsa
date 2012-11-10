package efa.dsa.being.skills

import efa.core.Default
import efa.rpg.core.DB
import scalaz._, Scalaz._

case class Skills(
  languages: DB[Language], 
  meleeTalents: DB[MeleeTalent], 
  rangedTalents: DB[RangedTalent], 
  rituals: DB[Ritual],
  scriptures: DB[Scripture], 
  spells: DB[Spell], 
  talents: DB[Talent] 
)

object Skills {

  private def db[A]: DB[A] = Map.empty

  val default = Skills(db, db, db, db, db, db, db)

  implicit val SkillsDefault = Default default default

  implicit val SkillsEqual = Equal.equalA[Skills]

  val languages: Skills @> DB[Language] =
    Lens.lensu((a,b) ⇒ a.copy(languages = b), _.languages)
  
  val meleeTalents: Skills @> DB[MeleeTalent] =
    Lens.lensu((a,b) ⇒ a.copy(meleeTalents = b), _.meleeTalents)

  val rangedTalents: Skills @> DB[RangedTalent] =
    Lens.lensu((a,b) ⇒ a.copy(rangedTalents = b), _.rangedTalents)

  val rituals: Skills @> DB[Ritual] =
    Lens.lensu((a,b) ⇒ a.copy(rituals = b), _.rituals)

  val scriptures: Skills @> DB[Scripture] =
    Lens.lensu((a,b) ⇒ a.copy(scriptures = b), _.scriptures)

  val spells: Skills @> DB[Spell] =
    Lens.lensu((a,b) ⇒ a.copy(spells = b), _.spells)
  
  val talents: Skills @> DB[Talent] =
    Lens.lensu((a,b) ⇒ a.copy(talents = b), _.talents)
}

// vim: set ts=2 sw=2 et:
