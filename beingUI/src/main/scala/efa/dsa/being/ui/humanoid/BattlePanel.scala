package efa.dsa.being.ui.humanoid

import dire._, dire.swing._, Swing._
import efa.dsa.being._, efa.dsa.being.{loc ⇒ bLoc, HumanoidData ⇒ HD}
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

object BattlePanel {
  import AsHumanoid.{humanoidData ⇒ hd}

  def apply[A:AsHumanoid](): IO[BeingPanel[A,HD,Panel]] = for {
    at         ← disabledNumeric
    aw         ← disabledNumeric
    be         ← disabledNumeric
    exhaustion ← numeric
    fk         ← disabledNumeric
    overstrain ← disabledNumeric
    pa         ← disabledNumeric
    rs         ← disabledNumeric
    wounds     ← numeric
    ws         ← disabledNumeric
    panel      ← Panel(border := Border.title(efa.dsa.being.ui.loc.battleValues))

    _          ← (AtKey.loc.shortName beside at beside
                  PaKey.loc.shortName beside pa beside
                  FkKey.loc.shortName beside fk beside
                  AwKey.loc.shortName beside aw beside
                  bLoc.exhaustion beside exhaustion) above
                 (RsKey.loc.shortName beside rs beside
                  BeKey.loc.shortName beside be beside
                  WsKey.loc.shortName beside ws beside
                  bLoc.wounds beside wounds beside
                  bLoc.overstrain beside overstrain) addTo panel

    sf = (longSf(exhaustion.sfE, Exhaustion.validate)(HD.exhaustion) ∙ hd[A]) ⊹
         (longSf(wounds.sfE, Wounds.validate)(HD.wounds) ∙ hd[A]) ⊹
         modifiedProp(AtKey, at) ⊹
         modifiedProp(AwKey, aw) ⊹
         modifiedProp(BeKey, be) ⊹
         modifiedProp(FkKey, fk) ⊹
         modifiedProp(OverstrainKey, overstrain) ⊹
         modifiedProp(PaKey, pa) ⊹
         modifiedProp(RsKey, rs) ⊹
         modifiedProp(WsKey, ws)
  } yield BeingPanel(panel, sf)
}

// vim: set ts=2 sw=2 et:
