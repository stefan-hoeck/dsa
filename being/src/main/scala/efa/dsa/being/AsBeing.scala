package efa.dsa.being

import efa.rpg.core.{Modified, ModifiedFunctions}

trait AsBeing[A] extends Modified[A] {
  def au (a: A): Int
  def exhaustion (a: A): Long
  def le (a: A): Int
  def maxAu (a: A): Long = prop(a, AuKey)
  def maxLe (a: A): Long = prop(a, LeKey)
}

trait AsBeingFunctions extends ModifiedFunctions {
  import efa.dsa.being.{AsBeing ⇒ AB}

  def au[A:AB] (a: A): Int = AB[A] au a
  def exhaustion[A:AB] (a: A): Long = AB[A] exhaustion a
  def le[A:AB] (a: A): Int = AB[A] le a
  def maxAu[A:AB] (a: A): Long = AB[A] maxAu a
  def maxLe[A:AB] (a: A): Long = AB[A] maxLe a
}

object AsBeing {
  def apply[A:AsBeing]: AsBeing[A] = implicitly
}

// vim: set ts=2 sw=2 et:
