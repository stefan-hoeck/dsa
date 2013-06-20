package efa.dsa.being.ui.skills

import dire._, dire.swing._, Swing._
import efa.core.{EndoVal, Validators}
import efa.dsa.abilities._
import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.calc.SkillLinker.{MeleeTalentLinker ⇒ MTL}
import efa.dsa.being.skills._
import efa.dsa.being.ui.DescribedPanel
import efa.dsa.world.RaisingCost
import efa.nb.Widgets._
import efa.nb.dialog.{DialogEditable ⇒ DE, DEInfo}
import efa.rpg.core.RpgEnum
import scalaz._, Scalaz._, effect.IO

object implicits {
  implicit lazy val LanguageE = DE.io1((a: Language) ⇒ info(a))
  implicit lazy val MeleeE = DE io1 melee
  implicit lazy val RangedE = DE.io1((a: RangedTalent) ⇒ info(a))
  implicit lazy val RitualE = DE.io1((a: Ritual) ⇒ info(a))
  implicit lazy val ScriptureE = DE.io1((a: Scripture) ⇒ info(a))
  implicit lazy val SpellE = DE io1 spell
  implicit lazy val TalentE = DE.io1((a: Talent) ⇒ info(a))

  private def info[A,B:SkillData](s: Skill[A,B]): DEInfo[TalentData] =
    SkillPanel(s) map (p ⇒ (p.dp size p.elem, p.in))

  private def melee(s: MeleeTalent): DEInfo[MeleeTalentData] = for {
    sp ← SkillPanel(s)
    ap ← TextField trailing s.skill.at.toString

    in = ^(sp.in, intIn(ap.in, MTL atVal s))(MeleeTalentData.apply)
    el = (efa.core.loc.name beside sp.dp.name) above
         (bLoc.taw beside sp.taw) above
         (bLoc.at beside ap) above
         (bLoc.raisingCost beside sp.rc) above
         (bLoc.specialExp beside sp.special)
  } yield (sp.dp size el, in)

  private def spell(s: Spell): DEInfo[SpellData] = for {
    sp    ← SkillPanel(s)
    house ← CheckBox(selected := s.skill.houseSpell)
    repr  ← TextField text s.skill.representation

    in = ^^(sp.in, valid(house.in), valid(repr.in))(SpellData.apply)
    el = sp.elem above
         (bLoc.houseSpell beside house) above
         (bLoc.representation beside repr)
  } yield (sp.dp size el, in)
}

final class SkillPanel[A,B](
    val dp: DescribedPanel[Skill[A,B]],
    val rc: ComboBox[RaisingCost],
    val taw: TextField,
    val special: CheckBox){
  private val perm: Int = dp.a.taw - dp.a.tap

  private def tawVal: EndoVal[Int] =
    Validators.interval(Tap.min + perm, Tap.max + perm)

  def in: VSIn[TalentData] = ^^^(
    vsin(dp.a.id),
    intIn(taw.in, tawVal) ∘ (_ - perm),
    valid(rc.in),
    valid(special.in)
  )(TalentData.apply)

  def elem =
   (efa.core.loc.name beside dp.name) above
   (bLoc.taw beside taw) above
   (bLoc.raisingCost beside rc) above
   (bLoc.specialExp beside special)
}

object SkillPanel {
  def apply[A,B:SkillData](s: Skill[A,B]): IO[SkillPanel[A,B]] = for {
    dp  ← DescribedPanel(s)
    rc  ← ComboBox(RaisingCost.values, s.rc)
    taw ← TextField trailing s.taw.toString
    sp  ← CheckBox(selected := s.special)
    _   ← dp.name properties (editable := false)
  } yield new SkillPanel(dp, rc, taw, sp)
}

// vim: set ts=2 sw=2 et:
