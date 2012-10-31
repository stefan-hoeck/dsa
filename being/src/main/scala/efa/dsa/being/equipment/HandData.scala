package efa.dsa.being.equipment

import efa.core.{Efa, ValRes, Read, ToXml}, Efa._
import org.scalacheck.{Arbitrary, Prop, Gen}, Arbitrary.arbitrary
import scalaz._, Scalaz._

sealed trait HandData {
  def toHand (es: Equipments): Option[Hand]
}

object HandData {
  case object Empty extends HandData {
    def toHand (es: Equipments): Option[Hand] = Some(Hand.Empty)
  }

  case class Melee(id: Int) extends HandData {
    override def toString = "Melee" + id
    def toHand (es: Equipments): Option[Hand] =
      es.meleeWeapons get id map Hand.Melee
  }

  case class Ranged(id: Int) extends HandData {
    override def toString = "Ranged" + id
    def toHand (es: Equipments): Option[Hand] =
      es.rangedWeapons get id map Hand.Ranged
  }

  case class Shield(id: Int) extends HandData {
    override def toString = "Shield" + id
    def toHand (es: Equipments): Option[Hand] =
      es.shields get id map Hand.Shield
  }

  case class Ammo(id: Int) extends HandData {
    override def toString = "Ammo" + id
    def toHand (es: Equipments): Option[Hand] =
      es.ammunition get id map Hand.Ammo
  }

  private val idR = "(-?\\d+)"
  private val EmptyR = "Empty".r
  private val MeleeR = "Melee" + idR r
  private val RangedR = "Ranged" + idR r
  private val ShieldR = "Shield" + idR r
  private val AmmoR = "Ammo" + idR r

  def fromString (s: String): ValRes[HandData] = s match {
    case EmptyR() ⇒ Empty.success
    case MeleeR(i) ⇒ i.read[Int] map Melee
    case RangedR(i) ⇒ i.read[Int] map Ranged
    case ShieldR(i) ⇒ i.read[Int] map Shield
    case AmmoR(i) ⇒ i.read[Int] map Ammo
    case _ ⇒ efa.dsa.being.loc.unknownHandString(s).failureNel
  }

  private lazy val idGen = arbitrary[Int]

  lazy val emptyGen: Gen[HandData] = Gen value Empty
  lazy val meleeGen: Gen[HandData] = idGen map Melee
  lazy val rangedGen: Gen[HandData] = idGen map Ranged
  lazy val shieldGen: Gen[HandData] = idGen map Shield
  lazy val ammoGen: Gen[HandData] = idGen map Ammo

  implicit lazy val HandDataEqual: Equal[HandData] = Equal.equalA

  implicit lazy val HandDataArbitrary = Arbitrary(
    Gen frequency (
      (1, emptyGen),
      (3, meleeGen),
      (3, rangedGen),
      (3, shieldGen),
      (3, ammoGen)
    )
  )

  implicit lazy val HandDataShow: Show[HandData] = Show shows (_.toString)

  implicit lazy val HandDataRead: Read[HandData] = Read readV fromString

  implicit lazy val HandDataToXml: ToXml[HandData] = ToXml.readShow
}

// vim: set ts=2 sw=2 et:
