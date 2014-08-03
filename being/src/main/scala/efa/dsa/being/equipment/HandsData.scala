package efa.dsa.being.equipment

import efa.core._, Efa._
import efa.core.syntax.{string, nodeSeq}
import org.scalacheck.{Arbitrary, Prop, Gen}, Arbitrary.arbitrary
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

sealed trait HandsData {
  def left: HandData
  def right: HandData
  def toHands (es: Equipments): Option[Hands]

  def setLeft (h: HandData): HandsData = toHD (h, right)

  def setRight (h: HandData): HandsData = toHD (left, h)

  import HandData._

  private def toHD (l: HandData, r: HandData): HandsData = (l,r) match {
    case (Empty, Empty)                ⇒ HandsData.Empty 
    case (Melee(x), Melee(y)) if x ≟ y ⇒ HandsData.TwoHanded(x)
    case (a,b)                         ⇒ HandsData.OneHanded(a,b)
  }
}

object HandsData {
  
  case object Empty extends HandsData {
    def left = HandData.Empty
    def right = HandData.Empty
    def toHands (es: Equipments): Option[Hands] = Some(Hands.Empty)
  }
  
  case class OneHanded(left: HandData, right: HandData) extends HandsData {
    override def toString = "OneHanded:%s,%s" format (left, right)
    def toHands (es: Equipments): Option[Hands] = 
      ^(left toHand es, right toHand es)(Hands.OneHanded)
  }

  case class TwoHanded(id: Int) extends HandsData {
    def left = HandData.Melee(id)
    def right = HandData.Melee(id)

    def toHands (es: Equipments): Option[Hands] = 
      es.meleeWeapons get id map Hands.TwoHanded

    override def toString = "TwoHanded%d" format id
  }

  private val handR = "([A-Za-z0-9-]+)"
  private val EmptyR = "Empty".r
  private val OneHandedR = "OneHanded:%s,%s" format (handR, handR) r
  private val TwoHandedR = "TwoHanded(-?\\d+)".r

  def fromString (s: String): ValRes[HandsData] = s match {
    case EmptyR() ⇒ Empty.success
    case OneHandedR(l, r) ⇒ ^(l.read[HandData], r.read[HandData])(OneHanded)
    case TwoHandedR(i) ⇒ i.read[Int] map TwoHanded
    case _ ⇒ efa.dsa.being.loc.unknownHandString(s).failureNel
  }

  private lazy val idGen = arbitrary[Int]
  private lazy val handGen = arbitrary[HandData]

  lazy val emptyGen: Gen[HandsData] = Gen const Empty
  lazy val oneHandedGen: Gen[HandsData] = ^(handGen, handGen)(OneHanded)
  lazy val twoHandedGen: Gen[HandsData] = idGen map TwoHanded

  implicit lazy val HandsDataDefault: Default[HandsData] =
    Default default Empty

  implicit lazy val HandsDataEqual = Equal.equalA[HandsData]

  implicit lazy val HandsDataArbitrary = Arbitrary(
    Gen frequency ((1, emptyGen), (3, oneHandedGen), (3, twoHandedGen))
  )

  implicit lazy val HandsDataShow: Show[HandsData] = Show.shows(_.toString)

  implicit lazy val HandsDataRead: Read[HandsData] = Read readV fromString

  implicit lazy val HandsDataToXml: TaggedToXml[HandsData] =
    TaggedToXml readShow ("hands")
}

// vim: set ts=2 sw=2 et:
