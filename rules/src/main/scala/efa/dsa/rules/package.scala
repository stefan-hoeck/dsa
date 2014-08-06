package efa.dsa

import efa.core.Service.unique
import efa.dsa.rules.spi.RulesLoc

package object rules {
  lazy val loc = unique[RulesLoc]
} 

// vim: set ts=2 sw=2 et:
