package efa.dsa.being.ui

import efa.dsa.being.skills._
import efa.nb.dialog.{DialogEditable ⇒ DE}
import scalaz.effect.IO

package object skills {
  type SkillsPanel = NodePanel[Skills,SkillDatas]

  implicit lazy val LanguageEditable =
    DE.io1((a: Language) ⇒ SkillPanel.languageP(a))(_.talentIn)

  implicit lazy val MeleeEditable =
    DE.io1((a: MeleeTalent) ⇒ SkillPanel.meleeP(a))(_.in)

  implicit lazy val RangedEditable =
    DE.io1((a: RangedTalent) ⇒ SkillPanel.rangedP(a))(_.talentIn)

  implicit lazy val RitualEditable =
    DE.io1((a: Ritual) ⇒ SkillPanel.ritualP(a))(_.talentIn)

  implicit lazy val ScriptureEditable =
    DE.io1((a: Scripture) ⇒ SkillPanel.scriptureP(a))(_.talentIn)

  implicit lazy val SpellEditable =
    DE.io1((a: Spell) ⇒ SkillPanel.spellP(a))(_.in)

  implicit lazy val TalentEditable =
    DE.io1((a: Talent) ⇒ SkillPanel.talentP(a))(_.talentIn)
}

// vim: set ts=2 sw=2 et:
