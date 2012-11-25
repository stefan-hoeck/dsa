package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData}
import efa.dsa.being.abilities._
import efa.dsa.being.ui.{loc, version ⇒ v}
import efa.dsa.being.ui.abilities.{AbilitiesPanel, abilitiesPanel}
import efa.rpg.being.MVPanel
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroMainPanel (abilitiesP: AbilitiesPanel)
  extends MVPanel[Hero, HeroData] (
    "DSA_HeroMainPanel", loc.mainPanel, efa.dsa.being.ui.version
  ) {

  val baseP = new HeroBasePanel {border = titledBorder (loc.basePanel)}

  val derivedP = new HeroHumanoidPanel[Hero] {
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

  def set =
    attributesP.set ⊹ 
    (lensedV(baseP.set)(HeroData.base) ∙ (_.data)) ⊹
    (lensedV(apP.set)(HeroData.base) ∙ (_.data)) ⊹
    (mapSt(abilitiesP.set)(HeroData.abilities) ∙ (_.abilities)) ⊹ 
    mapSt(derivedP.set)(HeroData.humanoid)

  override def persistentChildren = List(abilitiesP)
  override lazy val getLookup = abilitiesP.getLookup
}

object HeroMainPanel {
  def create: IO[HeroMainPanel] =  for {
    advP ← abilitiesPanel
    p    ← IO(new HeroMainPanel(advP))
  } yield p
}

// vim: set ts=2 sw=2 et:
