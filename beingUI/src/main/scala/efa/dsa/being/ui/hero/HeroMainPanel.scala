package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData}
import efa.dsa.being.ui.{loc, version ⇒ v}
import efa.nb.PureLookup
import efa.nb.tc.PersistentComponent
import efa.rpg.being.MVPanel
import org.openide.util.Lookup
import org.openide.util.lookup.ProxyLookup
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroMainPanel (pl: PureLookup)
   extends MVPanel[Hero, HeroData] 
   with Lookup.Provider
   with PersistentComponent {

  val basePanel = new HeroBasePanel {border = titledBorder (loc.basePanel)}

  val derivedPanel = new HeroHumanoidPanel {
    border = titledBorder (loc.derived)
  }

  val attributesPanel = new HeroAttributesPanel {
    border = titledBorder (loc.attributes)
  }

  val apPanel = new HeroApPanel {border = titledBorder (loc.ap)}

  basePanel above attributesPanel above derivedPanel above apPanel add()
  
  //(basePanel above 
  // (
  //    (
  //      attributesPanel above derivedPanel above apPanel
  //    ) beside (
  //      advantagesPanel, fillCons()
  //    )
  //  )
  //).add()

  def set = derivedPanel.set ⊹ 
    attributesPanel.set ⊹ 
    (lensedV(basePanel.set)(HeroData.base) ∙ (_.data)) ⊹
    (lensedV(apPanel.set)(HeroData.base) ∙ (_.data))

  def version = v
  def prefId = "DSA_HeroMainPanel"
  def locName = loc.mainPanel
  override def persistentChildren = Nil
  override lazy val getLookup = new ProxyLookup(pl.l)
}

object HeroMainPanel {
  def create: IO[HeroMainPanel] = 
    PureLookup.apply map (new HeroMainPanel(_))
}

// vim: set ts=2 sw=2 et:
