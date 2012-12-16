package efa.dsa.rules.combat

import efa.core.Localization
import efa.dsa.being._, AttackMode._, efa.dsa.being.{CanAttack ⇒ CA}
import efa.dsa.being.abilities.{HasAbilities ⇒ HA}
import efa.dsa.being.equipment.{MeleeWeapon, RangedWeapon}
import efa.dsa.being.skills.{HasSkills ⇒ HS}
import efa.dsa.rules.{loc ⇒ Loc, FADRules}
import efa.dsa.world.Ebe
import efa.rpg.core.{HasModifiers ⇒ HM, Modifier}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules modify a hero's attack modes. They depend on all
 * modified values of the hero itself, including her talens, advantages, 
 * base- and derived values as well as carried equipment. Therefore, these
 * rules must be applied AFTER any rule that modifies the hero's other values.
 */
object AttackModeRules extends FADRules with AttackModeFunctions {
  type AmSt[A] = State[AttackMode,A]

  def all[A:CA:HM:HS:HA]: DList[Rule[A]] =
    canAttack ++ hasSkills ++ hasAbilities

  def canAttack[A:CA:HM]: DList[Rule[A]] = DList(
    baseAttackMode, weaponWrongHand, shieldIni, shieldAt, shieldPa,
    meleeWeaponIni, meleeWeaponWm
  )

  private def amRule[A:CA](l: Localization, f: A ⇒ CA.PfAmSt): Rule[A] =
    Rule[A](l.name, a ⇒ adjustAttackModes (a, f(a)))

  def baseAttackMode[A:CA:HM]: Rule[A] = {
    def addRest(a: A): AmSt[Unit] =
      addModsS(AwKey, modsFor (a, AwKey)) >>
      addModsS(IniKey, modsFor (a, IniKey))
            
    amRule[A](Loc.baseAttackModeL, a ⇒ {
      case am if am.isMelee ⇒ 
        addRest(a) >>
        addModsS(AtFkKey, modsFor (a, AtKey)) >>
        addModsS(PaKey, modsFor (a, PaKey))
      
      case am ⇒ addRest(a) >> addModsS(AtFkKey, modsFor (a, FkKey))
    })
  }

  def weaponWrongHand[A:CA] = amRule[A](Loc.weaponWrongHandL, h ⇒  {
    case a @MeleeSingle(_, _, true, _) ⇒
      addAt(Loc.wrongHand, -9L) >> addPa(Loc.wrongHand, -9L)
    case a @Throwing(_, _, true, _) ⇒ addFk(Loc.wrongHand, -9L)
  })
  
  def shieldIni[A:CA] = amRule[A](Loc.shieldIniL, a ⇒  {
    case MeleeShield(_, s, _, _) ⇒ addIni(s.name, s.data.ini)
  })
  
  def shieldAt[A:CA] = amRule[A](Loc.shieldAtL, a ⇒  {
    case MeleeShield(_, s, _, _) ⇒ addAt(s.name, s.data.wm.at)
  })
  
  def shieldPa[A:CA] = amRule[A](Loc.shieldPaL, a ⇒  {
    case MeleeShield(_, s, _, _) ⇒ addPa(s.name, s.data.wm.pa)
  })

  def meleeWeaponIni[A:CA] = amRule[A](Loc.meleeWeaponIniL, a ⇒ {
    case MeleeShield(w, _, _, _) ⇒ addIni (w.name, w.data.ini)
    case MeleeSingle(w, _, _, _) ⇒ addIni (w.name, w.data.ini)
    case MeleeTwoHanded(w, _, _) ⇒ addIni (w.name, w.data.ini)
  })

  def meleeWeaponWm[A:CA] = amRule[A](Loc.meleeWeaponWmL, a ⇒ {
    case MeleeShield(w, _, _, _) ⇒ addAt (Loc.wm, w.data.wm.at)
    case MeleeSingle(w, _, _, _) ⇒ 
      addAt (Loc.wm, w.data.wm.at) >>
      addPa (Loc.wm, w.data.wm.pa)
    case MeleeTwoHanded(w, _, _) ⇒ 
      addAt (Loc.wm, w.data.wm.at) >>
      addPa (Loc.wm, w.data.wm.pa)
  })

  // *** HasSkills ***

  def hasSkills[A:CA:HS:HM]: DList[Rule[A]] = DList(
    meleeTalentAtPa, rangedTalentFk, beToAtPa, beToFk
  )

  def meleeTalentAtPa[A:CA:HS] = amRule[A](Loc.meleeTalentAtPaL, a ⇒ {
    case am if am.isMelee ⇒ meleeAtPa(am.talent, a) match {
      case Some((at, pa)) ⇒ 
        addAt(am.talent, at) >> addPa(am.talent, pa)
      case None ⇒ 
        addAt(Loc.noTalent, -2L) >> addPa(Loc.noTalent, -3L)
    }
  })

  def rangedTalentFk[A:CA:HS] = amRule[A](Loc.rangedTalentFkL, a ⇒ {
    case am if (! am.isMelee) ⇒ rangedTaw(am.talent, a) match {
      case Some(fk) ⇒ addFk(am.talent, fk)
      case None ⇒ addFk(Loc.noTalent, -5L)
    }
  })

  private def ebe[A:HM] (a: A, ebe: Option[Ebe]): Long =
    ebe cata (_ calcEbe prop(a, BeKey), prop(a, BeKey))

  def beToAtPa[A:CA:HS:HM] = {
    def atpa (ebe: Long): (Long, Long) = (ebe / 2L, ebe / 2L + (ebe % 2L))

    def adj (n: String, a: A) = {
      val (at, pa) = atpa(ebe(a, melee (n, a) map (_.item.ebe)))

      addAt(Loc.ebe, -at) >> addPa (Loc.ebe, -pa)
    }

    amRule[A](Loc.beToAtPaL, a ⇒ {
      case MeleeShield(w, _, _, _) ⇒ adj(w.name, a)
      case MeleeSingle(w, _, _, _) ⇒ adj(w.name, a)
      case MeleeTwoHanded(w, _, _) ⇒ adj(w.name, a)
      case Unarmed (n, _)          ⇒ adj(n, a)
    })
  }

  def beToFk[A:CA:HS:HM] = {
    def adj (n: String, a: A) = 
      addFk(Loc.ebe, - ebe(a, ranged(n, a) map (_.item.ebe)))
    
    amRule[A](Loc.beToFkL, a ⇒ {
      case Shooting(w, _, _, _) ⇒ adj(w.name, a)
      case Throwing(w, _, _, _) ⇒ adj(w.name, a)
    })
  }

  // *** HasAbilities ***


  def hasAbilities[A:CA:HA]: DList[Rule[A]] = DList(
    linkhandShieldPa, linkhandWeapon, beidhandigerKampfI,
    beidhandigerKampfII, schildkampfI, schildkampfII
  )

  def linkhandShieldPa[A:CA:HA] = amRule[A](Loc.linkhandShieldPaL, a ⇒ {
    case MeleeShield(_, _, _, _) if hasFeat(Loc.linkhand, a) ⇒ 
      addPa(Loc.linkhand, 1L)
  })

  private def wrongHandFeat[A:CA:HA](loc: Localization, name: String) =
    amRule[A](loc, a ⇒ {
      case MeleeSingle(_, _, true, _) if hasFeat(name, a) ⇒ 
        addAt(name, 3L) >> addPa(name, 3L)
      case Throwing(_, _, true, _) if hasFeat(name, a) ⇒ addFk(name, 3L)
    })

  def linkhandWeapon[A:CA:HA] =
    wrongHandFeat[A](Loc.linkhandWeaponL, Loc.linkhand)

  def beidhandigerKampfI[A:CA:HA] =
    wrongHandFeat[A](Loc.beidhandigerKampfIL, Loc.beidhandigerKampfI)

  def beidhandigerKampfII[A:CA:HA] =
    wrongHandFeat[A](Loc.beidhandigerKampfIIL, Loc.beidhandigerKampfII)

  def schildkampfI[A:CA:HA] = amRule[A](Loc.schildkampfIL, a ⇒ {
    case MeleeShield(_, _, _, _) if hasFeat(Loc.schildkampfI, a) ⇒ 
      addPa(Loc.schildkampfI, 5L)
  })

  def schildkampfII[A:CA:HA] = amRule[A](Loc.schildkampfIIL, a ⇒ {
    case MeleeShield(_, _, _, _) if hasFeat(Loc.schildkampfII, a) ⇒ 
      addPa(Loc.schildkampfII, 5L)
  })
}

// vim: set ts=2 sw=2 et:
