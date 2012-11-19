package efa.dsa.being.ui.hero

import efa.core.loc.valueLoc
import efa.dsa.being.{Hero, HeroData ⇒ HD, loc ⇒ bLoc}
import efa.dsa.being.ui.{loc, NodePanel, AttributesPanel}
import efa.dsa.being.ui.generation.GenPanel
import efa.react.swing.GbPanel
import efa.rpg.being.{MVPanel, BeingPanel}
import org.openide.util.lookup.ProxyLookup
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroGenerationPanel (rP: GenPanel, cP: GenPanel, pP:GenPanel)
  extends MVPanel[Hero,HD] (
    "DSA_HeroGenerationPanel", loc.generationPanel, efa.dsa.being.ui.version
  ) {

  rP.border = titledBorder(bLoc.race)
  cP.border = titledBorder(bLoc.culture)
  pP.border = titledBorder(bLoc.profession)
  
  rP.fillV(1) beside cP.fillV(1) beside pP.fillV(1) add()

  def set = rP.set ⊹ cP.set ⊹ pP.set

  override def persistentChildren = List(rP.np, cP.np, pP.np)
  override lazy val getLookup =
    new ProxyLookup(rP.getLookup, cP.getLookup, pP.getLookup)
}

object HeroGenerationPanel {

  def create: IO[HeroGenerationPanel] = for {
    a ← GenPanel.race
    b ← GenPanel.culture
    c ← GenPanel.profession
  } yield new HeroGenerationPanel(a,b,c)

}

// vim: set ts=2 sw=2 et:
