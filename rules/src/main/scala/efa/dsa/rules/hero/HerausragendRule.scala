package efa.dsa.rules.hero

import efa.dsa.being._, efa.dsa.being.{AsHero ⇒ AH}
import efa.dsa.rules.{loc, ModRules}
import efa.dsa.world.Attribute
import efa.rpg.core.Modifier
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules modify a Hero's attributes, both
 * via ModifierKeys and through the immutable attributes
 * and attributes during creation. They do not depend
 * on other rules being already executed.
 */
object HerausragendRule extends ModRules {
  def all[A:AH]: DList[Rule[A]] = DList(herausragend, miserabel)

  def herausragend[A:AH]: Rule[A] =
    rule(loc.herausragendeEigenschaftL, loc.herausragendMap)(_.advantageValue)

  def miserabel[A:AH]: Rule[A] =
    rule(loc.miserableEigenschaftL, loc.miserabelMap)(
      ah ⇒ ah handicapValue (_,_) map (-_))

  private def rule[A:AH] (l: efa.core.Localization, m: Map[Attribute,String])
  (value: AH[A] ⇒ (String, A) ⇒ Option[Int]): Rule[A] = {

    def applyAtt (a: A, att: Attribute): A = {
      val name = m(att)

      def mod (v: Int): A = (
        (AH[A].attributes.immutable.at(att) += v) >>
        (AH[A].attributes.creation.at(att) += v) >>
        addModS[A] (attributeKeyFor(att), Modifier(name, v))
      ) exec a

      value(AH[A])(name, a) flatMap notZero map mod getOrElse a
    }

    Rule(l.name, a ⇒ Attribute.values.foldLeft(a)(applyAtt))
  }
}

// vim: set ts=2 sw=2 et:
