package efa.dsa.rules.humanoid

import efa.dsa.rules.{loc ⇒ Loc, FADRules}
import efa.dsa.being._
import efa.dsa.being.abilities.{HasAbilities ⇒ HA}
import efa.dsa.world._
import efa.rpg.core._, efa.rpg.core.{Modified ⇒ M}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules all depend on BE.
 * They affect INI, Dodge, GS.
 * They must be the last rules concerning GS, since
 * the GS-rule in this set must not lower the GS below 1.
 */
object BeRules extends FADRules {
  def all[A:HA:AsBeing:M]: DList[Rule[A]] =
    hasAbilities ++ asBeing

  def hasAbilities[A:HA:M]: DList[Rule[A]] = DList(
    kampfgespurIni, kampfreflexeIni, iniBe, 
    dodgeBe, flinkGs, flinkDodge, gsBe
  )

  def asBeing[A:AsBeing:M]: DList[Rule[A]] = DList(lowLe, lowAu)
  
  def dodgeBe[A:HA:M]: Rule[A] = oModRule[A](
    Loc.dodgeBeL, BeKey.loc.locName, AwKey, - prop(_, BeKey))
  
  def iniBe[A:HA:M]: Rule[A] = oModRule[A] (
    Loc.iniBeL, BeKey.loc.locName, IniKey, - prop(_, BeKey))

  def flinkGs[A:HA:M]: Rule[A] = advantageRule[A](
    Loc.flinkGsL, Loc.flink, flinkMod, GsKey)
  
  def flinkDodge[A:HA:M]: Rule[A] = advantageRule[A](
    Loc.flinkDodgeL, Loc.flink, flinkMod, AwKey)

  def gsBe[A:HA:M]: Rule[A] = oModRule[A](
    Loc.gsBeL, BeKey.loc.locName, GsKey,
    a ⇒ gsNotBelow1(a, prop(a, BeKey))
  )
  
  def kampfgespurIni[A:HA:M]: Rule[A] = featRule[A](
    Loc.kampfgespurIniL, Loc.kampfgespur, _ ⇒ 2L, IniKey)
  
  def kampfreflexeIni[A:HA:M]: Rule[A] = {
    def mod (a: A) = (prop(a, BeKey) < 5L) ? 4L | 0L

    featRule[A](Loc.kampfreflexeIniL, Loc.kampfreflexe, mod, IniKey)
  }

  def lowLe[A:AsBeing:M]: Rule[A] = {
    def calc(a: A): State[A,Unit] = {
      val mod = le(a) match {
        case x if (x < (maxLe(a) rdiv 4L)) ⇒ 3L
        case x if (x < (maxLe(a) rdiv 3L)) ⇒ 2L
        case x if (x < (maxLe(a) rdiv 2L)) ⇒ 1L
        case _                             ⇒ 0L
      }

      for {
        _ ← oModAddS(Loc.lowLe, -mod, AtKey)
        _ ← oModAddS(Loc.lowLe, -mod, PaKey)
        _ ← oModAddS(Loc.lowLe, -mod, AwKey)
        _ ← oModAddS(Loc.lowLe, -mod, FkKey)
        _ ← oModAddS(Loc.lowLe, gsNotBelow1 (a, mod), GsKey)
      } yield ()
    }

    Rule.state(Loc.lowLeL.name, init[A] >>= calc)
  }

  def lowAu[A:AsBeing:M]: Rule[A] = {
    def calc(a: A): State[A,Unit] = {
      val mod = au(a) match {
        case x if (x < (maxAu(a) rdiv 4L)) ⇒ 2L
        case x if (x < (maxAu(a) rdiv 3L)) ⇒ 1L
        case _                             ⇒ 0L
      }

      for {
        _ ← oModAddS(Loc.lowAu, -mod, AtKey)
        _ ← oModAddS(Loc.lowAu, -mod, PaKey)
        _ ← oModAddS(Loc.lowAu, -mod, AwKey)
        _ ← oModAddS(Loc.lowAu, -mod, FkKey)
        _ ← oModAddS(Loc.lowAu, -mod, IniKey)
      } yield ()
    }

    Rule.state(Loc.lowAuL.name, init[A] >>= calc)
  }
   
  private def flinkMod[A:M](a: A, v: Int) = (v, prop(a, BeKey)) match {
    case (2, x) if (x < 5L) ⇒ 2
    case (2, x) if (x < 7L) ⇒ 1
    case (1, y) if (y < 5L) ⇒ 1
    case (0, y) if (y < 5L) ⇒ 1
    case _ ⇒ 0
  }

  private def gsNotBelow1[A:M] (a: A, mod: Long): Long =
    prop(a, GsKey) match {
      case x if (x <= 1L)  ⇒  0L
      case x if (x <= mod) ⇒  - (x - 1L)
      case _               ⇒ - mod
    }
}

// vim: set ts=2 sw=2 et:
