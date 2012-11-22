package efa.dsa.rules

import efa.core.Localization
import efa.dsa.being.calc.UtilFunctions
import efa.rpg.core._
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

trait ModRules extends UtilFunctions {

  def modI[A:Modified] (a: A, n: String, v: Option[Int], k: ModifierKey)
    (f: (A,Int) ⇒ Int): A =
      oModAdd(a, n, v map (f(a, _)) getOrElse 0 toLong, k)

  def mod[A:Modified] (a: A, n: String, v: Option[Long], k: ModifierKey)
    (f: (A,Long) ⇒ Long): A =
      oModAdd(a, n, v map (f(a, _)) getOrElse 0L, k)

  def oModAdd[A:Modified] (a: A, n: String, v: Long, k: ModifierKey): A =
    oMod(n, v) fold (addMod (a, k, _), a)

  def oMod (name: String, v: Long): Option[Modifier] =
    notZero(v) ∘ (Modifier(name, _))

  def oModRule[A:Modified] (
    l: Localization, mod: String, k: ModifierKey, f: A ⇒ Long
  ): Rule[A] = Rule(l.name, a ⇒ oModAdd (a, mod, f(a), k))
    

  def notZero (v: Long): Option[Long] = (v ≟ 0L) ? none[Long] | v.some

  def notZero (v: Int): Option[Int] = (v ≟ 0) ? none[Int] | v.some

}

object ModRules extends ModRules

// vim: set ts=2 sw=2 et:
