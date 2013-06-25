package efa.dsa.being.ui.hero

import dire.swing._, Swing._
import efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being.ui._, abilities.AbilityNodes
import efa.nb.tc.AsTc
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

final class HeroMainPanel private(
  val abilities: AbilitiesPanel, val panel: Panel)

object HeroMainPanel {
  private val locs = List(cLoc.valueLoc, bLoc.isActiveLoc)

  private val absId = "DSA_abilities_NodePanel"

  private val abilitiesL = HeroData.humanoid.abilities

  def apply(): IO[BeingPanel[Hero,HeroData,HeroMainPanel]] = for {
    abilities ← NodePanel(AbilityNodes.default, locs)
    base      ← HeroBasePanel()
    derived   ← HeroHumanoidPanel[Hero]()
    atts      ← HeroAttributesPanel[Hero]()
    ap        ← HeroApPanel()
    p         ← (base fillH 2) ^^
                ((atts ^^ derived ^^ ap) <> (abilities fillV 3)) panel

    _         ← abilities title loc.abilities
    _         ← base title loc.basePanel
    _         ← derived title loc.derived
    _         ← atts title loc.attributes
    _         ← ap title loc.ap

    sf = atts.sf ⊹ 
         (lensedVSt(base.sf)(HeroData.base) ∙ Hero.data.get) ⊹
         (lensedVSt(ap.sf)(HeroData.base) ∙ Hero.data.get) ⊹
         ((abilities.sf >=> mapSt(abilitiesL)) ∙ Hero.abilities.get) ⊹ 
         derived.sf
  } yield BeingPanel(new HeroMainPanel(abilities, p), sf)

  implicit val Tc: AsTc[HeroMainPanel] = 
    new BeingTc[HeroMainPanel](loc.mainPanel, "DSA_HeroMainPanel", _.panel){
      override def lookup(h: HeroMainPanel) = h.abilities.p.lookup
      override def readProps(h: HeroMainPanel) = readNp(h.abilities, absId)
      override def writeProps(h: HeroMainPanel) = writeNp(h.abilities, absId)
    }
}

// vim: set ts=2 sw=2 et:
