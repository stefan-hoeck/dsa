package efa.dsa.being.ui

import efa.nb.dialog.DialogPanel
import efa.rpg.core.Described
import scala.swing.GridBagPanel.Fill
import scala.swing.{TextArea, ScrollPane, Label, TextComponent, TextField}
import scalaz.effect.IO

abstract class DescribedPanel[A:Described] (a: A) extends DialogPanel {
  lazy val nameC = textField (Described[A] name a)
  lazy val descC = new TextArea (Described[A] desc a)
  lazy val descPane = new ScrollPane(descC)

  protected def descElem: Single = descPane fillV 1
  protected def descLbl: Single =
    Single (new Label(efa.core.loc.desc), f = Fill.None, wx = 0D)

  protected def elems: Elem

  protected def sizeF: (Int, Int) ⇒ (Int, Int) =
    (w, h) ⇒ (400 max w min 1000, h min 600)

  final def adjust: IO[Unit] =  IO{elems.add(); adjSize (sizeF)}
}

// vim: set ts=2 sw=2 et:
