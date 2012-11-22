package efa.dsa.rules.humanoid

import efa.dsa.rules.{loc ⇒ Loc, FADRules}
import efa.dsa.being._
import efa.dsa.world._
import efa.rpg.core._, efa.rpg.core.{Modified ⇒ M}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules depend on KO only
 * (they modify KO as well and must therefore be
 * the LAST rules to modify KO). They modify KO, BE, AU.
 */
object ExhaustionRules extends FADRules {
  private val KoKey = attributeKeyFor(Attribute.Ko)
  private def ovKey = OverstrainKey
  private def ex= efa.dsa.being.loc.exhaustion
  private def ov= ovKey.loc.locName

  def all[A:AsBeing:M]: DList[Rule[A]] = DList(
    overstrain, overstrainAu, overstrainBe, overstrainKo
  )

  def overstrain[A:AsBeing]: Rule[A] = oModRule[A](
    Loc.overstrainFromExhaustionL, ex, OverstrainKey,
    a ⇒ (exhaustion(a) - prop(a, KoKey)) max 0
  )
  
  def overstrainAu[A:M]: Rule[A] = oModRule[A] (
    Loc.overstrainAuL, ov, AuKey, - prop(_, ovKey) * 2L)
  
  def overstrainBe[A:M]: Rule[A] = oModRule[A] (
    Loc.overstrainBeL, ov, BeKey, prop(_, ovKey))
  
  def overstrainKo[A:M]: Rule[A] = oModRule[A] (
    Loc.overstrainKoL, ov, KoKey, - prop(_, ovKey))
}

// vim: set ts=2 sw=2 et:
