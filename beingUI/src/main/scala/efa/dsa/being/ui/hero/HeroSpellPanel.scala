package efa.dsa.being.ui.hero

import dire.swing._, Swing._
import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc, HeroBaseData ⇒ HBD}
import efa.dsa.being.ui._
import efa.dsa.being.ui.skills.SkillNodes
import efa.nb.tc.{AsTc, OutlineTc}
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

final class HeroSpellPanel private(
  val spells: SpellsPanel, val panel: Panel)

object HeroSpellPanel {
  val locs = List(bLoc.attributesLoc, bLoc.raisingCostLoc, bLoc.tawLoc,
                  bLoc.houseSpellLoc, bLoc.specialExpLoc)

  private val spellsId = "DSA_Spells_NodePanel"

  def apply(): IO[BeingPanel[Hero,HeroData,HeroSpellPanel]] = for {
    spells ← NodePanel(SkillNodes.spellsOut, locs)
    atts   ← AttributesPanel[Hero,HeroData]()
    ap     ← HeroApPanel()
    p      ← (atts <> ap) ^^ (spells fillH 2 fillV 1) panel

    _      ← spells title loc.spells
    _      ← atts title loc.attributes
    _      ← ap title loc.ap

    sf     = (spells.sf ∙ ((_: Hero).skills)) ⊹
             atts.sf ⊹
             (lensedVSt(ap.sf)(HeroData.base) ∙ (_.data))
  } yield BeingPanel(new HeroSpellPanel(spells, p), sf)

  implicit val Tc: AsTc[HeroSpellPanel] = 
    new BeingTc[HeroSpellPanel](loc.spellsPanel, "DSA_HeroSpellPanel", _.panel){
      override def lookup(h: HeroSpellPanel) = h.spells.p.lookup
      override def readProps(h: HeroSpellPanel) = readNp(h.spells, spellsId)
      override def writeProps(h: HeroSpellPanel) = writeNp(h.spells, spellsId)
    }
}

// vim: set ts=2 sw=2 et:
