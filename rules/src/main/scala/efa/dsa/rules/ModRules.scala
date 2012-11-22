package efa.dsa.rules

import efa.core.Localization
import efa.rpg.core._
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

trait ModRules
  extends ModifiedFunctions 
  with efa.dsa.being.abilities.HasAbilitiesFunctions
  with efa.dsa.being.equipment.HasEquipmentFunctions 
  with efa.dsa.being.AsBeingFunctions {

  def oModRule[A:Modified] (
    l: Localization, mod: String, k: ModifierKey, f: A ⇒ Long
  ): Rule[A] = Rule(l.name, a ⇒ oModAdd (a, mod, f(a), k))

}

object ModRules extends ModRules

// vim: set ts=2 sw=2 et:
