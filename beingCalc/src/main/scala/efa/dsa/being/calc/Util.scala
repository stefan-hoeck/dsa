package efa.dsa.being.calc

import efa.dsa.being._
import efa.dsa.being.abilities._
import efa.rpg.core._
import scalaz._, Scalaz._

trait UtilFunctions extends HasAbilitiesFunctions {

  def addMod[A:Modified] (a: A, k: ModifierKey, m: Modifier): A =
    Modified[A].modifiersL add (k, m) exec a

  def prop[A:HasModifiers] (a: A, k: ModifierKey): Long = property(a, k)
}

// vim: set ts=2 sw=2 et:
