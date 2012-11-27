package efa.dsa.being.ui.hero

import efa.core._, Efa._
import efa.nb.VSET
import efa.dsa.being._
import efa.dsa.being.ui.{loc ⇒ uiLoc}
import efa.react.SET
import efa.react.swing.Swing
import efa.rpg.being.BeingPanel
import scala.swing.TextField
import scalaz._, Scalaz._

class HeroHumanoidPanel[A:AsHero] extends BeingPanel[A,HeroData] {
  import AsHero._
  
  val tAe = number
  val tAu = number
  val tKe = number
  val tLe = number

  val tAeB = number
  val tAuB = number
  val tKeB = number
  val tLeB = number
  val tMrB = number
  
  val tAeM = numberDisabled
  val tAt = numberDisabled
  val tAuM = numberDisabled
  val tAw = numberDisabled
  val tFk = numberDisabled
  val tGs = numberDisabled
  val tIni = numberDisabled
  val tKeM = numberDisabled
  val tLeM = numberDisabled
  val tMr = numberDisabled
  val tPa = numberDisabled
  val tWs = numberDisabled

  def set: VSET[A,HeroData] = null
    valInHuman(ae, setAe)(tAe) ⊹ 
    valInHuman(au, setAu)(tAu) ⊹ 
    valInHuman(ke, setKe)(tKe) ⊹ 
    valInHuman(le, setLe)(tLe) ⊹ 
    valInHero(boughtAe, setBoughtAe)(tAeB) ⊹ 
    valInHero(boughtAu, setBoughtAu)(tAuB) ⊹ 
    valInHero(boughtKe, setBoughtKe)(tKeB) ⊹ 
    valInHero(boughtLe, setBoughtLe)(tLeB) ⊹ 
    valInHero(boughtMr, setBoughtMr)(tMrB) ⊹ 
    modifiedProp(AeKey)(tAeM) ⊹ 
    modifiedProp(AtKey)(tAt) ⊹ 
    modifiedProp(AuKey)(tAuM) ⊹ 
    modifiedProp(AwKey)(tAw) ⊹ 
    modifiedProp(FkKey)(tFk) ⊹ 
    modifiedProp(GsKey)(tGs) ⊹ 
    modifiedProp(IniKey)(tIni) ⊹ 
    modifiedProp(KeKey)(tKeM) ⊹ 
    modifiedProp(LeKey)(tLeM) ⊹ 
    modifiedProp(MrKey)(tMr) ⊹ 
    modifiedProp(PaKey)(tPa) ⊹ 
    modifiedProp(WsKey)(tWs) ⊹
    maxBoughtTT(maxBoughtAe, tAeB) ⊹
    maxBoughtTT(maxBoughtAu, tAuB) ⊹
    maxBoughtTT(maxBoughtKe, tKeB) ⊹
    maxBoughtTT(maxBoughtLe, tLeB) ⊹
    maxBoughtTT(maxBoughtMr, tMrB)

  (
    ("" beside uiLoc.actual beside uiLoc.max beside
      uiLoc.bought beside "" beside uiLoc.actual) above
    (LeKey beside tLe beside tLeM beside tLeB beside AtKey beside tAt) above
    (AuKey beside tAu beside tAuM beside tAuB beside PaKey beside tPa) above
    (AeKey beside tAe beside tAeM beside tAeB beside FkKey beside tFk) above
    (KeKey beside tKe beside tKeM beside tKeB beside AwKey beside tAw) above
    (MrKey beside tMr beside "" beside tMrB beside IniKey beside tIni) above
    (WsKey beside tWs beside "" beside "" beside GsKey beside tGs)
  ).add()

  private def valInHero (
    get: A ⇒ Long,
    set: (A, Long) ⇒ ValSt[HeroData]
  )(t: TextField): VSET[A,HeroData] =
    getSet(get)(set, readVals[Long](t))

  private def valInHuman (
    get: A ⇒ Long,
    set: (A, Long) ⇒ ValSt[HumanoidData]
  )(t: TextField): VSET[A,HeroData] =
    valInHero(get, set ∘ (_ ∘ HeroData.humanoid.lifts))(t)

  private def maxBoughtTT(f: A ⇒ Long, t: TextField):VSET[A,HeroData] =
    outOnly (Swing tooltip t) ∙ (uiLoc maxBought f(_).toString)
}

// vim: set ts=2 sw=2 et:
