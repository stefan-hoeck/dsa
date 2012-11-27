package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being._, equipment._
import efa.dsa.being.ui.{loc, NodePanel, AttributesPanel}
import efa.dsa.being.ui.equipment.{EquipmentNodes ⇒ EN}
import efa.dsa.being.ui.humanoid._
import efa.dsa.equipment.{loc ⇒ eLoc}
import efa.rpg.being.{MVPanel, BeingPanel}
import org.openide.util.lookup.ProxyLookup
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroBattlePanel (
  attackModeP: NodePanel[Hero,HeroData],
  armorP: NodePanel[Equipments,EquipmentDatas],
  weaponP: NodePanel[Equipments,HumanoidData]
) extends MVPanel[Hero,HeroData] (
  "DSA_HeroBattlePanel", loc.battlePanel, efa.dsa.being.ui.version
) {

  val zoneRsP = new ZoneRsPanel[Hero]
  val valuesP = new BattlePanel[Hero]

  attackModeP.border = titledBorder(loc.attackModes)
  armorP.border = titledBorder(loc.armor)
  weaponP.border = titledBorder(loc.weapons)
  zoneRsP.border = titledBorder(loc.zones)
  valuesP.border = titledBorder(loc.battleValues)

  (valuesP fillH 2) above (zoneRsP fillH 2) above (
    ((attackModeP fillV 1) above (armorP fillV 1)) beside
    (weaponP fillV 2)
  ) add()

  def set =
    (mapSt(armorP.set)(HeroData.humanoid.equipment) ∙ ((_: Hero).equipment)) ⊹
    (mapSt(weaponP.set)(HeroData.humanoid) ∙ (_.equipment)) ⊹
    mapSt(zoneRsP.set)(HeroData.humanoid) ⊹ 
    mapSt(valuesP.set)(HeroData.humanoid) ⊹ 
    attackModeP.set

  override def persistentChildren = List(armorP, weaponP, attackModeP)
  override lazy val getLookup = new ProxyLookup(
    armorP.getLookup, weaponP.getLookup, attackModeP.getLookup)
}

object HeroBattlePanel {

  def create: IO[HeroBattlePanel] = for {
    a ← NodePanel[Hero,HeroData](EN.attackModesOut,
        "DSA_attackModes_NodePanel",
        List(TpKey.loc, AtFkKey.loc, PaKey.loc, AwKey.loc, IniKey.loc))
    b ← NodePanel(EN.armorsOut, "DSA_Armor_NodePanel",
        List(bLoc.rsLoc, bLoc.beLoc, bLoc.equippedLoc))
    c ← NodePanel(EN.weaponsOut, "DSA_Weapons_NodePanel",
        List(eLoc.tpLoc, eLoc.tpkkLoc,
        eLoc.wmLoc, eLoc.bfLoc, eLoc.iniLoc, eLoc.talentLoc,
        bLoc.lhLoc, bLoc.rhLoc))
  } yield new HeroBattlePanel(a,b,c)

}

// vim: set ts=2 sw=2 et:
