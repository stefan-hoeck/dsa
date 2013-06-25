package efa.dsa.being.ui.hero

import dire.swing._, Swing._
import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being.skills._
import efa.dsa.being.ui._
import efa.dsa.being.ui.skills.{SkillNodes ⇒ SN}
import efa.nb.lookup._
import efa.nb.tc.AsTc
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

final class HeroTalentPanel private(
  val talent: NP[Skills,HeroData],
  val melee: NP[Skills,HeroData],
  val language: NP[Skills,HeroData],
  val panel: Panel
) {
  lazy val lookup = talent.p.lookup ⊹ melee.p.lookup ⊹ language.p.lookup
}

object HeroTalentPanel {
  def apply(): IO[BeingPanel[Hero,HeroData,HeroTalentPanel]] = for {
    talent     ← NodePanel(SN.talentsOut, talentLoc)
    melee      ← NodePanel(SN.battleOut, battleLoc)
    language   ← NodePanel(SN.languagesOut, langLoc)
    attributes ← AttributesPanel[Hero,HeroData]()
    be         ← BePanel[Hero,HeroData]()
    ap         ← HeroApPanel()

    _          ← attributes title loc.attributes
    _          ← be title bLoc.be
    _          ← ap title loc.ap
    _          ← talent title loc.talents
    _          ← melee title loc.battleTalents
    _          ← language title loc.languages

    topRight   ← (ap beside be).panel
    center     = (melee.fillV(1) ^^ language.fillV(1)) <> talent.fillV(2)
    p          ← (attributes <> topRight) ^^ center panel

    sf         = (talent.sf ∙ Hero.skills.get) ⊹
                 (melee.sf ∙ Hero.skills.get) ⊹
                 (language.sf ∙ Hero.skills.get) ⊹
                 attributes.sf ⊹
                 be.sf ⊹
                 (lensedVSt(ap.sf)(HeroData.base) ∙ Hero.data.get)
  } yield BeingPanel(new HeroTalentPanel(talent, melee, language, p), sf)

  private val talentId = "DSA_Talents_NodePanel"
  private val talentLoc = List(bLoc.tawLoc, bLoc.attributesLoc, bLoc.ebeLoc,
                               bLoc.raisingCostLoc, bLoc.specialExpLoc)
  private val battleId = "DSA_BattleTalents_NodePanel"
  private val battleLoc = List(bLoc.tawLoc, bLoc.atLoc, bLoc.paLoc,
                               bLoc.raisingCostLoc, bLoc.specialExpLoc)

  private val langId = "DSA_Languages_NodePanel"
  private val langLoc = List(bLoc.tawLoc, bLoc.raisingCostLoc, bLoc.specialExpLoc)

  private val skills = (_: Hero).skills

  implicit val Tc: AsTc[HeroTalentPanel] = 
    new BeingTc[HeroTalentPanel](loc.talentsPanel, "DSA_HeroTalentPanel", _.panel){
      override def lookup(h: HeroTalentPanel) = h.lookup

      override def readProps(h: HeroTalentPanel) = 
        readNp(h.talent, talentId) >>
        readNp(h.melee, battleId) >>
        readNp(h.language, langId)

      override def writeProps(h: HeroTalentPanel) =
        writeNp(h.talent, talentId) >>
        writeNp(h.melee, battleId) >>
        writeNp(h.language, langId)
    }
}

// vim: set ts=2 sw=2 et:
