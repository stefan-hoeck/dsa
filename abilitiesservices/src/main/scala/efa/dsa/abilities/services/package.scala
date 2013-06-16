package efa.dsa.abilities.services

import dire.{SF, SIn}, dire.swing._, Swing._
import efa.core.Efa._
import efa.dsa.abilities._
import efa.dsa.world._
import efa.rpg.items.controller.{editable, ItemDataUI, itemPanel}
import scalaz._, Scalaz._, effect.IO

object implicits {
  import itemPanel._

  implicit lazy val AdvantageEditable = editable[AdvantageItem] { (s,b) ⇒
    for {
      dw ← dataWidgets(s, b)
      gp ← TextField trailing dw.item.gp.toString

      elem = advElems(Elem(al.gp), Elem(gp), dw)
      in   = ^(dw.in, intIn(gp.in, Gp.validate))(AdvantageItem.apply)
    } yield (elem, in)
  }

  implicit lazy val FeatEditable = editable[FeatItem] { (s,b) ⇒
    for {
      dw ← dataWidgets(s, b)
      ap ← TextField trailing dw.item.ap.toString

      elem = advElems(Elem(al.ap), Elem(ap), dw)

      in   = ^^(dw.in,
               intIn(ap.in, Ap.validate),
               vsin(dw.item.featType))(FeatItem.apply)
    } yield (elem, in)
  }

  implicit lazy val HandicapEditable = editable[HandicapItem] { (s,b) ⇒
    for {
      dw ← dataWidgets(s, b)
      gp ← TextField trailing dw.item.gp.toString

      elem = advElems(Elem(al.gp), Elem(gp), dw)
      in   = ^(dw.in, readIn[HandicapGp](gp.in))(HandicapItem.apply)
    } yield (elem, in)
  }

  implicit lazy val LanguageEditable = editable[LanguageItem] { (s,b) ⇒
    for {
      dw         ← dataWidgets(s, b)
      rc         ← ComboBox(RaisingCost.values, dw.item.raisingCost)
      complexity ← TextField trailing dw.item.complexity.toString
      family     ← TextField text dw.item.family
      scripture  ← TextField text dw.item.scripture

      lbls = al.raisingCost ^^ al.complexity ^^ al.family ^^ al.scripture
      comps = rc ^^ complexity ^^ family ^^ scripture

      in   = ^^^^(dw.in, 
               valid(rc.in),
               intIn(complexity.in, Complexity.validate),
               valid(scripture.in),
               valid(family.in))(LanguageItem.apply)
    } yield (advElems(lbls, comps, dw), in)
  }

  implicit lazy val MeleeTalentEditable = editable[MeleeTalentItem] { (s,b) ⇒
    for {
      dw         ← dataWidgets(s, b)
      rc         ← ComboBox(RaisingCost.values, dw.item.raisingCost)
      ebe        ← TextField trailing dw.item.ebe.toString
      bt         ← CheckBox(selected := dw.item.isBaseTalent)

      lbls = al.ebe ^^ al.raisingCost ^^ al.baseTalent
      comps = ebe ^^ rc ^^ bt

      in   = ^^^(dw.in, readIn[Ebe](ebe.in), valid(rc.in), valid(bt.in))(
               MeleeTalentItem.apply)
    } yield (advElems(lbls, comps, dw), in)
  }

  implicit lazy val RangedTalentEditable = editable[RangedTalentItem] { (s,b) ⇒
    for {
      dw         ← dataWidgets(s, b)
      rc         ← ComboBox(RaisingCost.values, dw.item.raisingCost)
      ebe        ← TextField trailing dw.item.ebe.toString
      bt         ← CheckBox(selected := dw.item.isBaseTalent)

      lbls = al.ebe ^^ al.raisingCost ^^ al.baseTalent
      comps = ebe ^^ rc ^^ bt

      in   = ^^^(dw.in, readIn[Ebe](ebe.in), valid(rc.in), valid(bt.in))(
               RangedTalentItem.apply)
    } yield (advElems(lbls, comps, dw), in)
  }

  implicit lazy val RitualEditable = editable[RitualItem] { (s,b) ⇒
    for {
      dw         ← dataWidgets(s, b)
      rc         ← ComboBox(RaisingCost.values, dw.item.raisingCost)

      elem = advElems(Elem(al.raisingCost), Elem(rc), dw)
      in   = ^(dw.in, valid(rc.in))(RitualItem.apply)
    } yield (elem, in)
  }

  implicit lazy val ScriptureEditable = editable[ScriptureItem] { (s,b) ⇒
    for {
      dw  ← dataWidgets(s, b)
      rc  ← ComboBox(RaisingCost.values, dw.item.raisingCost)
      co  ← TextField trailing dw.item.complexity.toString

      elem = advElems(al.raisingCost ^^ al.complexity, rc ^^ co, dw)
      in   = ^^(dw.in, valid(rc.in), intIn(co.in, Complexity.validate))(
               ScriptureItem.apply)
    } yield (elem, in)
  }

  implicit lazy val SpellEditable = editable[SpellItem] { (s,b) ⇒
    for {
      dw  ← dataWidgets(s, b)
      rc  ← ComboBox(RaisingCost.values, dw.item.raisingCost)
      p   ← attributes(dw.item.attributes)

      elem = advElems(al.raisingCost ^^ al.attributes, rc ^^ p._1, dw)
      in   = ^^(dw.in, valid(rc.in), p._2)(SpellItem.apply)
    } yield (elem, in)
  }

  implicit lazy val TalentEditable = editable[TalentItem] { (s,b) ⇒
    for {
      dw   ← dataWidgets(s, b)
      rc   ← ComboBox(RaisingCost.values, dw.item.raisingCost)
      ebe  ← TextField trailing dw.item.ebe.toString
      bt   ← CheckBox(selected := dw.item.isBaseTalent)
      tt   ← ComboBox(TalentType.values, dw.item.talentType)
      p    ← attributes(dw.item.attributes)

      lbls = al.raisingCost ^^ al.attributes ^^ al.ebe ^^
             al.talentType ^^ al.baseTalent
      comps = rc ^^ p._1 ^^ ebe ^^ tt ^^ bt

      in   = ^^^^^(dw.in,
               p._2,
               readIn[Ebe](ebe.in),
               valid(rc.in),
               valid(bt.in),
               valid(tt.in))(TalentItem.apply)
    } yield (advElems(lbls, comps, dw), in)
  }

  def attributes(as: Attributes): IO[(Elem, VSIn[Attributes])] = for {
    a ← ComboBox(Attribute.values, as(0))
    b ← ComboBox(Attribute.values, as(1))
    c ← ComboBox(Attribute.values, as(2))
    p ← a <> b <> c panel

    in = valid(^^(a.in, b.in, c.in)(Attributes.apply))
  } yield (p fillV 0, in)

  private def ul = efa.core.loc
  private def al = efa.dsa.abilities.loc

  private def advElems(lbls: Elem, fields: Elem, dw: ItemDataUI[_]): Elem =
    (ul.name ^^ lbls ^^ ul.desc) <> (dw.name ^^ fields ^^ dw.sp)
}

// vim: set ts=2 sw=2 et:
