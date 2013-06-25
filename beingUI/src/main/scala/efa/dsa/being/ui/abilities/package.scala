package efa.dsa.being.ui

import dire._, dire.swing.Swing._
import efa.dsa.being.abilities._
import efa.nb.dialog.{DialogEditable ⇒ DE, DEInfo}
import efa.nb.Widgets._
import scalaz._, Scalaz._, effect.IO

package object abilities {
  implicit lazy val AdvantageE = DE io1 { a: Advantage ⇒ info(a) }
  implicit lazy val DisadvantageE = DE io1 { a: Handicap ⇒ info(a) }
  implicit lazy val FeatE = DE io1 { a: Feat ⇒ info(a) }

  def info[A,B](a: Ability[A,B])(implicit B: AbilityData[B]): DEInfo[B] = for {
    dp ← DescribedPanel(a)

    in = ^(valid(dp.name.in), valid(dp.desc.in))((n,d) ⇒
           ((B.nameL := n) >> (B.descL := d)) exec a.data
         )

    el = (efa.core.loc.name beside dp.name) above
         (efa.core.loc.desc beside dp.sp)
  } yield (dp size el, in)
}

// vim: set ts=2 sw=2 et:
