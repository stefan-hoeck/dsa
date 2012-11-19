package efa.dsa.being.calc

import efa.dsa.being._
import efa.dsa.being.abilities._
import efa.rpg.core._

trait UtilFunctions {

  def hasAdvantage[A:HasAbilities] (name: String, a: A): Boolean =
    HasAbilities[A].advantages(a) get name nonEmpty

  def hasFeat[A:HasAbilities] (name: String, a: A): Boolean =
    HasAbilities[A].feats(a) get name nonEmpty

  def hasHandicap[A:HasAbilities] (name: String, a: A): Boolean =
    HasAbilities[A].handicaps(a) get name nonEmpty

  def prop[A:HasModifiers] (a: A, k: ModifierKey): Long = property(a, k)
}

// vim: set ts=2 sw=2 et:
