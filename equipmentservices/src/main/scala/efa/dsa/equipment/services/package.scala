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
      tp ← TextField trailing eu.item.tp.toString

      elem = eu.elem(Elem(el.tp), Elem(tp))
      in   = ^(eu.in, readIn[DieRoller](tp.in))(AmmunitionItem.apply)
    } yield (elem, in)
  }

  implicit lazy val ArmorEditable = editable[ArmorItem] { (s,b) ⇒
    for {
      eu  ← eqUI(s, b)
      rs  ← TextField trailing eu.item.rs.toString
      be  ← TextField trailing eu.item.be.toString
      add ← CheckBox(selected := eu.item.isAddition)

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
      ini        ← TextField trailing eu.item.ini.toString
      bf         ← TextField trailing eu.item.bf.toString
      wm         ← TextField trailing eu.item.wm.toString
      tp         ← TextField trailing eu.item.tp.toString
      tpkk       ← TextField trailing eu.item.tpkk.toString
      improvised ← CheckBox(selected := eu.item.improvised)
      talent     ← TextField text eu.item.talent
      twohanded  ← CheckBox(selected := eu.item.twoHanded)
      dk         ← ComboBox(DistanceClass.values, eu.item.dk)
      length     ← TextField trailing ued.showPretty(Distance.S, 2)(eu.item.length)

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
      tp         ← TextField trailing eu.item.tp.toString
      tpkk       ← TextField trailing eu.item.tpkk.toString
      ttl        ← TextField trailing eu.item.timeToLoad.toString
      improvised ← CheckBox(selected := eu.item.improvised)
      talent     ← TextField text eu.item.talent
      reach      ← TextField text Reach.shows(eu.item.reach)
      tpplus     ← TextField text TpPlus.shows(eu.item.tpPlus)
      usesAmmo   ← CheckBox(selected := eu.item.usesAmmo)
      makesWound ← CheckBox(selected := eu.item.makesWound)

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
      size  ← ComboBox(ShieldSize.values, eu.item.size)
      sType ← ComboBox(ShieldType.values, eu.item.shieldType)
      ini   ← TextField trailing eu.item.ini.toString
      bf    ← TextField trailing eu.item.bf.toString
      wm    ← TextField trailing eu.item.wm.toString

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
      rs  ← TextField text ZoneRs.shows(eu.item.rs)
      be  ← TextField trailing eu.item.be.toString
      add ← CheckBox(selected := eu.item.isAddition)

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
  ): IO[EquipmentUI[A]] = {
    def eq: EquipmentItem[A] = implicitly

    for {
      price ← unitOut(baseCoin, pricePrecision, eq price item(p))
      weight ← unitOut(baseWeight, weightPrecision, eq weight item(p))
      dw ← dataWidgets(p, b)
    } yield new EquipmentUI(dw, price, weight,
                            unitIn(baseCoin, price, Price.validate),
                            unitIn(baseWeight, weight, Weight.validate))
  }

  final private class EquipmentUI[A](
    dw: ItemDataUI[A],
    price: TextField,
    weight: TextField,
    priceIn: VSIn[Long],
    weightIn: VSIn[Long] 
  )(implicit A: EquipmentItem[A]) {
    def item: A = dw.item

    def in: VSIn[EquipmentItemData] =
      ^^(dw.in, priceIn, weightIn)(EquipmentItemData.apply)

    def elem(lbls: Elem, comps: Elem): Elem =
      (ul.name ^^ lbls ^^ el.price ^^ el.weight ^^ ul.desc) <>
      (dw.name ^^ comps ^^ price ^^ weight ^^ dw.sp)
  }
}

// vim: set ts=2 sw=2 et nowrap:
