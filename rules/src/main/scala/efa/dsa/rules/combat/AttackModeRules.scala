package efa.dsa.rules.combat

import efa.core.Localization
import efa.dsa.being._, AttackMode._, efa.dsa.being.{CanAttack ⇒ CA}
import efa.dsa.rules.{loc ⇒ Loc, FADRules}
import efa.rpg.core.{HasModifiers ⇒ HM, Modifier}
import efa.rpg.rules.Rule
import scalaz._, Scalaz._

/**
 * The following rules modify a hero's attack modes. They depend on all
 * modified values of the hero itself, including her talens, advantages, 
 * base- and derived values as well as carried equipment. Therefore, these
 * rules must be applied AFTER any rule that modifies the hero's other values.
 */
object AttackModeRules extends FADRules {
  type AmSt = State[AttackMode,Unit]

  def all[A:CA:HM]: DList[Rule[A]] = canAttack

  def canAttack[A:CA:HM]: DList[Rule[A]] = DList(
    baseAttackMode, weaponWrongHand
  )

  private def amRule[A:CA](l: Localization, f: A ⇒ CA.PfAmSt): Rule[A] =
    Rule[A](l.name, a ⇒ adjustAttackModes (a, f(a)))

  def baseAttackMode[A:CA:HM]: Rule[A] = {
    def addRest(a: A): AmSt =
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
      addModS(AtFkKey, wrongHandMod) >> addModS(PaKey, wrongHandMod)
    case a @Throwing(_, _, true, _) ⇒ addModS(AtFkKey, wrongHandMod)
  })

  private val wrongHandMod = Modifier(Loc.wrongHand, -9L)
}

//object AttackModeRules extends NbBundled {
//  
//  def beingRules[H <: DsaBeingBuilder[H]]: List[Rule[H]] = 
//    List(baseAttackModeRule[H], weaponWrongHandRule[H],
//         linkhandShieldPaRule[H], linkhandWeaponRule[H],
//         beidhandigerKampfIWeaponRule[H], beidhandigerKampfIIWeaponRule[H],
//         shieldIniRule[H], shieldAtRule[H], shieldPaRule[H],
//         schildkampfIRule[H], schildkampfIIRule[H], meleeWeaponIniRule[H],
//         meleeWeaponWmRule[H], beToAtPaRule[H], beToFkRule[H])
//  
//  def skilledRules[H <: SkilledBeingBuilder[H]]: List[Rule[H]] =
//    List(meleeTalentAtPaRule[H], rangedTalentFkRule[H]) ::: beingRules[H]
//
//  def meleeTalentAtPaRule[H <: SkilledBeingBuilder[H]] = {
//    def addTalent(name: String, h: H, a: AttackMode): AttackMode =
//      h meleeTalentByName name match {
//        case None => a.addAt(-2L, noTalent).addPa(-3L, noTalent)
//        case Some(x) => a.addAt(x.parent.at, name).addPa(x.pa, name)
//      }
//    amRule[H]("meleeTalentAtPaRule", h => {
//        case a @MeleeSingle(w, _, _, _, _) => addTalent(w.talent, h, a)
//          //do not include talent pa for shields
//        case a @MeleeShield(w, _, _, _, _) => h meleeTalentByName w.talent match {
//            case None => a.addAt(-2L, noTalent)
//            case Some(x) => a.addAt(x.parent.at, x.name)
//          }
//        case a @MeleeTwoHanded(w, _, _, _) => addTalent(w.talent, h, a)
//        case a @Unarmed(n, _, _) => addTalent(n, h, a)
//      })
//  }
//
//  def rangedTalentFkRule[H <: SkilledBeingBuilder[H]] = {
//    def addTalent(name: String, h: H, a: AttackMode): AttackMode =
//      h rangedTalentByName name match {
//        case None => a.addFk(-5L, noTalent)
//        case Some(x) => a.addFk(x.taw, name)
//      }
//    amRule[H]("rangedTalentFkRule", h => {
//        case a @Throwing(w, _, _, _, _) => addTalent(w.talent, h, a)
//        case a @Shooting(w, _, _, _, _) => addTalent(w.talent, h, a)
//      })
//  }
//
//  def linkhandShieldPaRule[H <: DsaBeingBuilder[H]] =
//    amRule[H]("linkhandShieldPaRule", h => {
//      case a @MeleeShield(_, _, _, _, _) if (h hasFeat linkhand) =>
//        a.addPa(1L, linkhand)})
//  
//
//  def linkhandWeaponRule[H <: DsaBeingBuilder[H]] =
//    amRule[H]("linkhandWeaponRule", h => {
//      case a @MeleeSingle(_, _, _, true, _) if (h hasFeat linkhand) =>
//        a.addAt(3L, linkhand).addPa(3L, linkhand)
//      case a @Throwing(_, _, _, true, _) if (h hasFeat linkhand) =>
//        a.addFk(3L, linkhand)
//    })
//  
//  def beidhandigerKampfIWeaponRule[H <: DsaBeingBuilder[H]] =
//    amRule[H]("beidhandigerKampfIWeaponRule", h => {
//      case a @MeleeSingle(_, _, _, true, _) if (h hasFeat beidhandigerKampfI) =>
//        a.addAt(3L, beidhandigerKampfI).addPa(3L, beidhandigerKampfI)
//      case a @Throwing(_, _, _, true, _) if (h hasFeat beidhandigerKampfI) =>
//        a.addFk(3L, beidhandigerKampfI)
//    })
//  
//  def beidhandigerKampfIIWeaponRule[H <: DsaBeingBuilder[H]] =
//    amRule[H]("beidhandigerKampfIIWeaponRule", h => {
//      case a @MeleeSingle(_, _, _, true, _) if (h hasFeat beidhandigerKampfII) =>
//        a.addAt(3L, beidhandigerKampfII).addPa(3L, beidhandigerKampfII)
//      case a @Throwing(_, _, _, true, _) if (h hasFeat beidhandigerKampfII) =>
//        a.addFk(3L, beidhandigerKampfII)
//    })
//  
//  def shieldIniRule[H <: DsaBeingBuilder[H]] = amRule[H]("shieldIniRule", h => {
//      case a @MeleeShield(_, s, _, _, _) => a.addIni(s.ini, s.name)
//    })
//  
//  def shieldAtRule[H <: DsaBeingBuilder[H]] = amRule[H]("shieldAtRule", h => {
//      case a @MeleeShield(_, s, _, _, _) => a.addAt(s.wm.at, shieldWm)
//    })
//  
//  def shieldPaRule[H <: DsaBeingBuilder[H]] = amRule[H]("shieldPaRule", h => {
//      case a @MeleeShield(_, s, _, _, _) => a.addPa(s.wm.pa, shieldWm)
//    })
//
//  def schildkampfIRule[H <: DsaBeingBuilder[H]] =
//    amRule[H]("schildkampfIRule", h => {
//      case a @MeleeShield(_, s, _, _, _) if(h hasFeat schildkampfI) =>
//        a.addPa(5, schildkampfI)
//    })
//
//  def schildkampfIIRule[H <: DsaBeingBuilder[H]] =
//    amRule[H]("schildkampfIIRule", h => {
//      case a @MeleeShield(_, s, _, _, _) if(h hasFeat schildkampfII) =>
//        a.addPa(5, schildkampfII)
//    })
//
//  def meleeWeaponIniRule[H <: DsaBeingBuilder[H]] =
//    amRule[H]("meleeWeaponIniRule", h => {
//      case a @MeleeShield(w, _, _, _, _) => a addIni (w.ini, w.name)
//      case a @MeleeSingle(w, _, _, _, _) => a addIni (w.ini, w.name)
//      case a @MeleeTwoHanded(w, _, _, _) => a addIni (w.ini, w.name)
//    })
//
//  def meleeWeaponWmRule[H <: DsaBeingBuilder[H]] =
//    amRule[H]("meleeWeaponWmRule", h => {
//      case a @MeleeShield(w, _, _, _, _) => a addAt (w.wm.at, wm)
//      case a @MeleeSingle(w, _, _, _, _) => a addAt (w.wm.at, wm) addPa (w.wm.pa, wm)
//      case a @MeleeTwoHanded(w, _, _, _) => a addAt (w.wm.at, wm) addPa (w.wm.pa, wm)
//    })
//
//  private def ebe (h: DsaBeing, ebe: Option[Ebe]): Long =
//    ebe map (_ calcEbe h.be) getOrElse h.be
//
//  def beToAtPaRule[H <: DsaBeingBuilder[H]] = {
//    def atpa (ebe: Long): (Long, Long) = 
//      if ((ebe & 1L) == 1L) (ebe / 2L, ebe / 2L + 1L) else (ebe / 2L, ebe / 2L)
//    def calc (a: AttackMode, w: MeleeWeapon, h: H): AttackMode = {
//      val (at, pa) = atpa(ebe(h, Skills.meleeTalents.now find
//                              (_.name == w.talent) map (_.ebe)))
//      a addAt (-at, ebe) addPa (-pa, ebe)
//    }
//    amRule[H]("beToAtPaRule", h => {
//        case a @MeleeShield(w, _, _, _, _) => calc(a, w, h)
//        case a @MeleeSingle(w, _, _, _, _) => calc(a, w, h)
//        case a @MeleeTwoHanded(w, _, _, _) => calc(a, w, h)
//      })
//  }
//
//  def beToFkRule[H <: DsaBeingBuilder[H]] = {
//    def calc (a: AttackMode, w: RangedWeapon, h: H): AttackMode = 
//      a.addFk(- ebe(h, Skills.rangedTalents.now find (_.name == w.talent) map
//                    (_.ebe)), ebe)
//    
//    amRule[H]("beToFkRule", h => {
//        case a @Shooting(w, _, _, _, _) => calc(a, w, h)
//        case a @Throwing(w, _, _, _, _) => calc(a, w, h)
//      })
//  }
//}

// vim: set ts=2 sw=2 et:
