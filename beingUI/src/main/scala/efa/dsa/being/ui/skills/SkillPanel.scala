package efa.dsa.being.ui.skills

import efa.core.{EndoVal, Validators}
import efa.dsa.abilities._
import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.calc.SkillLinker.{MeleeTalentLinker ⇒ MTL}
import efa.dsa.being.skills._
import efa.dsa.being.ui.DescribedPanel
import efa.dsa.world.RaisingCost
import efa.nb.VSIn
import efa.rpg.core.RpgEnum
import scalaz._, Scalaz._, effect.IO

class SkillPanel[A,B:SkillData](s: Skill[A,B])
  extends DescribedPanel[Skill[A,B]](s) {

  val rcC = comboBox(s.rc, RpgEnum[RaisingCost].values)
  val tawC = numField(s.taw)
  val specialC = checkBox(s.special)

  nameC.editable = false

  override def elems =
   (efa.core.loc.name beside nameC) above
   (bLoc.taw beside tawC) above
   (bLoc.raisingCost beside rcC) above
   (bLoc.specialExp beside specialC)

  private val perm: Int = s.taw - s.tap

  private def tawVal: EndoVal[Int] =
    Validators.interval(Tap.min + perm, Tap.max + perm)

  def talentIn: VSIn[TalentData] = ^^^(
    s.id.η[VSIn],
    intIn(tawC, tawVal) ∘ (_ - perm),
    comboBox(rcC),
    checkBox(specialC)
  )(TalentData.apply)
}

class MeleePanel(m: MeleeTalent) extends SkillPanel(m) {
  val atC = numField(m.skill.at)

  override def elems =
   (efa.core.loc.name beside nameC) above
   (bLoc.taw beside tawC) above
   (bLoc.at beside atC) above
   (bLoc.raisingCost beside rcC) above
   (bLoc.specialExp beside specialC)

  def in: VSIn[MeleeTalentData] =
    ^(talentIn, intIn(atC, MTL.atVal(m)))(MeleeTalentData.apply)
}

class SpellPanel(m: Spell) extends SkillPanel(m) {
  val houseC = checkBox(m.skill.houseSpell)
  val representationC = textField(m.skill.representation)

  override def elems =
   super.elems above
   (bLoc.houseSpell beside houseC) above
   (bLoc.representation beside representationC)

  def in: VSIn[SpellData] =
    ^^(talentIn, checkBox(houseC), stringIn(representationC))(
    SpellData.apply)
}

object SkillPanel {
  def create[A,B:SkillData](a: Skill[A,B]): IO[SkillPanel[A,B]] = for {
    p ← IO(new SkillPanel[A,B](a))
    _ ← p.adjust
  } yield p

  def languageP (l: Language) = create(l)

  def meleeP (a: MeleeTalent): IO[MeleePanel] = for {
    p ← IO(new MeleePanel(a))
    _ ← p.adjust
  } yield p

  def rangedP (l: RangedTalent) = create(l)

  def ritualP (l: Ritual) = create(l)

  def scriptureP (l: Scripture) = create(l)

  def spellP (a: Spell): IO[SpellPanel] = for {
    p ← IO(new SpellPanel(a))
    _ ← p.adjust
  } yield p

  def talentP (l: Talent) = create(l)
}

// vim: set ts=2 sw=2 et:
