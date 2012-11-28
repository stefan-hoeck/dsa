package efa.dsa.being

import efa.rpg.core.ModifiedFunctions
import scalaz._, Scalaz._

trait CanAttack[A] {

  def attackModesL: A @> List[AttackMode]
  def attackModes (a: A): List[AttackMode] = attackModesL get a

  def adjustAttackModes (a: A, pf: CanAttack.PfAmSt): A = {
    def adjust (m: AttackMode) = if (pf isDefinedAt m) pf(m) exec m else m

    attackModesL mod (_ map adjust, a)
  }
}

trait CanAttackFunctions extends ModifiedFunctions {
  import efa.dsa.being.{CanAttack ⇒ CA}

  def adjustAttackModes[A:CA] (a: A, pf: CA.PfAmSt): A =
    CA[A] adjustAttackModes (a, pf)

  def attackModes[A:CA] (a: A): List[AttackMode] = CA[A] attackModes a
}

object CanAttack extends CanAttackFunctions {
  type PfAmSt = PartialFunction[AttackMode,State[AttackMode,Unit]]

  def apply[A:CanAttack]: CanAttack[A] = implicitly
}

// vim: set ts=2 sw=2 et:
