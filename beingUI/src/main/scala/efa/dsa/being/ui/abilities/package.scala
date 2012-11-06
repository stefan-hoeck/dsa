package efa.dsa.being.ui

import efa.dsa.being.abilities._
import efa.nb.dialog.DialogEditable

package object abilities {
  implicit lazy val AdvantageEditable =
    DialogEditable.io((a: Advantage) ⇒ AbilityPanel.create(a))(_.in)

  implicit lazy val HandicapEditable =
    DialogEditable.io((a: Handicap) ⇒ AbilityPanel.create(a))(_.in)

  implicit lazy val FeatEditable =
    DialogEditable.io((a: Feat) ⇒ AbilityPanel.create(a))(_.in)
}

// vim: set ts=2 sw=2 et:
