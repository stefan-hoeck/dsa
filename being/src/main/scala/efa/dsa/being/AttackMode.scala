package efa.dsa.being

import efa.dsa.being.equipment._
import efa.rpg.core._
import scalaz._, Scalaz._

sealed trait AttackMode {
  def name: String

  def baseTp: DieRoller

  def isMelee: Boolean

  def modifiers: Modifiers

  def talent: String

  lazy val tp = (shapeless.lens[DieRoller] >> 'plus)
                  .set(baseTp, modifiers property TpKey toInt)
	lazy val at = modifiers property AtFkKey
	lazy val pa = modifiers property PaKey
	lazy val fk = modifiers property AtFkKey
	lazy val aw = modifiers property AwKey
	lazy val ini = modifiers property IniKey

  def subdual: Boolean

  import AttackMode._

  def modifiers_= (ms: Modifiers): AttackMode = this match {
    case MeleeSingle(a, b, c, _) ⇒ MeleeSingle(a, b, c, ms)
    case MeleeShield(a, b, c, _) ⇒ MeleeShield(a, b, c, ms)
    case MeleeTwoHanded(a, b, _) ⇒ MeleeTwoHanded(a, b, ms)
    case Throwing(a, b, c, _) ⇒ Throwing(a, b, c, ms)
    case Shooting(a, b, c, _) ⇒ Shooting(a, b, c, ms)
    case Unarmed(a, _) ⇒ Unarmed(a, ms)
  }
}

sealed trait MeleeMode extends AttackMode {
  def isMelee = true
  def weapon: MeleeWeapon
  def talent = weapon.data.talent

  lazy val name = if (subdual) "%s (%s)" format (weapon.name, loc.subdual) 
                  else weapon.name

  def baseTp: DieRoller = weapon.data.tp
}

object AttackMode {
  val modifiers: AttackMode @> Modifiers =
    Lens.lensu(_ modifiers_= _, _.modifiers)

  case class MeleeSingle(
    weapon: MeleeWeapon,
    subdual: Boolean,
    wrongHand: Boolean,
    modifiers: Modifiers
  ) extends MeleeMode

  case class MeleeShield(
    weapon: MeleeWeapon,
    shield: Shield,
    subdual: Boolean,
    modifiers: Modifiers
  ) extends MeleeMode

  case class MeleeTwoHanded(
    weapon: MeleeWeapon,
    subdual: Boolean,
    modifiers: Modifiers
  ) extends MeleeMode

  case class Throwing (
    weapon: RangedWeapon,
    subdual: Boolean,
    wrongHand: Boolean,
    modifiers: Modifiers
  ) extends AttackMode {
    def isMelee = false
    def name = weapon.name
    def talent = weapon.data.talent
    def baseTp = weapon.data.tp
  }

  case class Shooting (
    weapon: RangedWeapon,
    ammo: Ammunition,
    subdual: Boolean,
    modifiers: Modifiers
  ) extends AttackMode {
    def isMelee = false
    def name = weapon.name
    def talent = weapon.data.talent
    def baseTp = ammo.data.tp
  }

  case class Unarmed (name: String, modifiers: Modifiers) extends AttackMode {
    def isMelee = true
    def baseTp = DieRoller.default
    def subdual = true
    def talent = name
  }

	def fromHands (hs: Hands): List[AttackMode] = {
	  import Hands._, Hand._

    val ft = List(false, true)

    def e: Modifiers = Modifiers.empty

		def res: List[AttackMode] = hs match {
      case Hands.Empty ⇒ List(Unarmed(loc.raufen, e), Unarmed(loc.ringen, e))
      case OneHanded(Shield(s), Melee(w)) ⇒ ft ∘ (MeleeShield(w, s, _, e))
      case OneHanded(x, Melee(w)) ⇒ ft ∘ (MeleeSingle(w, _, false, e))
      case OneHanded(Melee(w), x) ⇒ ft ∘ (MeleeSingle(w, _, true, e)) 
      case OneHanded(Ammo(a), Ranged(r)) if (r.item.usesAmmo) ⇒ 
				List(Shooting(r, a, false, e)) 
      case OneHanded(x, Ranged(r)) if (!r.item.usesAmmo) ⇒ 
				List(Throwing(r, false, false, e))
      case OneHanded(Ranged(r), x) if (!r.item.usesAmmo) ⇒ 
				List(Throwing(r, false, true, e))
			case TwoHanded(w) => ft ∘ (MeleeTwoHanded(w, _, e))
      case _ ⇒ Nil
		} 

		def adjustTp(a: AttackMode): AttackMode = a.baseTp.plus match {
      case 0 ⇒ a
      case x ⇒ modifiers.add(TpKey, Modifier(loc.base, x)) exec a
		} 

		res ∘ adjustTp
	}

  implicit lazy val AttackModeEqual = Equal.equalA[AttackMode]

  implicit lazy val AttackModeModified = new Modified[AttackMode] {
    val modifiersL =
      Lens.lensu[AttackMode,Modifiers](_ modifiers_= _, _.modifiers)
  }
}

trait AttackModeFunctions extends ModifiedFunctions {
  def addAt (n: String, v: Long): State[AttackMode,Unit] =
    oModAddS (n, v, AtFkKey)

  def addAw (n: String, v: Long): State[AttackMode,Unit] =
    oModAddS (n, v, AwKey)

  def addFk (n: String, v: Long): State[AttackMode,Unit] =
    oModAddS (n, v, AtFkKey)

  def addIni (n: String, v: Long): State[AttackMode,Unit] =
    oModAddS (n, v, IniKey)

  def addPa (n: String, v: Long): State[AttackMode,Unit] =
    oModAddS (n, v, PaKey)

  def addTp (n: String, v: Long): State[AttackMode,Unit] =
    oModAddS (n, v, TpKey)
}

// vim: set ts=2 sw=2 et:
