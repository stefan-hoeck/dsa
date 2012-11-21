package efa.dsa.rules.humanoid

import efa.dsa.rules.{loc ⇒ Loc, FADRules}
import efa.dsa.being._
import efa.dsa.being.abilities.{HasAbilities ⇒ HA}
import efa.rpg.core._, efa.rpg.core.{Modified ⇒ M}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules depend only on the presence or absence of advantages.
 * They are independant of any other calculated values and should therefore
 * be applied early in the sequence of rules.
 */
object AdvantagesRules extends FADRules {
  def all[A:HA:M]: DList[Rule[A]] = DList[Rule[A]](
    hoheLebenskraft, ausdauernd, vollzaubererMr,
    vollzaubererAe, halbzaubererAe, viertelzaubererAe,
    halbzaubererMr, hoheMagieresistenz, 
    eisernWoundThreshold, magierAe
  )
  
  def ausdauernd[A:HA:M] = advantageRule[A](
    Loc.ausdauerndL, Loc.ausdauernd, (_, x) ⇒ x, AuKey)
  
  def eisernWoundThreshold[A:HA:M] = advantageRule[A](
    Loc.eisernWoundThresholdL, Loc.eisern, (_, _) ⇒ 3, WsKey)
  
  def halbzaubererAe[A:HA:M] = advantageRule[A](
    Loc.halbzaubererAeL, Loc.halbzauberer, (_, _) ⇒ 6, AeKey)
  
  def halbzaubererMr[A:HA:M] = advantageRule[A](
    Loc.halbzaubererMrL, Loc.halbzauberer, (_, _) ⇒ 1, MrKey)
  
  def hoheLebenskraft[A:HA:M] = advantageRule[A](
    Loc.hoheLebenskraftL, Loc.hoheLebenskraft, (_, x) ⇒ x, LeKey)
  
  def hoheMagieresistenz[A:HA:M] = advantageRule[A](
    Loc.hoheMagieresistenzL, Loc.hoheMagieresistenz, (_, x) ⇒ x, MrKey)
  
  def magierAe[A:HA:M] = advantageRule[A](
    Loc.magierAeL, Loc.magier, (_, _) ⇒ 6, AeKey)
  
  def viertelzaubererAe[A:HA:M] = advantageRule[A](
    Loc.viertelzaubererAeL, Loc.viertelzauberer, (_, _) ⇒ -6, AeKey)
  
  def vollzaubererMr[A:HA:M] = advantageRule[A](
    Loc.vollzaubererMrL, Loc.vollzauberer, (_, _) ⇒ 2, MrKey)
  
  def vollzaubererAe[A:HA:M] = advantageRule[A](
    Loc.vollzaubererAeL, Loc.vollzauberer, (_, _) ⇒ 12, AeKey)
}

// vim: set ts=2 sw=2 et:
