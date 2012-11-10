package efa.dsa.being.ui

import efa.dsa.being._
import efa.rpg.being.BeingPanel
import efa.rpg.core.HasModifiers
import scalaz._, Scalaz._

class BePanel[A:HasModifiers,B] extends BeingPanel[A,B] {
  val beC = numberDisabled

  BeKey.loc.shortName beside beC add()

  def set = modifiedProp(BeKey)(beC)
}

// vim: set ts=2 sw=2 et:
