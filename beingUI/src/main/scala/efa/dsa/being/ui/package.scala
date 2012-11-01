package efa.dsa.being

import efa.core.Service.unique
import efa.dsa.being.ui.spi.UILocal

package object ui {
  lazy val loc = unique[UILocal](UILocal)
  val version = "1.0.0-SNAPSHOT"
}

// vim: set ts=2 sw=2 et:
