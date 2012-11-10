package efa.dsa.being

import efa.dsa.abilities._
import efa.rpg.core.RangeVals
import scalaz.@>

package object skills extends RangeVals {
  val Tap = fullInfo(0, 999, "tap")

  val At = fullInfo(-Tap.max, Tap.max, "at")

  type Language = Skill[LanguageItem,TalentData]

  type MeleeTalent = Skill[MeleeTalentItem,MeleeTalentData]

  type RangedTalent = Skill[RangedTalentItem,TalentData]
 
  type Scripture = Skill[ScriptureItem,TalentData]

  type Ritual = Skill[RitualItem,TalentData]

  type Spell = Skill[SpellItem,SpellData]

  type Talent = Skill[TalentItem,TalentData]

  private[skills] def skillData[A](l: A @> TalentData, a: A): SkillData[A]
    = new SkillData[A] {
      def talentDataL = l
      val default = a
    }
}

// vim: set ts=2 sw=2 et:
