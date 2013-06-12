package efa.dsa.equipment

import dire.{SF, SIn}, dire.swing._, Swing._
import efa.core.{Read, Efa}, Efa._
import efa.core.Read.readV
import efa.dsa.equipment._
import efa.dsa.world._
import efa.dsa.world.mittelreich.{Coin, Weight ⇒ MWeight, Distance}
import efa.rpg.core.{UnitEnum, DieRoller}
import efa.rpg.items.ItemPair
import efa.rpg.items.controller.{editable, ItemDataUI, itemPanel}
import scalaz._, Scalaz._, effect.IO

object implicits {
  import itemPanel._

  implicit lazy val AmmunitionEditable = editable[AmmunitionItem] { (s,b) ⇒
    for {
      eu ← eqUI(s, b)
      tp ← TextField trailing item(s).tp.toString

      elem = eu.elem(Elem(el.tp), Elem(tp))
      in   = ^(eu.in, readIn[DieRoller](tp.in))(AmmunitionItem.apply)
    } yield (elem, in)
  }

  implicit lazy val ArmorEditable = editable[ArmorItem] { (s,b) ⇒
    for {
      eu  ← eqUI(s, b)
      rs  ← TextField trailing item(s).rs.toString
      be  ← TextField trailing item(s).be.toString
      add ← CheckBox(selected := item(s).isAddition)

      elem = eu.elem(el.rs ^^ el.be ^^ el.isAddition, rs ^^ be ^^ add)

      in   = ^^^(eu.in,
               intIn(rs.in, Rs.validate),
               intIn(be.in, Be.validate),
               valid(add.in))(ArmorItem.apply)
    } yield (elem, in)
  }

  implicit lazy val ArticleEditable = editable[ArticleItem] { (s,b) ⇒
    eqUI(s, b) map (eu ⇒ (eu.elem(∅[Elem], ∅[Elem]), eu.in))
  }
  
  implicit lazy val MeleeWeaponEditable = editable[MeleeWeaponItem] { (s,b) ⇒
    for {
      eu         ← eqUI(s, b)
      ini        ← TextField trailing item(s).ini.toString
      bf         ← TextField trailing item(s).bf.toString
      wm         ← TextField trailing item(s).wm.toString
      tp         ← TextField trailing item(s).tp.toString
      tpkk       ← TextField trailing item(s).tpkk.toString
      improvised ← CheckBox(selected := item(s).improvised)
      talent     ← TextField text item(s).talent
      twohanded  ← CheckBox(selected := item(s).twoHanded)
      dk         ← ComboBox(DistanceClass.values, item(s).dk)
      length     ← TextField trailing ued.showPretty(Distance.S, 2)(item(s).length)

      lbls = el.tp ^^ el.tpkk ^^ el.improvised ^^ el.talent ^^ el.bf ^^
             el.twoHanded ^^ el.wm ^^ el.ini ^^ el.dk ^^ el.length

      comps = tp ^^ tpkk ^^ improvised ^^ talent ^^ bf ^^
              twohanded ^^ wm ^^ ini ^^ dk ^^ length

      in   = Apply[VSIn].apply11(
               eu.in,
               readIn[DieRoller](tp.in),
               readIn[TpKk](tpkk.in),
               valid(improvised.in),
               valid(talent.in),
               intIn(bf.in, Bf.validate),
               valid(twohanded.in),
               readIn[Wm](wm.in),
               intIn(ini.in, Ini.validate),
               valid(dk.in),
               readIn(length.in, Length.validate)(lengthRead)
             )(MeleeWeaponItem.apply)
    } yield (eu.elem(lbls, comps), in)
  }
  
  implicit lazy val RangedWeaponEditable = editable[RangedWeaponItem] { (s,b) ⇒
    for {
      eu         ← eqUI(s, b)
      tp         ← TextField trailing item(s).tp.toString
      tpkk       ← TextField trailing item(s).tpkk.toString
      ttl        ← TextField trailing item(s).timeToLoad.toString
      improvised ← CheckBox(selected := item(s).improvised)
      talent     ← TextField text item(s).talent
      reach      ← TextField text Reach.shows(item(s).reach)
      tpplus     ← TextField text TpPlus.shows(item(s).tpPlus)
      usesAmmo   ← CheckBox(selected := item(s).usesAmmo)
      makesWound ← CheckBox(selected := item(s).makesWound)

      lbls = el.tp ^^ el.tpkk ^^ el.improvised ^^ el.talent ^^ el.reach ^^
             el.tpPlus ^^ el.makesWound ^^ el.timeToLoad ^^ el.usesAmmo

      comps = tp ^^ tpkk ^^ improvised ^^ talent ^^ reach ^^
              tpplus ^^ makesWound ^^ ttl ^^ usesAmmo

      in   = Apply[VSIn].apply10(
               eu.in,
               readIn[DieRoller](tp.in),
               readIn[TpKk](tpkk.in),
               valid(improvised.in),
               valid(talent.in),
               readIn[Reach](reach.in)(readV(Reach.read)),
               readIn[TpPlus](tpplus.in)(readV(TpPlus.read)),
               valid(makesWound.in),
               intIn(ttl.in, Ttl.validate),
               valid(usesAmmo.in)
             )(RangedWeaponItem.apply)
    } yield (eu.elem(lbls, comps), in)
  }

  implicit lazy val ShieldEditable = editable[ShieldItem] { (s,b) ⇒
    for {
      eu    ← eqUI(s, b)
      size  ← ComboBox(ShieldSize.values, item(s).size)
      sType ← ComboBox(ShieldType.values, item(s).shieldType)
      ini   ← TextField trailing item(s).ini.toString
      bf    ← TextField trailing item(s).bf.toString
      wm    ← TextField trailing item(s).wm.toString

      lbls = el.size ^^ el.shieldType ^^ el.ini ^^ el.bf ^^ el.wm
      comps = size ^^ sType ^^ ini ^^ bf ^^ wm

      in   = ^^^^^(eu.in,
               valid(size.in),
               valid(sType.in),
               intIn(ini.in, Ini.validate),
               intIn(bf.in, Bf.validate),
               readIn[Wm](wm.in))(ShieldItem.apply)
    } yield (eu.elem(lbls, comps), in)
  }

  implicit lazy val ZoneArmorEditable = editable[ZoneArmorItem] { (s,b) ⇒
    for {
      eu  ← eqUI(s, b)
      rs  ← TextField text ZoneRs.shows(item(s).rs)
      be  ← TextField trailing item(s).be.toString
      add ← CheckBox(selected := item(s).isAddition)

      elem = eu.elem(el.rs ^^ el.be ^^ el.isAddition, rs ^^ be ^^ add)

      in   = ^^^(eu.in,
               readIn[ZoneRs](rs.in)(readV(ZoneRs.read)),
               intIn(be.in, Be.validate),
               valid(add.in))(ZoneArmorItem.apply)
    } yield (elem, in)
  }

  private def ul = efa.core.loc
  private def el = efa.dsa.equipment.loc
  private def ued: UnitEnum[Distance] = UnitEnum[Distance]
  private val lengthRead = Read read (ued readPretty Distance.S)

  private def eqUI[A:EquipmentItem](
    p: ItemPair[A],
    b: Boolean,
    pricePrecision: Int = 2,
    baseCoin: Coin = Coin.S,
    weightPrecision: Int = 4,
    baseWeight: MWeight = MWeight.U
  ): IO[EquipmentUI] = {
    def formatP = UnitEnum[Coin] showPretty (baseCoin, pricePrecision)
    def parseP = Read read UnitEnum[Coin].readPretty(baseCoin)
    def formatW = UnitEnum[MWeight] showPretty (baseWeight, weightPrecision)
    def parseW = Read read UnitEnum[MWeight].readPretty(baseWeight)
    def eq: EquipmentItem[A] = implicitly

    for {
      price ← TextField trailing formatP(eq price item(p))
      weight ← TextField trailing formatW(eq weight item(p))
      dw ← dataWidgets(p, b)
    } yield new EquipmentUI(dw, price, weight, parseP, parseW)
  }

  final private class EquipmentUI(
    dw: ItemDataUI,
    price: TextField,
    weight: TextField,
    parseP: Read[Long],
    parseW: Read[Long]
  ) {
    def in: VSIn[EquipmentItemData] =
      ^^(dw.in,
        readIn(price.in, Price.validate)(parseP),
        readIn(weight.in, Weight.validate)(parseW))(EquipmentItemData.apply)

    def elem(lbls: Elem, comps: Elem): Elem =
      (ul.name ^^ lbls ^^ el.price ^^ el.weight ^^ ul.desc) <>
      (dw.name ^^ comps ^^ price ^^ weight ^^ dw.sp)
  }
}

// vim: set ts=2 sw=2 et nowrap:
