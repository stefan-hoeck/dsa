package efa.dsa.being.ui.hero

import efa.core.Efa._
import efa.dsa.being._
import efa.dsa.being.{HeroBaseData ⇒ HBD}
import efa.dsa.being.ui.{loc ⇒ uiLoc}
import efa.react.swing.textComponent
import efa.rpg.being.BeingPanel
import scalaz._, Scalaz._

class HeroApPanel extends BeingPanel[HBD,HBD] {
  val tAp = number
  val tApUsed = number
  val tRestAp = numberDisabled

  def set = 
    getSet((_: HBD).ap)(_ setAp _, readVals[Long](tAp)) ⊹ 
    getSet((_: HBD).apUsed)(_ setApUsed _, readVals[Long](tApUsed)) ⊹
    outOnly(textComponent text tRestAp contramap (_.restAp.toString))

  uiLoc.total beside tAp beside uiLoc.used beside tApUsed beside 
  uiLoc.rest beside tRestAp add()
}

// vim: set ts=2 sw=2 et:
