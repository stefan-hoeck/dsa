package efa.dsa.being.ui

import efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.abilities._
import efa.nb.dialog.DialogEditable
import scalaz.effect.IO

package object abilities {
  type AbilitiesPanel = NodePanel[Abilities,AbilityDatas]

  def abilitiesPanel: IO[AbilitiesPanel] =
    NodePanel(AbilityNodes.default,
      List(cLoc.valueLoc, bLoc.isActiveLoc))

  implicit lazy val AdvantageEditable =
    DialogEditable.io((a: Advantage) ⇒ AbilityPanel.create(a))(_.in)

  implicit lazy val HandicapEditable =
    DialogEditable.io((a: Handicap) ⇒ AbilityPanel.create(a))(_.in)

  implicit lazy val FeatEditable =
    DialogEditable.io((a: Feat) ⇒ AbilityPanel.create(a))(_.in)
}

// vim: set ts=2 sw=2 et:
