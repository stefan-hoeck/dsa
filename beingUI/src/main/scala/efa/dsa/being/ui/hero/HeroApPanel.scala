package efa.dsa.being.ui.hero

import dire._, dire.swing._, Swing._
import efa.core.Efa._
import efa.dsa.being._
import efa.dsa.being.{HeroBaseData ⇒ HBD}
import efa.dsa.being.ui.{loc ⇒ uiL}
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

object HeroApPanel {
  def apply(): IO[BeingPanel[HBD,HBD,Panel]] = for {
    ap     ← numeric
    apUsed ← numeric
    restAp ← disabledNumeric
    panel  ← Panel(border := Border.title(uiL.ap))

    _ ← uiL.total <> ap <> uiL.used <> apUsed <> uiL.rest <> restAp addTo panel

    sf = getSet((_: HBD).ap)(_ setAp _, readShow[Long](ap.sf)) ⊹ 
         getSet((_: HBD).apUsed)(_ setApUsed _, readShow[Long](apUsed.sf)) ⊹
         outOnly(restAp.text ∙ (_.restAp.toString))
  } yield BeingPanel(panel, sf)
}

// vim: set ts=2 sw=2 et:
