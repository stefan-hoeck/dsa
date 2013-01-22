package efa.dsa.being.ui.equipment

import efa.core._
import efa.dsa.abilities._
import efa.dsa.being.{loc ⇒ bLoc, HumanoidData ⇒ HD}
import efa.dsa.being._
import efa.dsa.being.equipment._
import efa.dsa.being.calc.EquipmentLinker, EquipmentLinker._
import efa.dsa.being.ui.{Nodes, loc ⇒ uiLoc}
import efa.dsa.equipment._, efa.dsa.equipment.{loc ⇒ eLoc}
import efa.dsa.world.{Be, Rs}
import efa.dsa.world.mittelreich.{Coin, Weight}
import efa.nb.dialog.DialogEditable
import efa.nb.node._
import efa.rpg.core._
import scalaz._, Scalaz._
import scala.swing.Alignment.Trailing

object EquipmentNodes extends StNodeFunctions {
  type EquipmentOut[A] = NodeOut[A,ValSt[EquipmentDatas]]
  type HDOut[A] = NodeOut[A,ValSt[HD]]

  def equipmentOut[A,B](
    implicit L: EquipmentLinker[A,B],
    D: DialogEditable[Equipment[A,B],B]
  ): EquipmentOut[Equipment[A,B]] = 
    destroyEs(L.delete) ⊹
    (editDialog(D) map (L add _ success)) ⊹
    Nodes.described[Equipment[A,B]] ⊹
    Nodes.childActions("ContextActions/DsaEquipmentNode") ⊹
    textW[Equipment[A,B],Long](uiLoc.priceLoc.name, _.fullPrice, price) ⊹
    textW[Equipment[A,B],Long](uiLoc.weightLoc.name, _.fullWeight, weight)

  lazy val attackModeOut: NodeOut[AttackMode,Nothing] =
    name[AttackMode](_.name) ⊹
    attackMods(_.tp, TpKey) ⊹
    attackMods(_.at, AtFkKey) ⊹
    attackMods(_.pa, PaKey) ⊹
    attackMods(_.aw, AwKey) ⊹
    attackMods(_.ini, IniKey)

  lazy val attackModesOut: NodeOut[Hero,Nothing] =
    children(leavesF(attackModeOut)(_.attackModes))

  lazy val ammunitionOut = equipmentOut[AmmunitionItem,AmmunitionData] ⊹
    sg(AmmunitionLinker set AmmunitionData.count)(_.data.count)(
      intRw(bLoc.countLoc.name, Count.validate)) ⊹
    (stringW(eLoc.tpLoc.name) ∙ (_.data.tp.toString))

  lazy val armorOut = equipmentOut[ArmorItem,ArmorData] ⊹
    (intW(bLoc.beLoc.name) ∙ (_.data.be)) ⊹
    (intW(bLoc.rsLoc.name) ∙ (_.data.rs)) ⊹
    sg(ArmorLinker set ArmorData.equipped)(_.data.equipped)(
      booleanRw(bLoc.equippedLoc.name))

  lazy val articleOut = equipmentOut[ArticleItem,ArticleData] ⊹
    sg(ArticleLinker set ArticleData.count)(_.data.count)(
      intRw(bLoc.countLoc.name, Count.validate))

  lazy val meleeOut = equipmentOut[MeleeWeaponItem,MeleeWeaponData] ⊹
    (stringW(eLoc.tpLoc.name) ∙ (_.data.tp.toString)) ⊹
    (stringW(eLoc.tpkkLoc.name) ∙ (_.data.tpkk.toString)) ⊹
    (stringW(eLoc.wmLoc.name) ∙ (_.data.wm.toString)) ⊹
    (intW(eLoc.bfLoc.name) ∙ (_.data.bf)) ⊹
    (intW(eLoc.iniLoc.name) ∙ (_.data.ini)) ⊹
    (stringW(eLoc.talentLoc.name) ∙ (_.data.talent))

  lazy val rangedOut = equipmentOut[RangedWeaponItem,RangedWeaponData] ⊹
    (stringW(eLoc.tpLoc.name) ∙ (_.data.tp.toString)) ⊹
    (stringW(eLoc.tpkkLoc.name) ∙ (_.data.tpkk.toString)) ⊹
    (stringW(eLoc.talentLoc.name) ∙ (_.data.talent))

  lazy val shieldOut = equipmentOut[ShieldItem,ShieldData] ⊹
    (stringW(eLoc.wmLoc.name) ∙ (_.data.wm.toString)) ⊹
    (intW(eLoc.iniLoc.name) ∙ (_.data.ini)) ⊹
    (intW(eLoc.bfLoc.name) ∙ (_.data.bf))

  lazy val zoneArmorOut = equipmentOut[ZoneArmorItem,ZoneArmorData] ⊹
    (intW(bLoc.beLoc.name) ∙ (_.data.be)) ⊹
    (stringW(bLoc.rsLoc.name) ∙ ( ZoneRs shows _.data.rs)) ⊹
    sg(ZoneArmorLinker set ZoneArmorData.equipped)(_.data.equipped)(
      booleanRw(bLoc.equippedLoc.name))

  val armorsOut: EquipmentOut[Equipments] = children(
    singleF (parent(armorOut, eLoc.armors)),
    singleF (parent(zoneArmorOut, eLoc.zoneArmors))
  )

  val weaponsOut: HDOut[Equipments] = children(
    singleF (parentHands(ammunitionOut, eLoc.ammunitions)),
    singleF (parentHands(meleeOut, eLoc.meleeWeapons)),
    singleF (parentHands(rangedOut, eLoc.rangedWeapons)),
    singleF (parentHands(shieldOut, eLoc.shields))
  )

  val allOut: EquipmentOut[Equipments] = children(
    singleF (parent(ammunitionOut, eLoc.ammunitions)),
    singleF (parent(armorOut, eLoc.armors)),
    singleF (parent(articleOut, eLoc.articles)),
    singleF (parent(meleeOut, eLoc.meleeWeapons)),
    singleF (parent(rangedOut, eLoc.rangedWeapons)),
    singleF (parent(shieldOut, eLoc.shields)),
    singleF (parent(zoneArmorOut, eLoc.zoneArmors))
  )

  def hands[A,B](o: EquipmentOut[Equipment[A,B]])
    (implicit L: EquipmentLinker[A,B]): HDOut[Equipment[A,B]] =
    mapSt[Equipment[A,B],EquipmentDatas,HD](o)(HD.equipment) ⊹
    sg(L.equipLeft)(_.leftEquipped)(booleanRw(bLoc.lhLoc.name)) ⊹
    sg(L.equipRight)(_.rightEquipped)(booleanRw(bLoc.rhLoc.name))

  def parent[A,B] (o: EquipmentOut[Equipment[A,B]], n: String)
  (implicit L: EquipmentLinker[A,B], M: Manifest[A]) =
    Nodes.parentNode(n, o)(L.equipmentList)(L.addI)

  def parentHands[A,B] (o: EquipmentOut[Equipment[A,B]], n: String)
  (implicit L: EquipmentLinker[A,B], M: Manifest[A]) =
    Nodes.parentNode(n, hands(o))(L.equipmentList)(L.addIHD)

  private def price[A,B](e: Equipment[A,B]): String =
    UnitEnum[Coin] showPretty (Coin.S, 2) apply e.fullPrice

  private def weight[A,B](e: Equipment[A,B]): String =
    UnitEnum[Weight] showPretty (Weight.U, 4) apply e.fullWeight

  private def attackMods[A:Manifest](
    get: AttackMode ⇒ A,
    k: ModifierKey
  ): NodeOut[AttackMode,Nothing] =
    textW(k.loc.name, get, get(_).toString,
    prettyModsKeyO[AttackMode](k), Trailing)
}

// vim: set ts=2 sw=2 et:
