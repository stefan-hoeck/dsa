package efa.dsa.being.ui.hero

import efa.core._, Efa._
import efa.nb.VSET
import efa.dsa.being._
import efa.dsa.being.ui.{loc ⇒ uiLoc}
import efa.react.SET
import efa.rpg.being.BeingPanel
import scalaz._, Scalaz._

class HeroHumanoidPanel[A:AsHumanoid] extends BeingPanel[A,HumanoidData] {
  import AsHumanoid._
  
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

  def set: VSET[A,HumanoidData] =
    valIn(ae, setAe)(tAe) ⊹ 
    valIn(au, setAu)(tAu) ⊹ 
    valIn(ke, setKe)(tKe) ⊹ 
    valIn(le, setLe)(tLe) ⊹ 
    valIn(boughtAe, setBoughtAe)(tAeB) ⊹ 
    valIn(boughtAu, setBoughtAu)(tAuB) ⊹ 
    valIn(boughtKe, setBoughtKe)(tKeB) ⊹ 
    valIn(boughtLe, setBoughtLe)(tLeB) ⊹ 
    valIn(boughtMr, setBoughtMr)(tMrB) ⊹ 
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
    modifiedProp(WsKey)(tWs)

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

  private def valIn (
    get: A ⇒ Long,
    set: (A, Long) ⇒ ValSt[HumanoidData]
  )(t: scala.swing.TextField): VSET[A,HumanoidData] =
    getSet(get)(set, readVals[Long](t))

}

// vim: set ts=2 sw=2 et:
