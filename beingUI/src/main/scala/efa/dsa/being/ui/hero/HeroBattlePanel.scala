package efa.dsa.being.ui.hero

import dire.swing._, Swing._
import efa.dsa.being.{loc ⇒ bLoc, _}, equipment._
import efa.dsa.being.ui._
import efa.dsa.being.ui.equipment.{EquipmentNodes ⇒ EN}
import efa.dsa.being.ui.humanoid._
import efa.dsa.equipment.{loc ⇒ eLoc}
import efa.nb.lookup._
import efa.nb.tc.AsTc
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

final class HeroBattlePanel private(
  val attack: NP[Hero,HeroData],
  val armor: NP[Equipments,EquipmentDatas],
  val weapon: NP[Equipments,HumanoidData],
  val panel: Panel
) {
  lazy val lookup = attack.p.lookup ⊹ armor.p.lookup ⊹ weapon.p.lookup
}

object HeroBattlePanel {
  def apply(): IO[BeingPanel[Hero,HeroData,HeroBattlePanel]] = for {
    attack ← NodePanel(EN.attackModesOut, attackLoc)
    armor  ← NodePanel(EN.armorsOut, armorLoc)
    weapon ← NodePanel(EN.weaponsOut, weaponLoc)
    zoneRs ← ZoneRsPanel[Hero]()
    values ← BattlePanel[Hero]()
    p      ← values.fillH(2) ^^ zoneRs.fillH(2) ^^ (
               (attack.fillV(1) ^^ armor.fillV(1)) <> weapon.fillV(2)
             ) panel

    _      ← attack title loc.attackModes
    _      ← armor title loc.armor
    _      ← weapon title loc.weapons
    _      ← zoneRs title loc.zones
    _      ← values title loc.battleValues
    sf = ((armor.sf >=> mapSt(eqL)) ∙ Hero.equipment.get) ⊹
         ((weapon.sf >=> mapSt(humanoidL)) ∙ Hero.equipment.get) ⊹
         (zoneRs.sf >=> mapSt(humanoidL)) ⊹ 
         (values.sf >=> mapSt(humanoidL)) ⊹ 
         attack.sf
  } yield BeingPanel(new HeroBattlePanel(attack, armor, weapon, p), sf)

  private val attackId = "DSA_attackModes_NodePanel"
  private val attackLoc = List(TpKey.loc, AtFkKey.loc, PaKey.loc,
                               AwKey.loc, IniKey.loc)

  private val armorId = "DSA_Armor_NodePanel"
  private val armorLoc = List(bLoc.rsLoc, bLoc.beLoc, bLoc.equippedLoc)

  private val weaponId = "DSA_Armor_NodePanel"
  private val weaponLoc = List(eLoc.tpLoc, eLoc.tpkkLoc, eLoc.wmLoc,
                               eLoc.bfLoc, eLoc.iniLoc, eLoc.talentLoc,
                               bLoc.lhLoc, bLoc.rhLoc)

  private val humanoidL = HeroData.humanoid
  private val eqL = HeroData.humanoid.equipment

  implicit val Tc: AsTc[HeroBattlePanel] = 
    new BeingTc[HeroBattlePanel](loc.battlePanel, "DSA_HeroBattlePanel", _.panel){
      override def lookup(h: HeroBattlePanel) = h.lookup

      override def readProps(h: HeroBattlePanel) = 
        readNp(h.attack, attackId) >>
        readNp(h.armor, armorId) >>
        readNp(h.weapon, weaponId)

      override def writeProps(h: HeroBattlePanel) =
        writeNp(h.attack, attackId) >>
        writeNp(h.armor, armorId) >>
        writeNp(h.weapon, weaponId)
    }
}
//
//

// vim: set ts=2 sw=2 et:
