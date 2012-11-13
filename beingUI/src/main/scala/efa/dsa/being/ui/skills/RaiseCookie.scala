package efa.dsa.being.ui.skills

import scalaz.effect.IO

trait RaiseCookie {
  def raise: IO[Unit]
}

object RaiseCookie {
  def apply (i: IO[Unit]): List[RaiseCookie] =
    List(new RaiseCookie{def raise = i})
}

// vim: set ts=2 sw=2 et:
