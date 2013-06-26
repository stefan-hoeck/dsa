package efa.dsa.being.ui.hero

import dire._, dire.swing._, Swing._
import efa.core.Efa._
import efa.dsa.being._, efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.ui.{loc ⇒ uiLoc}
import efa.dsa.world.Attribute
import efa.nb.VStSF
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

object HeroAttributesPanel {
  import AsHero._

  type Panels = (Attribute, TextField, TextField, TextField, Button, CheckBox)

  private val iniL = HeroData.humanoid.initial
  private val boughtL = HeroData.bought

  def apply[A:AsHero](): IO[BeingPanel[A,HeroData,Panel]] = {
    def sf(p: Panels): VStSF[A,HeroData] = {
      val (a, tot, ini, bght, button, chb) = p

      (longSf(ini.sf, valIni)(iniL at a) ∙ heroData[A]) ⊹ 
      getSet(boughtAtt[A](a))(setBought(a), readShow[Long](bght.sf)) ⊹
      (lensed(chb.sf)(HeroData.specialExp at a) ∙ heroData[A]) ⊹
      (SF.id[A] on button.clicks.sf map raiseAtt(a)) ⊹
      modifiedProp(attributeKeyFor(a), tot) ⊹
      outOnly(bght.tooltip ∙ (maxBought(a)(_).toString.some)) ⊹
      outOnly(button.tooltip ∙ (raiseAttAp(a)(_).toString.some)) ⊹
      outOnly(button.enabled ∙ canRaiseAtt(a))
    }

    for {
      se ← Label(text := bLoc.shortSpecialExp, tooltip := bLoc.specialExp.some)
      ps ← Attribute.values traverse panel
      p  ← Panel(border := Border.title(uiLoc.attributes))
      _  ← ("" <> uiLoc.total <> uiLoc.start <> uiLoc.bought <> "" <> se) ^^
           (ps foldMap elem) addTo p
    } yield BeingPanel(p, ps foldMap sf)
  }

  private def panel(a: Attribute) = ^^^^(disabledNumeric, numeric, numeric,
    Button(text := "↑"), CheckBox(selected := false))((a,_,_,_,_,_))

  private def elem(p: Panels) = p._1.loc.locName <> p._2 <> p._3 <> p._4 <>
    (p._5 fillH 0) <> (p._6 fillH 0)

  private def valIni = InitialAttributes.validator
  private def valBought = BoughtAttributes.validator
}

// vim: set ts=2 sw=2 et:
