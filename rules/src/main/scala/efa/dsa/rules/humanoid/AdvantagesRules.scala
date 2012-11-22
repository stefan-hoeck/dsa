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
    ausdauernd, ausweichenI, ausweichenII, ausweichenIII,
    behabigAw, behabigGs, eisernWs, glasknochenWs,
    halbzaubererAe, halbzaubererMr, hoheLe, hoheMr, kleinwuchsigGs,
    kurzatmig, magierAe, niedrigeLe, niedrigeMr,
    viertelzaubererAe, vollzaubererAe, vollzaubererMr, zwergenwuchsGs
  )
  
  def ausdauernd[A:HA:M] = advantageRule[A](
    Loc.ausdauerndL, Loc.ausdauernd, (_, x) ⇒ x, AuKey)
  
  def ausweichenI[A:HA:M] = featRule[A](
    Loc.ausweichenIL, Loc.ausweichenI, _ ⇒ 3, AwKey)
  
  def ausweichenII[A:HA:M] = featRule[A](
    Loc.ausweichenIIL, Loc.ausweichenII, _ ⇒ 3, AwKey)
  
  def ausweichenIII[A:HA:M] = featRule[A](
    Loc.ausweichenIIIL, Loc.ausweichenIII, _ ⇒ 3, AwKey)
  
  def behabigAw[A:HA:M] = handicapRule[A](
    Loc.behabigDodgeL, Loc.behabig, (_, _) ⇒ -1, AwKey)
  
  def behabigGs[A:HA:M] = handicapRule[A](
    Loc.behabigGsL, Loc.behabig, (_, _) ⇒ -1, GsKey)
  
  def eisernWs[A:HA:M] = advantageRule[A](
    Loc.eisernWoundThresholdL, Loc.eisern, (_, _) ⇒ 3, WsKey)
  
  def glasknochenWs[A:HA:M] = handicapRule[A](
    Loc.glasknochenWoundThresholdL, Loc.glasknochen, (_, _) ⇒ -2, WsKey)
  
  def halbzaubererAe[A:HA:M] = advantageRule[A](
    Loc.halbzaubererAeL, Loc.halbzauberer, (_, _) ⇒ 6, AeKey)
  
  def halbzaubererMr[A:HA:M] = advantageRule[A](
    Loc.halbzaubererMrL, Loc.halbzauberer, (_, _) ⇒ 1, MrKey)
  
  def hoheLe[A:HA:M] = advantageRule[A](
    Loc.hoheLebenskraftL, Loc.hoheLebenskraft, (_, x) ⇒ x, LeKey)
  
  def hoheMr[A:HA:M] = advantageRule[A](
    Loc.hoheMagieresistenzL, Loc.hoheMagieresistenz, (_, x) ⇒ x, MrKey)
  
  def kleinwuchsigGs[A:HA:M] = handicapRule[A](
    Loc.kleinwuchsigGsL, Loc.kleinwuchsig, (_, _) ⇒ -1, GsKey)
  
  def kurzatmig[A:HA:M] = handicapRule[A](
    Loc.kurzatmigL, Loc.kurzatmig, (_, x) ⇒ -x, AuKey)
  
  def magierAe[A:HA:M] = advantageRule[A](
    Loc.magierAeL, Loc.magier, (_, _) ⇒ 6, AeKey)
  
  def niedrigeLe[A:HA:M] = handicapRule[A](
    Loc.niedrigeLebenskraftL, Loc.niedrigeLebenskraft, (_, x) ⇒  -x, LeKey)
  
  def niedrigeMr[A:HA:M] = handicapRule[A](
    Loc.niedrigeMagieresistenzL,
    Loc.niedrigeMagieresistenz, (_, x) ⇒ -x, MrKey
  )
  
  def viertelzaubererAe[A:HA:M] = advantageRule[A](
    Loc.viertelzaubererAeL, Loc.viertelzauberer, (_, _) ⇒ -6, AeKey)
  
  def vollzaubererMr[A:HA:M] = advantageRule[A](
    Loc.vollzaubererMrL, Loc.vollzauberer, (_, _) ⇒ 2, MrKey)
  
  def vollzaubererAe[A:HA:M] = advantageRule[A](
    Loc.vollzaubererAeL, Loc.vollzauberer, (_, _) ⇒ 12, AeKey)
  
  def zwergenwuchsGs[A:HA:M] = handicapRule[A](
    Loc.zwergenwuchsGsL, Loc.zwergenwuchs, (_, _) ⇒ -2, GsKey)
}

// vim: set ts=2 sw=2 et:
