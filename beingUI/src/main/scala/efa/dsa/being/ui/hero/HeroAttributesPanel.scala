package efa.dsa.being.ui.hero

import efa.core.Efa._
import efa.dsa.being._
import efa.dsa.being.ui.{loc ⇒ uiLoc}
import efa.dsa.world.Attribute
import efa.react.swing.component.{tooltip ⇒ tt}
import efa.rpg.being.BeingPanel
import scala.swing.TextField
import scalaz._, Scalaz._

class HeroAttributesPanel extends BeingPanel[Hero, HeroData] {
  import HeroAttributesPanel._

  val panels: List[Panels] = Attribute.values map (
    (_, numberDisabled, number, number))
  
  ("" beside uiLoc.total beside uiLoc.start beside uiLoc.bought) above
  (panels foldMap panelElem) add()

  def set = panels foldMap panelSET

  private def panelElem (p: Panels): Elem =
    p._1.loc.locName beside p._2 beside p._3 beside p._4

  private def panelSET (p: Panels): VSET[Hero,HeroData] = {
    val a: Attribute = p._1

    modifiedProp(attributeKeyFor(a))(p._2) ⊹
    (intIn(p._3, valIni)(lens.initial at a) ∙ (_.data)) ⊹ 
    outOnly[Hero](h ⇒ tt(p._4)(h.attributes.maxBought(a).toString)) ⊹
    getSet((_: Hero).data.attributes.bought(a))(
      _.setBoughtAttribute(a, _), readVals[Int](p._4))
  }
}

object HeroAttributesPanel {
  type Panels = (Attribute, TextField, TextField, TextField)

  val lens = HeroData.attributes

  def valIni = InitialAttributes.validator
  def valBought = BoughtAttributes.validator
}

// vim: set ts=2 sw=2 et:
