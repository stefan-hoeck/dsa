package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being.skills._
import efa.dsa.being.ui.{loc, version ⇒ v, NodePanel, AttributesPanel}
import efa.dsa.being.ui.skills.{SkillNodes ⇒ SN}
import efa.nb.tc.PersistentComponent
import efa.rpg.being.MVPanel
import org.openide.util.Lookup
import org.openide.util.lookup.ProxyLookup
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroSpellPanel (spellP: NodePanel[Skills,SkillDatas])
  extends MVPanel[Hero,HeroData]
    with Lookup.Provider
    with PersistentComponent {

  val attributesP = new AttributesPanel[Hero,HeroData]
  val apP = new HeroApPanel

  attributesP.border = titledBorder(loc.attributes)
  apP.border = titledBorder(loc.ap)
  spellP.border = titledBorder(loc.spells)

  (attributesP beside apP) above (spellP fillH 2 fillV 1) add()

  def set =
    (mapSt(spellP.set)(HeroData.skills) ∙ ((_: Hero).skills)) ⊹
    attributesP.set ⊹
    (lensedV(apP.set)(HeroData.base) ∙ (_.data))

  def version = v
  override def prefId = "DSA_HeroSpellPanel"
  def locName = loc.spellsPanel
  override def persistentChildren = Nil //List(spellP)
  override lazy val getLookup = new ProxyLookup(spellP.getLookup)
}

object HeroSpellPanel {
  def create: IO[HeroSpellPanel] = for {
    a ← NodePanel(SN.spellsOut, List(bLoc.attributesLoc,
           bLoc.raisingCostLoc, bLoc.tawLoc, bLoc.houseSpellLoc,
           bLoc.specialExpLoc))
  } yield new HeroSpellPanel(a)
}

// vim: set ts=2 sw=2 et:
