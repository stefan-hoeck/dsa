package efa.dsa.world

import scalaz._, Scalaz._
import org.scalacheck._, Prop._
import efa.rpg.core.specs.LocEnumProps

object RaisingCostTest extends LocEnumProps[RaisingCost] ("RaisingCost") {
 
  import RaisingCost._

  val rc2id = values.zipWithIndex.toMap

  val id2rc = values.zipWithIndex ∘ (_.swap) toMap

  property ("upper") = {
    def next (r: RaisingCost) = id2rc (rc2id (r) + 1)
    def safeNext (r: RaisingCost) = if (r ≟ H) H else next (r)

    values ∀ (r ⇒ safeNext (r) ≟ upper (r))
  }

  property ("lower") = {
    def prior (r: RaisingCost) = id2rc (rc2id (r) - 1)
    def safePrior (r: RaisingCost) = if (r ≟ AStar) AStar else prior (r)

    values ∀ (r ⇒ safePrior (r) ≟ lower (r))
  }
}

// vim: set ts=2 sw=2 et:
