package efa.dsa.being.ui.equipment

import dire._, dire.swing._, Swing._, validation.VSIn
import efa.core._, Read.readV
import efa.dsa.being.equipment._
import efa.dsa.being.ui.DescribedPanel
import efa.dsa.equipment._
import efa.dsa.world._
import efa.dsa.world.mittelreich.{Coin, Weight ⇒ MWeight}
import efa.nb.dialog.{DialogEditable ⇒ DE, DEInfo}
import efa.nb.Widgets._
import efa.rpg.core.{UnitEnum, ItemData, DieRoller}
import efa.rpg.items.controller.itemPanel.{unitIn, unitOut}
import scalaz._, Scalaz._, effect.IO

object implicits {
  implicit lazy val AmmunitionE = DE io1 { a: Ammunition ⇒
    for {
      eu ← eqUI(a)
      tp ← TextField trailing a.data.tp.toString
      count ← TextField trailing a.data.count.toString

      elem = eu.elem(el.tp ^^ bl.count, tp ^^ count)
      in   = ^^^(
               eu.eqIn,
               eu.parentIn,
               readIn[DieRoller](tp.in),
               intIn(count.in, Count.validate)
             )(AmmunitionData.apply)
    } yield (elem, in)
  }

  implicit lazy val ArmorE = DE io1 { a: Armor ⇒
    for {
      eu  ← eqUI(a)
      rs  ← TextField trailing a.data.rs.toString
      be  ← TextField trailing a.data.be.toString
      equipped ← CheckBox(selected := a.data.equipped)

      elem = eu.elem(el.rs ^^ el.be ^^ bl.equipped, rs ^^ be ^^ equipped)

      in   = ^^^^(
               eu.eqIn,
               eu.parentIn,
               intIn(rs.in, Rs.validate),
               intIn(be.in, Be.validate),
               valid(equipped.in)
             )(ArmorData.apply)
    } yield (elem, in)
  }

  implicit lazy val ArticleE = DE io1 { a: Article ⇒
    for {
      eu ← eqUI(a)
      count ← TextField trailing a.data.count.toString

      elem = eu.elem(∅[Elem], ∅[Elem])

      in = ^^(
             eu.eqIn,
             eu.parentIn,
             intIn(count.in, Count.validate)
           )(ArticleData.apply)

    } yield (elem, in)
  }
  
  implicit lazy val MeleeE = DE io1 { a: MeleeWeapon ⇒
    for {
      eu         ← eqUI(a)
      ini        ← TextField trailing a.data.ini.toString
      bf         ← TextField trailing a.data.bf.toString
      wm         ← TextField trailing a.data.wm.toString
      tp         ← TextField trailing a.data.tp.toString
      tpkk       ← TextField trailing a.data.tpkk.toString
      talent     ← TextField text a.data.talent

      lbls = el.tp ^^ el.tpkk ^^ el.talent ^^ el.bf ^^ el.wm ^^ el.ini
      comps = tp ^^ tpkk ^^ talent ^^ bf ^^ wm ^^ ini

      in   = Apply[VSIn].apply8(
               eu.eqIn,
               eu.parentIn,
               readIn[DieRoller](tp.in),
               valid(talent.in),
               intIn(bf.in, Bf.validate),
               readIn[TpKk](tpkk.in),
               intIn(ini.in, Ini.validate),
               readIn[Wm](wm.in)
             )(MeleeWeaponData.apply)
    } yield (eu.elem(lbls, comps), in)
  }
  
  implicit lazy val RangedE = DE io1 { a: RangedWeapon ⇒
    for {
      eu         ← eqUI(a)
      tp         ← TextField trailing a.data.tp.toString
      tpkk       ← TextField trailing a.data.tpkk.toString
      ttl        ← TextField trailing a.data.timeToLoad.toString
      talent     ← TextField text a.data.talent
      reach      ← TextField text Reach.shows(a.data.reach)
      tpplus     ← TextField text TpPlus.shows(a.data.tpPlus)

      lbls = el.tp ^^ el.tpkk ^^ el.talent ^^ el.reach ^^
             el.tpPlus ^^ el.timeToLoad

      comps = tp ^^ tpkk ^^ talent ^^ reach ^^ tpplus ^^ ttl

      in   = Apply[VSIn].apply8(
               eu.eqIn,
               eu.parentIn,
               readIn[DieRoller](tp.in),
               valid(talent.in),
               readIn[TpKk](tpkk.in),
               readIn[Reach](reach.in)(readV(Reach.read)),
               readIn[TpPlus](tpplus.in)(readV(TpPlus.read)),
               intIn(ttl.in, Ttl.validate)
             )(RangedWeaponData.apply)
    } yield (eu.elem(lbls, comps), in)
  }

  implicit lazy val ShieldE = DE io1 { a: Shield ⇒
    for {
      eu    ← eqUI(a)
      ini   ← TextField trailing a.data.ini.toString
      bf    ← TextField trailing a.data.bf.toString
      wm    ← TextField trailing a.data.wm.toString

      elem = eu.elem(el.ini ^^ el.bf ^^ el.wm, ini ^^ bf ^^ wm)

      in   = ^^^^(
               eu.eqIn,
               eu.parentIn,
               intIn(ini.in, Ini.validate),
               intIn(bf.in, Bf.validate),
               readIn[Wm](wm.in)
             )(ShieldData.apply)
    } yield (elem, in)
  }

  implicit lazy val ZoneArmorE = DE io1 { a: ZoneArmor ⇒
    for {
      eu  ← eqUI(a)
      rs  ← TextField text ZoneRs.shows(a.data.rs)
      be  ← TextField trailing a.data.be.toString
      equipped ← CheckBox(selected := a.data.equipped)

      elem = eu.elem(el.rs ^^ el.be ^^ bl.equipped, rs ^^ be ^^ equipped)

      in   = ^^^^(
               eu.eqIn,
               eu.parentIn,
               readIn[ZoneRs](rs.in)(readV(ZoneRs.read)),
               intIn(be.in, Be.validate),
               valid(equipped.in)
             )(ZoneArmorData.apply)
    } yield (elem, in)
  }

  private def ul = efa.core.loc
  private def el = efa.dsa.equipment.loc
  protected def bl = efa.dsa.being.loc

  private def eqUI[A,B](
    e: Equipment[A,B],
    pricePrecision: Int = 2,
    baseCoin: Coin = Coin.S,
    weightPrecision: Int = 4,
    baseWeight: MWeight = MWeight.U
  ): IO[EquipmentUI[A,B]] = for {
      price  ← unitOut(baseCoin, pricePrecision, e.price)
      weight ← unitOut(baseWeight, weightPrecision, e.weight)
      dp     ← DescribedPanel(e)
    } yield new EquipmentUI(dp, price, weight,
                            unitIn(baseCoin, price, Price.validate),
                            unitIn(baseWeight, weight, Weight.validate))

  final private class EquipmentUI[A,B](
    val dp: DescribedPanel[Equipment[A,B]],
    price: TextField,
    weight: TextField,
    priceIn: VSIn[Long],
    weightIn: VSIn[Long] 
  ){

    def eqIn: VSIn[EquipmentItemData] = ^^(
      ^^(vsin(dp.a.id), valid(dp.name.in), valid(dp.desc.in))(ItemData.apply),
      priceIn,
      weightIn
    )(EquipmentItemData.apply)

    def elem(lbls: Elem, comps: Elem): Elem = dp size (
      (ul.name ^^ lbls ^^ el.price ^^ el.weight ^^ ul.desc) <>
      (dp.name ^^ comps ^^ price ^^ weight ^^ dp.sp)
    )

    def parentIn: VSIn[Int] = dp.a.parentId.η[VSIn]
  }
}

// vim: set ts=2 sw=2 et:
