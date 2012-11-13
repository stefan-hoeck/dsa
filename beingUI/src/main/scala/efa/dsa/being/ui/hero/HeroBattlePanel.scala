package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being._, equipment._
import efa.dsa.being.ui.{loc, version ⇒ v, NodePanel, AttributesPanel}
import efa.dsa.being.ui.equipment.{EquipmentNodes ⇒ EN}
import efa.dsa.equipment.{loc ⇒ eLoc}
import efa.nb.tc.PersistentComponent
import efa.rpg.being.MVPanel
import org.openide.util.Lookup
import org.openide.util.lookup.ProxyLookup
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroBattlePanel (
  armorP: NodePanel[Equipments,EquipmentDatas],
  weaponP: NodePanel[Equipments,HeroData]
) extends MVPanel[Hero,HeroData]
    with Lookup.Provider
    with PersistentComponent {

  armorP.border = titledBorder(loc.armor)
  weaponP.border = titledBorder(loc.weapons)

  (armorP fillV 1) beside (weaponP fillV 1) add()

  def set =
    (mapSt(armorP.set)(HeroData.equipment) ∙ ((_: Hero).equipment)) ⊹
    (weaponP.set ∙ (_.equipment))

  def version = v
  override def prefId = "DSA_HeroBattlePanel"
  def locName = loc.battlePanel
  override def persistentChildren = Nil //List(equipmentP)
  override lazy val getLookup =
    new ProxyLookup(armorP.getLookup, weaponP.getLookup)
}

object HeroBattlePanel {

  def create: IO[HeroBattlePanel] = for {
    a ← NodePanel(EN.armorsOut, List(
      bLoc.rsLoc, bLoc.beLoc, bLoc.equippedLoc))
    b ← NodePanel(EN.weaponsOut, List(eLoc.tpLoc, eLoc.tpkkLoc,
      eLoc.wmLoc, eLoc.bfLoc, eLoc.iniLoc, eLoc.talentLoc,
      bLoc.lhLoc, bLoc.rhLoc))
  } yield new HeroBattlePanel(a,b)

}

// vim: set ts=2 sw=2 et:
