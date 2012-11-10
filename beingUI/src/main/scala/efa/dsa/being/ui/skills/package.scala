package efa.dsa.being.ui

import efa.dsa.being.skills._
import efa.nb.dialog.{DialogEditable ⇒ DE}
import scalaz.effect.IO

package object skills {
  type SkillsPanel = NodePanel[Skills,SkillDatas]

  implicit lazy val LanguageEditable =
    DE.io((a: Language) ⇒ SkillPanel.languageP(a))(_.talentIn)

  implicit lazy val MeleeEditable =
    DE.io((a: MeleeTalent) ⇒ SkillPanel.meleeP(a))(_.in)

  implicit lazy val RangedEditable =
    DE.io((a: RangedTalent) ⇒ SkillPanel.rangedP(a))(_.talentIn)

  implicit lazy val RitualEditable =
    DE.io((a: Ritual) ⇒ SkillPanel.ritualP(a))(_.talentIn)

  implicit lazy val ScriptureEditable =
    DE.io((a: Scripture) ⇒ SkillPanel.scriptureP(a))(_.talentIn)

  implicit lazy val SpellEditable =
    DE.io((a: Spell) ⇒ SkillPanel.spellP(a))(_.in)

  implicit lazy val TalentEditable =
    DE.io((a: Talent) ⇒ SkillPanel.talentP(a))(_.talentIn)
}

// vim: set ts=2 sw=2 et:
