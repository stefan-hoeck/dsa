package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData}
import efa.dsa.being.abilities._
import efa.dsa.being.ui.{loc, version ⇒ v}
import efa.dsa.being.ui.abilities.{AbilitiesPanel, abilitiesPanel}
import efa.nb.PureLookup
import efa.nb.tc.PersistentComponent
import efa.rpg.being.MVPanel
import org.openide.util.Lookup
import org.openide.util.lookup.ProxyLookup
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroMainPanel (pl: PureLookup, abilitiesP: AbilitiesPanel)
   extends MVPanel[Hero, HeroData] 
   with Lookup.Provider
   with PersistentComponent {

  val baseP = new HeroBasePanel {border = titledBorder (loc.basePanel)}

  val derivedP = new HeroHumanoidPanel {
    border = titledBorder (loc.derived)
  }

  val attributesP = new HeroAttributesPanel {
    border = titledBorder (loc.attributes)
  }

  val apP = new HeroApPanel {border = titledBorder (loc.ap)}

  abilitiesP.border = titledBorder (loc.abilities)

  (baseP fillH 2) above (
    (attributesP above derivedP above apP) beside (abilitiesP fillV 3)
  ) add()

  def set = derivedP.set ⊹ 
    attributesP.set ⊹ 
    (lensedV(baseP.set)(HeroData.base) ∙ (_.data)) ⊹
    (lensedV(apP.set)(HeroData.base) ∙ (_.data)) ⊹
    (mapSt(abilitiesP.set)(HeroData.abilities) ∙ (_.abilities))

  def version = v
  def prefId = "DSA_HeroMainPanel"
  def locName = loc.mainPanel
  override def persistentChildren = Nil
  override lazy val getLookup = new ProxyLookup(pl.l)
}

object HeroMainPanel {
  def create: IO[HeroMainPanel] =  for {
    l    ← PureLookup.apply
    advP ← abilitiesPanel
  } yield new HeroMainPanel(l, advP)
}

// vim: set ts=2 sw=2 et:
