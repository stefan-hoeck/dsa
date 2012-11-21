package efa.dsa.rules

import efa.dsa.being.calc.UtilFunctions
import efa.rpg.core._
import scalaz._, Scalaz._

trait ModRules extends UtilFunctions {

  def mod[A:Modified] (a: A, name: String, v: Option[Int], k: ModifierKey)
    (f: (A,Int) ⇒ Int): A = {
      val modifier = v >>= (x ⇒ notZero(f(a,x)) ∘ (Modifier(name, _)))

      modifier fold (addMod (a, k, _), a)
    }

  def notZero (v: Int): Option[Int] = (v ≟ 0) ? none[Int] | v.some

}

object ModRules extends ModRules

// vim: set ts=2 sw=2 et:
