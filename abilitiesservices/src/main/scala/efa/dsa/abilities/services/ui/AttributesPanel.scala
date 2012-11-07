package efa.dsa.abilities.services.ui

import scalaz._, Scalaz._
import efa.dsa.abilities.Attributes
import efa.dsa.world.Attribute
import efa.nb.VSIn
import efa.nb.dialog.DialogPanel

class AttributesPanel (a: Attributes) extends DialogPanel {
  def in: VSIn[Attributes] =
    ^^(comboBox(ca), comboBox(cb), comboBox(cc))(Attributes.apply)

  val ca = comboBox (a(0), Attribute.values)
  val cb = comboBox (a(1), Attribute.values)
  val cc = comboBox (a(2), Attribute.values)

  ca beside cb beside cc add()
}

// vim: set ts=2 sw=2 et:
