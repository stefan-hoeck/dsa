package efa.dsa.being.ui.skills

import scalaz.effect.IO

trait LowerCookie {
  def lower: IO[Unit]
}

object LowerCookie {
  def apply(i: IO[Unit]): List[LowerCookie] =
    List(new LowerCookie{def lower = i})
}

// vim: set ts=2 sw=2 et:
