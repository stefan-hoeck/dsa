package efa.dsa.being.ui.hero

import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being._, equipment._
import efa.dsa.being.ui.{loc, NodePanel, AttributesPanel}
import efa.dsa.being.ui.equipment.{EquipmentNodes ⇒ EN}
import efa.dsa.equipment.{loc ⇒ eLoc}
import efa.rpg.being.{MVPanel, BeingPanel}
import org.openide.util.lookup.ProxyLookup
import javax.swing.BorderFactory.{createTitledBorder ⇒ titledBorder}
import scalaz._, Scalaz._, effect.IO

class HeroBattlePanel (
  attackModeP: NodePanel[Hero,HeroData],
  armorP: NodePanel[Equipments,EquipmentDatas],
  weaponP: NodePanel[Equipments,HeroData]
) extends MVPanel[Hero,HeroData] (
  "DSA_HeroBattlePanel", loc.battlePanel, efa.dsa.being.ui.version
) {

  val zoneRsP = new HeroZoneRsPanel
  val valuesP = new HeroBattleValues

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
    (mapSt(armorP.set)(HeroData.equipment) ∙ ((_: Hero).equipment)) ⊹
    (weaponP.set ∙ (_.equipment)) ⊹
    zoneRsP.set ⊹ 
    attackModeP.set ⊹ 
    valuesP.set

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

class HeroBattleValues extends BeingPanel[Hero,HeroData] {

  val atC = numberDisabled
  val awC = numberDisabled
  val beC = numberDisabled
  val exhaustionC = number
  val fkC = numberDisabled
  val overstrainC = numberDisabled
  val paC = numberDisabled
  val rsC = numberDisabled
  val woundsC = number
  val wsC = numberDisabled

  (AtKey.loc.shortName beside atC beside
    PaKey.loc.shortName beside paC beside
    FkKey.loc.shortName beside fkC beside
    AwKey.loc.shortName beside awC beside
    bLoc.exhaustion beside exhaustionC) above
  (RsKey.loc.shortName beside rsC beside
    BeKey.loc.shortName beside beC beside
    WsKey.loc.shortName beside wsC beside
    bLoc.wounds beside woundsC beside
    bLoc.overstrain beside overstrainC) add()

  lazy val set =
    modifiedProp(AtKey)(atC) ⊹
    modifiedProp(AwKey)(awC) ⊹
    modifiedProp(BeKey)(beC) ⊹
    modifiedProp(FkKey)(fkC) ⊹
    modifiedProp(OverstrainKey)(overstrainC) ⊹
    modifiedProp(PaKey)(paC) ⊹
    modifiedProp(RsKey)(rsC) ⊹
    modifiedProp(WsKey)(wsC) ⊹
    (intIn(exhaustionC, Exhaustion.validate)(hl.exhaustion) ∙ (_.data)) ⊹
    (intIn(woundsC, Wounds.validate)(hl.wounds) ∙ (_.data))

  private val hl = HeroData.humanoid
}

// vim: set ts=2 sw=2 et:
