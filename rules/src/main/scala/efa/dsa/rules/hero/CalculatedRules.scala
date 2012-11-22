package efa.dsa.rules.hero

import efa.core.Localization
import efa.dsa.being._
import efa.dsa.being.{AsHero ⇒ AH}
import efa.dsa.rules.{loc, ModRules}
import efa.dsa.world._, Attribute._
import efa.rpg.core.{ModifierKey, Modifier}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules depend on a Hero's attributes and must
 * therefore be calculated AFTER all rules that modify
 * a Hero's attributes (especially the
 * miserabel and herausragend rules).
 */
object CalculatedRules extends ModRules {
  def all[A:AH]: DList[Rule[A]] = DList(calcAe, calcLe, calcAu, calcPa,
    calcAw, calcAt, calcFk, calcMr, calcIni, calcGs, calcWs,
    maxBoughtAe, maxBoughtAttribute, maxBoughtAu, maxBoughtLe,
    maxBoughtMr)

  private def immutable[A:AH](a: A) = AH[A].attributes.immutable get a

  private def rule[A:AH] (
    l: Localization, name: String, k: ModifierKey, v: Attributes ⇒ Int
  ): Rule[A] = {
    def m (a: A) = Modifier(name, v(immutable(a)))

    Rule(l.name, a ⇒ addMod(a, k, m(a)))
  }
  
  def calcAe[A:AH]: Rule[A] = {
    def calc(a: A): A = {
      def fromAtts(f: Attributes ⇒ Int) = f (immutable(a))
      def isMagician =
          hasAdvantage(loc.vollzauberer, a) || 
          hasAdvantage(loc.halbzauberer, a) ||
          hasAdvantage(loc.viertelzauberer, a)
      
      def hasGefass = hasFeat(loc.gefass, a) || hasFeat(loc.sumus, a)

      if (isMagician) {
        if (hasGefass) addMod(a, AeKey, Modifier(loc.calcAeGef, fromAtts(
          a ⇒ (a(Ch) + a(Ch) + a(In) + a(Mu)) rdiv 2)))
        else addMod(a, AeKey, Modifier(loc.calcAe, fromAtts(
          a ⇒ (a(Ch) + a(In) + a(Mu)) rdiv 2)))
      } else a
    }
     
    Rule(loc.calcAeL.name, calc)
  }
  
  def calcAt[A:AH] = rule[A](loc.calcAtL, loc.calcAt, AtKey, 
    as ⇒ (as(Mu) + as(Kk) + as(Ge)) rdiv 5)
  
  def calcAu[A:AH] = rule[A](loc.calcAuL, loc.calcAu, AuKey, 
    as ⇒ (as(Ge) + as(Ko) + as(Mu)) rdiv 2)
  
  def calcAw[A:AH] = rule[A](loc.calcDodgeL, loc.calcDodge, AwKey, 
    as ⇒ (as(Ge) + as(In) + as(Kk)) rdiv 5)
  
  def calcFk[A:AH] = rule[A](loc.calcFkL, loc.calcFk, FkKey, 
    as ⇒ (as(In) + as(Ff) + as(Kk)) rdiv 5)
  
  def calcGs[A:AH] = rule[A](loc.calcGsL, loc.calcGs, GsKey,
    _(Ge) match {
        case x if (x < 11) ⇒ 7  
        case x if (x > 15) ⇒ 9
        case _ ⇒ 8
    })
  
  def calcIni[A:AH] = rule[A](loc.calcIniL, loc.calcIni, IniKey, 
    as ⇒ (as(Ge) + as(In) + as(Mu) + as(Mu)) rdiv 5)
  
  def calcLe[A:AH] = rule[A](loc.calcLeL, loc.calcLe, LeKey, 
    as ⇒ (as(Ko) + as(Ko) + as(Kk)) rdiv 2)
  
  def calcMr[A:AH]= rule[A](loc.calcMrL, loc.calcMr, MrKey, 
    as ⇒ (as(Mu) + as(Kl) + as(Ko)) rdiv 5)
  
  def calcPa[A:AH] = rule[A](loc.calcPaL, loc.calcPa, PaKey, 
    as ⇒ (as(Ge) + as(In) + as(Kk)) rdiv 5)

  def calcWs[A:AH] = rule[A](loc.calcWoundThresholdL,
    loc.calcWoundThreshold, WsKey, _(Ko) rdiv 2)

  def maxBoughtAe[A:AH]: Rule[A] = Rule.state(loc.maxBoughtAeL.name, for{
    a ← init[A]
    ch = immutable(a) apply (Ch)
    _ ← AH[A].derived.maxBoughtAe := ch
  } yield ())

  def maxBoughtAttribute[A:AH]: Rule[A] = {
    def maxBought (a: A) =
      AH[A].attributes.creation get a map (_ rdiv 2)

    Rule.state(loc.maxBoughtAttributeL.name, init[A] >>=
      (AH[A].attributes.maxBought := maxBought(_) void))
  }

  def maxBoughtAu[A:AH]: Rule[A] = Rule.state (loc.maxBoughtAuL.name, 
    init[A] >>= (a ⇒ AH[A].derived.maxBoughtAu :=
    (immutable(a) apply Ko) void)
  )

  def maxBoughtLe[A:AH]: Rule[A] = Rule.state (loc.maxBoughtLeL.name, 
    init[A] >>= (a ⇒ AH[A].derived.maxBoughtLe :=
    (immutable(a) apply Ko rdiv 2) void)
  )

  def maxBoughtMr[A:AH]: Rule[A] = Rule.state (loc.maxBoughtMrL.name, 
    init[A] >>= (a ⇒ AH[A].derived.maxBoughtMr :=
    (immutable(a) apply Mu rdiv 2) void)
  )

}

// vim: set ts=2 sw=2 et:
