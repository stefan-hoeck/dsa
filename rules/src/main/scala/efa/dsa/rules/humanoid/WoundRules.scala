package efa.dsa.rules.humanoid

import efa.dsa.rules.{loc ⇒ Loc, FADRules}
import efa.dsa.being._
import efa.dsa.world.Attribute.Ge
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

object WoundRules extends FADRules {
  def being[A:AsBeing]: DList[Rule[A]] = DList(wounds)

  def all[A:AsHumanoid]: DList[Rule[A]] = being

  def wounds[A:AsBeing]: Rule[A] = {
    val st: State[A,Unit] = for {
      a ← init[A]
      gm = -wounds(a)
      m = 2L * gm
      _ ← oModAddS(Loc.wounds, m, AtKey)
      _ ← oModAddS(Loc.wounds, m, PaKey)
      _ ← oModAddS(Loc.wounds, m, FkKey)
      _ ← oModAddS(Loc.wounds, m, AwKey)
      _ ← oModAddS(Loc.wounds, m, IniKey)
      _ ← oModAddS(Loc.wounds, gm, GsKey)
      _ ← oModAddS(Loc.wounds, m, attributeKeyFor(Ge))
    } yield a

    Rule.state(Loc.woundsL.name, st)
  }
}

// vim: set ts=2 sw=2 et:
