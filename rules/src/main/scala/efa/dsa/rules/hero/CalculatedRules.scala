package efa.dsa.rules.hero

import efa.core.Localization
import efa.dsa.being._
import efa.dsa.being.{AsHero ⇒ AH}
import efa.dsa.being.calc.UtilFunctions
import efa.dsa.rules.loc
import efa.dsa.world._, Attribute._
import efa.rpg.core.{ModifierKey, Modifier}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules depend on a Hero's attributes and must
 * therefore be cal-
 * culated AFTER all rules that modify a Hero's attributes (especially the
 * miserabel and herausragend rules).
 */
object CalculatedRules extends UtilFunctions {
  def all[A:AH]: DList[Rule[A]] = DList(calcLe, calcAu, calcPa, calcAw,
    calcAt, calcFk, calcMr, calcIni, calcGs, calcWs)

  private def rule[A:AH] (
    l: Localization, modName: String, key: ModifierKey, mod: Attributes ⇒ Int
  ): Rule[A] = Rule.state[A](l.name, 
    for {
      a ← init[A]
      m = Modifier(modName, mod(AH[A].attributes.immutable get a))
      _ ← AH[A].modifiersL add (key, m)
    } yield ()
  )
  
  def calcLe[A:AH] = rule[A](loc.calcLeL, loc.calcLe, LeKey, 
    as ⇒ (as(Ko) + as(Ko) + as(Kk)) roundedDiv 2)
  
  def calcAu[A:AH] = rule[A](loc.calcAuL, loc.calcAu, AuKey, 
    as ⇒ (as(Ge) + as(Ko) + as(Mu)) roundedDiv 2)
  
  def calcPa[A:AH] = rule[A](loc.calcPaL, loc.calcPa, PaKey, 
    as ⇒ (as(Ge) + as(In) + as(Kk)) roundedDiv 5)
  
  def calcAw[A:AH] = rule[A](loc.calcDodgeL, loc.calcDodge, AwKey, 
    as ⇒ (as(Ge) + as(In) + as(Kk)) roundedDiv 5)
  
  def calcAt[A:AH] = rule[A](loc.calcAtL, loc.calcAt, AtKey, 
    as ⇒ (as(Mu) + as(Kk) + as(Ge)) roundedDiv 5)
  
  def calcFk[A:AH] = rule[A](loc.calcFkL, loc.calcFk, FkKey, 
    as ⇒ (as(In) + as(Ff) + as(Kk)) roundedDiv 5)
  
  def calcMr[A:AH]= rule[A](loc.calcMrL, loc.calcMr, MrKey, 
    as ⇒ (as(Mu) + as(Kl) + as(Ko)) roundedDiv 5)
  
  def calcIni[A:AH] = rule[A](loc.calcIniL, loc.calcIni, IniKey, 
    as ⇒ (as(Ge) + as(In) + as(Mu) + as(Mu)) roundedDiv 5)
  
  def calcGs[A:AH] = rule[A](loc.calcGsL, loc.calcGs, GsKey,
    _(Ge) match {
        case x if (x < 11) ⇒ 7  
        case x if (x > 15) ⇒ 9
        case _ ⇒ 8
    })

  def calcWs[A:AH] = rule[A](loc.calcWoundThresholdL,
    loc.calcWoundThreshold, WsKey, _(Ko) roundedDiv 2)
}
//  def all: List[Rule[Hero]] = calcAeRule :: heroAttributed[Hero] :::
//                              heroDerived[Hero]
//
//  def heroAttributed[H <: HeroAttributedBuilder[H]]: List[Rule[H]] =
//    List(calcLeRule[H], calcAuRule[H], calcAtRule[H], calcPaRule[H],
//         calcFkRule[H], calcMrRule[H], calcIniRule[H], calcGsRule[H],
//         calcDodgeRule[H], calcWoundThresholdRule[H], maxBoughtAttributeRule[H])
//  
//  def heroDerived[H <: HeroDerivedBuilder[H]]: List[Rule[H]] =
//    List(maxBoughtAeRule[H], maxBoughtAuRule[H], maxBoughtLeRule[H],
//         maxBoughtMrRule[H])
//
//  val gefass = message("NAME_gefass") //Gefäss der Sterne
//  val sumus = message("NAME_sumus") //Sumus Fülle
//  
//  
//  def calcAeRule: Rule[Hero] = new Rule[Hero] {
//    val id = "calcAeRule"
//    def apply(h: Hero): Hero = {
//      def fromAtts(n: Attributes => Int) = n(h.immutableAtts)
//      
//      if (h.hasAdvantage(AdvantagesRules.vollzauberer) || 
//          h.hasAdvantage(AdvantagesRules.halbzauberer) ||
//          h.hasAdvantage(AdvantagesRules.viertelzauberer)) {
//        if (h.hasFeat(gefass) || h.hasFeat(sumus)) {
//          h.addModifier(keyAe, Modifier(modCalcAeGef, fromAtts(
//                a => (a(Ch) + a(Ch) + a(In) + a(Mu)) roundedDiv 2)))
//        } else {
//          h.addModifier(keyAe, Modifier(modCalcAe, fromAtts(
//                a => (a(Ch) + a(In) + a(Mu)) roundedDiv 2)))
//        }
//      } else h
//    }
//  }
//
//  def maxBoughtAttributeRule[H <: HeroAttributedBuilder[H]]: Rule[H] = new Rule[H] {
//    val id = "maxBoughtAttributeRule"
//    def apply(h: H): H = {
//      def calc(v: Int) = v roundedDiv 2
//      val atts = Attribute.values map (a => (a, calc(h creationAtt a))) toMap
//
//      h maxBoughtAtts_= atts
//    }
//  }
//
//  def maxBoughtLeRule[H <: HeroDerivedBuilder[H]]: Rule[H] = new Rule[H] {
//    val id = "maxBoughtLeRule"
//    def apply(h: H): H = h maxBoughtLe_= (h immutableAtt Ko roundedDiv 2)
//  }
//
//  def maxBoughtAuRule[H <: HeroDerivedBuilder[H]]: Rule[H] = new Rule[H] {
//    val id = "maxBoughtAuRule"
//    def apply(h: H): H = h maxBoughtAu_= (h immutableAtt Ko)
//  }
//
//  def maxBoughtAeRule[H <: HeroDerivedBuilder[H]]: Rule[H] = new Rule[H] {
//    val id = "maxBoughtAeRule"
//    def apply(h: H): H = h maxBoughtAe_= (h immutableAtt Ch)
//  }
//
//  def maxBoughtMrRule[H <: HeroDerivedBuilder[H]]: Rule[H] = new Rule[H] {
//    val id = "maxBoughtMrRule"
//    def apply(h: H): H = h maxBoughtMr_= (h immutableAtt Mu roundedDiv 2)
//  }
//}

// vim: set ts=2 sw=2 et:
