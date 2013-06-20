package efa.dsa.being.ui.humanoid

import dire._, dire.swing._, Swing._
import efa.dsa.being._, efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.world.BodyPart
import efa.nb.VStSF
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

object ZoneRsPanel {
  private val counts = 0 to zoneWoundsMax toList

  def apply[A:AsHumanoid](): IO[BeingPanel[A,HumanoidData,Panel]] = {
    def ui(bp: BodyPart): IO[(VStSF[A,HumanoidData], Elem)] = for {
      radios ← counts traverse (s ⇒ RadioButton(text :=  s.toString))
      text   ← disabledNumeric
      panel  ← Panel(border := titleBorder(bp.loc.locName))

      //SF
      wounds ← (radios zip counts).group
      sf     = (lensed(wounds)(lens(bp)) ∙ AsHumanoid.humanoidData[A]) ⊹ 
               modifiedProp(zoneRsKeyFor(bp), text)

      //Layout
      _      ← (bLoc.shortRs <> text) ^^ 
               (bLoc.wounds <> radios.foldMap(_ fillH 2 elem)) addTo panel
    } yield (sf, panel.elem)

    for {
      ps ← BodyPart.values traverse ui
      p  ← ps foldMap (_._2.horizontal) panel
    } yield BeingPanel(p, ps foldMap (_._1))
  }

  private def lens(bp: BodyPart): HumanoidData @> Int =
    HumanoidData.zoneWounds at bp
}

// vim: set ts=2 sw=2 et:
