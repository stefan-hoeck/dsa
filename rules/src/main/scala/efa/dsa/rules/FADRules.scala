package efa.dsa.rules

import efa.core.Localization
import efa.dsa.being.abilities.{HasAbilities ⇒ HA}
import efa.dsa.being.calc.UtilFunctions
import efa.rpg.core._, efa.rpg.core.{Modified ⇒ M}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

trait FADRules extends ModRules {

  def advantageRule[A:HA:M](
    l: Localization,
    name: String,
    f: (A,Int) ⇒ Int,
    k: ModifierKey
  ): Rule[A] = Rule(l.name, advantageMod (_, name, k)(f))

  def featRule[A:HA:M](
    l: Localization,
    name: String,
    f: A ⇒ Long,
    k: ModifierKey
  ): Rule[A] = Rule(l.name, featMod (_, name, k)(f))

  def handicapRule[A:HA:M](
    l: Localization,
    name: String,
    f: (A,Int) ⇒ Int,
    k: ModifierKey
  ): Rule[A] = Rule(l.name, handicapMod (_, name, k)(f))

  def advantageMod[A:HA:M] (a: A, name: String, k: ModifierKey)
    (f: (A,Int) ⇒ Int): A = 
    modI (a, name, HA[A].advantageValue(name, a), k)(f)

  def featMod[A:HA:M] (a: A, name: String, k: ModifierKey)
    (f: A ⇒ Long): A = 
    mod (a, name, HA[A].activeFeat(name, a) as 0L, k)((a,_) ⇒ f(a))

  def handicapMod[A:HA:M] (a: A, name: String, k: ModifierKey)
    (f: (A,Int) ⇒ Int): A = 
    modI (a, name, HA[A].advantageValue(name, a), k)(f)

}

object FADRules extends FADRules

// vim: set ts=2 sw=2 et:
