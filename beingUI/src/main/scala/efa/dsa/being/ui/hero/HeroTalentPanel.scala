package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being.skills._
import efa.dsa.being.ui.{loc, version ⇒ v, NodePanel, BePanel, AttributesPanel}
import efa.dsa.being.ui.skills.{SkillNodes ⇒ SN}
import efa.nb.tc.PersistentComponent
import efa.react.swing.GbPanel
import efa.rpg.being.MVPanel
import org.openide.util.Lookup
import org.openide.util.lookup.ProxyLookup
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroTalentPanel(
  talentP: NodePanel[Skills,HeroData],
  meleeP: NodePanel[Skills,HeroData],
  languageP: NodePanel[Skills,HeroData]
) extends MVPanel[Hero,HeroData]
    with Lookup.Provider
    with PersistentComponent {

  val attributesP = new AttributesPanel[Hero,HeroData]
  val beP = new BePanel[Hero,HeroData]
  val apP = new HeroApPanel

  private def topRight = new GbPanel {apP beside beP add()} 

  attributesP.border = titledBorder(loc.attributes)
  beP.border = titledBorder(bLoc.be)
  apP.border = titledBorder(loc.ap)
  talentP.border = titledBorder(loc.talents)
  meleeP.border = titledBorder(loc.battleTalents)
  languageP.border = titledBorder(loc.languages)

  (attributesP beside topRight) above (
    ((meleeP fillV 1) above (languageP fillV 1)) beside
    (talentP fillV 2)
  ) add()

  def set =
    (talentP.set ∙ ((_: Hero).skills)) ⊹
    (meleeP.set ∙ (_.skills)) ⊹
    (languageP.set ∙ (_.skills)) ⊹
    attributesP.set ⊹
    (lensedV(apP.set)(HeroData.base) ∙ (_.data)) ⊹
    beP.set

  def version = v
  override def prefId = "DSA_HeroTalentPanel"
  def locName = loc.talentsPanel
  override def persistentChildren = Nil //List(talentsP, battleP, languageP)
  override lazy val getLookup =
    new ProxyLookup(talentP.getLookup, meleeP.getLookup,
      languageP.getLookup)
}

object HeroTalentPanel {
  def create: IO[HeroTalentPanel] = for {
    a ← NodePanel(SN.talentsOut,
      List(bLoc.tawLoc, bLoc.attributesLoc, bLoc.ebeLoc,
           bLoc.raisingCostLoc, bLoc.specialExpLoc))
    b ← NodePanel(SN.battleOut,
      List(bLoc.tawLoc, bLoc.atLoc, bLoc.paLoc,
           bLoc.raisingCostLoc, bLoc.specialExpLoc))
    c ← NodePanel(SN.languagesOut,
      List(bLoc.tawLoc, bLoc.raisingCostLoc, bLoc.specialExpLoc))
  } yield new HeroTalentPanel(a,b,c)
}

// vim: set ts=2 sw=2 et:
