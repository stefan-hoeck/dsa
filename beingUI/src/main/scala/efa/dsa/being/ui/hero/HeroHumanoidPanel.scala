package efa.dsa.being.ui.hero

import efa.core._, Efa._
import efa.dsa.being._
import efa.dsa.being.ui.{loc ⇒ uiLoc}
import efa.react.SET
import efa.rpg.being.BeingPanel
import scalaz._, Scalaz._

class HeroHumanoidPanel extends BeingPanel[Hero,HeroData] {
  import HeroHumanoidPanel._
  
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

  def set = 
    damageIn(_.ae, _ setAe _)(tAe) ⊹ 
    damageIn(_.au, _ setAu _)(tAu) ⊹ 
    damageIn(_.ke, _ setKe _)(tKe) ⊹ 
    damageIn(_.le, _ setLe _)(tLe) ⊹ 
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
    (
      intIn(tLeB, BoughtLe.validate)(HL.boughtLe) ⊹
      intIn(tAuB, BoughtAu.validate)(HL.boughtAu) ⊹
      intIn(tAeB, BoughtAe.validate)(HL.boughtAe) ⊹
      intIn(tKeB, BoughtKe.validate)(HL.boughtKe) ⊹
      intIn(tMrB, BoughtMr.validate)(HL.boughtMr)
    ).contramap (_.data)

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

  private def damageIn (get: Hero ⇒ Int, set: (Hero, Int) ⇒ ValSt[HeroData])
    (t: scala.swing.TextField): VSET[Hero,HeroData] =
    getSet(get)(set, readVals[Int](t))

}

object HeroHumanoidPanel {
  val HL = HeroData.humanoid
}

// vim: set ts=2 sw=2 et:
