package efa.dsa.being

import efa.rpg.core.{Modified, ModifiedFunctions}

trait AsBeing[A] extends Modified[A] with CanAttack[A] {
  def ae (a: A): Long
  def au (a: A): Long
  def exhaustion (a: A): Long
  def ke (a: A): Long
  def le (a: A): Long
  def maxAe (a: A): Long = prop(a, AeKey)
  def maxAu (a: A): Long = prop(a, AuKey)
  def maxKe (a: A): Long = prop(a, KeKey)
  def maxLe (a: A): Long = prop(a, LeKey)
  def mrBody (a: A): Long
  def mrMind (a: A): Long
  def rs (a: A): Long = prop(a, RsKey)
  def wounds (a: A): Long
}

trait AsBeingFunctions extends ModifiedFunctions with CanAttackFunctions {
  import efa.dsa.being.{AsBeing ⇒ AB}

  def ae[A:AB] (a: A): Long = AB[A] ae a
  def au[A:AB] (a: A): Long = AB[A] au a
  def exhaustion[A:AB] (a: A): Long = AB[A] exhaustion a
  def ke[A:AB] (a: A): Long = AB[A] ke a
  def le[A:AB] (a: A): Long = AB[A] le a
  def maxAe[A:AB] (a: A): Long = AB[A] maxAe a
  def maxAu[A:AB] (a: A): Long = AB[A] maxAu a
  def maxKe[A:AB] (a: A): Long = AB[A] maxKe a
  def maxLe[A:AB] (a: A): Long = AB[A] maxLe a
  def mrBody[A:AB] (a: A): Long = AB[A] mrBody a
  def mrMind[A:AB] (a: A): Long = AB[A] mrMind a
  def rs[A:AB] (a: A): Long = AB[A] rs a
  def wounds[A:AB] (a: A): Long = AB[A] wounds a
}

object AsBeing extends AsBeingFunctions {
  def apply[A:AsBeing]: AsBeing[A] = implicitly
}

// vim: set ts=2 sw=2 et:
