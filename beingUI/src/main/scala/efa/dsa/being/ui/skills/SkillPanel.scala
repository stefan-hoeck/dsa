package efa.dsa.being.ui.skills

import efa.core.{EndoVal, Validators}
import efa.dsa.being.{loc ⇒ bLoc}
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

  private val perm = s.taw - s.tap

  private def tawVal: EndoVal[Int] =
    Validators.interval(Tap.min + perm, Tap.max + perm)

  def in: VSIn[B] = ^^(
    comboBox(rcC),
    intIn(tawC, tawVal),
    checkBox(specialC)
  )((rc,taw,sp) ⇒ (
      (SkillData[B].raisingCostL := rc) >>
      (SkillData[B].tapL := (taw - perm)) >>
      (SkillData[B].specialExpL := sp)
    ) exec s.skill
  )
}

object SkillPanel {
  def create[A,B:SkillData](a: Skill[A,B]): IO[SkillPanel[A,B]] = for {
    p ← IO(new SkillPanel[A,B](a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
