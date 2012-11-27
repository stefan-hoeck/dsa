package efa.dsa.being.ui.humanoid

import efa.dsa.being._, efa.dsa.being.{loc ⇒ bLoc, HumanoidData ⇒ HD}
import efa.rpg.being.BeingPanel
import scalaz._, Scalaz._

class BattlePanel[A:AsHumanoid] extends BeingPanel[A,HD] {
  import AsHumanoid._

  val atC = numberDisabled
  val awC = numberDisabled
  val beC = numberDisabled
  val exhaustionC = number
  val fkC = numberDisabled
  val overstrainC = numberDisabled
  val paC = numberDisabled
  val rsC = numberDisabled
  val woundsC = number
  val wsC = numberDisabled

  (AtKey.loc.shortName beside atC beside
    PaKey.loc.shortName beside paC beside
    FkKey.loc.shortName beside fkC beside
    AwKey.loc.shortName beside awC beside
    bLoc.exhaustion beside exhaustionC) above
  (RsKey.loc.shortName beside rsC beside
    BeKey.loc.shortName beside beC beside
    WsKey.loc.shortName beside wsC beside
    bLoc.wounds beside woundsC beside
    bLoc.overstrain beside overstrainC) add()

  lazy val set =
    modifiedProp(AtKey)(atC) ⊹
    modifiedProp(AwKey)(awC) ⊹
    modifiedProp(BeKey)(beC) ⊹
    modifiedProp(FkKey)(fkC) ⊹
    modifiedProp(OverstrainKey)(overstrainC) ⊹
    modifiedProp(PaKey)(paC) ⊹
    modifiedProp(RsKey)(rsC) ⊹
    modifiedProp(WsKey)(wsC) ⊹
    (longIn(exhaustionC, Exhaustion.validate)(HD.exhaustion) ∙ humanoidData[A]) ⊹
    (longIn(woundsC, Wounds.validate)(HD.wounds) ∙ humanoidData[A])
}

// vim: set ts=2 sw=2 et:
