package efa.dsa.being.ui.hero

import efa.core.Efa._
import efa.dsa.being._, efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.ui.{loc ⇒ uiLoc}
import efa.dsa.world.Attribute
import efa.nb.VSET
import efa.react.sTrans
import efa.react.swing.Swing
import efa.rpg.being.BeingPanel
import scala.swing.{TextField, Button, CheckBox}
import scalaz._, Scalaz._

class HeroAttributesPanel[A:AsHero] extends BeingPanel[A, HeroData] {
  import HeroAttributesPanel._, AsHero._

  val panels: List[Panels] = Attribute.values map (
    (_, numberDisabled, number, number, new Button("..."),
      checkBox(false)))
  
  ("" beside uiLoc.total beside uiLoc.start beside uiLoc.bought beside
    "" beside bLoc.specialExp) above
  (panels foldMap panelElem) add()

  def set = panels foldMap panelSET

  private def panelElem (p: Panels): Elem =
    p._1.loc.locName beside p._2 beside p._3 beside p._4 beside
    p._5 beside p._6

  private def panelSET (p: Panels): VSET[A,HeroData] = {
    val a: Attribute = p._1
    def buttonIn = sTrans.id[A] >|> Swing.clicks(p._5)

    modifiedProp(attributeKeyFor(a))(p._2) ⊹
    (longIn(p._3, valIni)(iniL at a) ∙ heroData[A]) ⊹ 
    outOnly[A](x ⇒ Swing.tooltip(p._4)(maxBought(a)(x).toString)) ⊹
    outOnly[A](x ⇒ Swing.tooltip(p._5)(raiseAttAp(a)(x).toString)) ⊹
    getSet(boughtAtt[A](a))(setBought(a), readVals[Long](p._4)) ⊹
    outOnly(Swing.enabled(p._5) ∙ canRaiseAtt(a)) ⊹
    (checkBox(p._6)(HeroData.specialExp at a) ∙ heroData[A]) ⊹
    buttonIn.uponOut((x,_: Any) ⇒ raiseAtt(a)(x))
  }
}

object HeroAttributesPanel {
  type Panels =
    (Attribute, TextField, TextField, TextField, Button, CheckBox)

  val iniL = HeroData.humanoid.initial
  val boughtL = HeroData.bought

  def valIni = InitialAttributes.validator
  def valBought = BoughtAttributes.validator
}

// vim: set ts=2 sw=2 et:
