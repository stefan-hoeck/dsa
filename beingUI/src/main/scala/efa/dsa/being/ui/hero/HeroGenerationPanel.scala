package efa.dsa.being.ui.hero

import dire.swing._, Swing._
import efa.core.loc.valueLoc
import efa.dsa.being.{Hero, HeroData ⇒ HD, loc ⇒ bLoc}
import efa.dsa.being.ui._
import efa.dsa.being.ui.generation.{GenPanel ⇒ GP}
import efa.nb.lookup._
import efa.nb.tc.AsTc
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

final class HeroGenerationPanel private(
  val r: GP, val c: GP, val p: GP, val panel: Panel
) {
  lazy val lookup = r.np.p.lookup ⊹ c.np.p.lookup ⊹ p.np.p.lookup
}

object HeroGenerationPanel {
  type HGP = HeroGenerationPanel

  private val cultureId = "DSA_cultureGeneration_NodePanel"
  private val professionId = "DSA_professionGeneration_NodePanel"
  private val raceId = "DSA_raceGeneration_NodePanel"

  def apply(): IO[BeingPanel[Hero,HD,HGP]] = for {
    r   ← GP.race
    c   ← GP.culture
    p   ← GP.profession

    pnl ← r.panel.fillV(1) <> c.panel.fillV(1) <> p.panel.fillV(1) panel

    sf  = r.sf ⊹ c.sf ⊹ p.sf
  } yield BeingPanel(new HGP(r, c, p, pnl), sf)

  implicit val Tc: AsTc[HGP] = 
    new BeingTc[HGP](loc.generationPanel, "DSA_HeroGenerationPanel", _.panel){
      override def lookup(h: HGP) = h.lookup

      override def readProps(h: HGP) = 
        readNp(h.r.np, raceId) >>
        readNp(h.c.np, cultureId) >>
        readNp(h.p.np, professionId)

      override def writeProps(h: HGP) =
        writeNp(h.r.np, raceId) >>
        writeNp(h.c.np, cultureId) >>
        writeNp(h.p.np, professionId)
    }
}

// vim: set ts=2 sw=2 et nowrap:
