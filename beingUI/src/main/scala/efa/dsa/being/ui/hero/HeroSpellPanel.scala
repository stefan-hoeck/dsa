package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being.skills._
import efa.dsa.being.ui.{loc, NodePanel, AttributesPanel}
import efa.dsa.being.ui.skills.{SkillNodes ⇒ SN}
import efa.rpg.being.MVPanel
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroSpellPanel (spellP: NodePanel[Skills,HeroData])
  extends MVPanel[Hero,HeroData](
    "DSA_HeroSpellPanel", loc.spellsPanel, efa.dsa.being.ui.version
  ){

  val attributesP = new AttributesPanel[Hero,HeroData]
  val apP = new HeroApPanel

  attributesP.border = titledBorder(loc.attributes)
  apP.border = titledBorder(loc.ap)
  spellP.border = titledBorder(loc.spells)

  (attributesP beside apP) above (spellP fillH 2 fillV 1) add()

  def set =
    (spellP.set ∙ ((_: Hero).skills)) ⊹
    attributesP.set ⊹
    (lensedV(apP.set)(HeroData.base) ∙ (_.data))

  override def persistentChildren = List(spellP)
  override lazy val getLookup = spellP.getLookup
}

object HeroSpellPanel {
  def create: IO[HeroSpellPanel] = for {
    a ← NodePanel(SN.spellsOut, "DSA_Spells_NodePanel",
          List(bLoc.attributesLoc, bLoc.raisingCostLoc, bLoc.tawLoc,
            bLoc.houseSpellLoc, bLoc.specialExpLoc))
  } yield new HeroSpellPanel(a)
}

// vim: set ts=2 sw=2 et:
