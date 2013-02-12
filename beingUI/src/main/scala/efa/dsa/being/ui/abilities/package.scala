package efa.dsa.being.ui

import efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.abilities._
import efa.nb.dialog.{DialogEditable ⇒ DE}
import scalaz.effect.IO

package object abilities {
  type AbilitiesPanel = NodePanel[Abilities,AbilityDatas]

  def abilitiesPanel: IO[AbilitiesPanel] =
    NodePanel(AbilityNodes.default, "DSA_abilities_NodePanel",
      List(cLoc.valueLoc, bLoc.isActiveLoc))

  implicit lazy val AdvantageE =
    DE.io1{ a: Advantage ⇒ AbilityPanel.create(a)}(_.in)

  implicit lazy val DisadvantageE =
    DE.io1{ a: Handicap ⇒ AbilityPanel.create(a)}(_.in)

  implicit lazy val FeatE =
    DE.io1{ a: Feat ⇒ AbilityPanel.create(a) }(_.in)
}

// vim: set ts=2 sw=2 et:
