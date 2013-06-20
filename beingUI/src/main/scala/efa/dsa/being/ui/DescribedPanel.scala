package efa.dsa.being.ui

import dire._, dire.swing._, Swing._
import efa.rpg.core.Described
import scalaz.effect.IO

final class DescribedPanel[A:Described](
    val a: A,
    val name: TextField,
    val desc: TextArea,
    val sp: ScrollPane) {

  def size(e: Elem,
           wMin: Int = 400,
           wMax: Int = 1000,
           hMin: Int = 600): Elem = 
    e adjustSize { case (w, h) ⇒ (wMin max w min wMax, h min hMin) }
}

object DescribedPanel {
  def apply[A](a: A)(implicit A: Described[A]): IO[DescribedPanel[A]] =
    for {
      name ← TextField text A.name(a)
      desc ← TextArea(text := A.desc(a))
      sp   ← ScrollPane(desc)
    } yield new DescribedPanel(a, name, desc, sp)
}

// vim: set ts=2 sw=2 et:
