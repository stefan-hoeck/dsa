package efa.dsa.world

import efa.core._, Efa._
import efa.rpg.core.RangeVals
import org.scalacheck._, Prop._
import scala.xml.Node
import scalaz._, Scalaz._

sealed abstract class Ebe {
  def calcEbe (be: Long): Long 
}

object Ebe extends RangeVals {
  private[this] val noEbeR = "\\-".r
  private[this] val addR = "(0|\\-[0-9]+)".r
  private[this] val factorR = "x([0-9]+)".r

  val add = genInfo (-10L, 0L)
  val fac = genInfo (1L, 3L)

  case object NoEbe extends Ebe {
    override def toString = "-"
    override def calcEbe (be: Long) = 0L
  }

  case class Add(add: Long) extends Ebe {
    require (Ebe.add validate add isRight)
    override def toString = add.toString
    override def calcEbe (be: Long) = (be + add) max 0
  }

  case class Factor(f: Long) extends Ebe {
    require (fac validate f isRight)
    override def toString = "x" + f.toString
    override def calcEbe (be: Long) = be * f
  }

  implicit val EbeEqual = Equal.equalA[Ebe]

  implicit val EbeShow = Show.shows[Ebe](_.toString)

  implicit val EbeRead = Read.readV[Ebe] { _ match {
      case noEbeR () ⇒ NoEbe.success
      case addR (x) ⇒ add.read(x) map Add.apply
      case factorR (x) ⇒ fac.read(x) map Factor.apply
      case _ ⇒ loc.invalidEbe.failureNel
    }
  }

  implicit val EbeToXml = TaggedToXml.readShow[Ebe]("ebe")

  implicit val EbeArbitrary: Arbitrary[Ebe] = {
    val noEbeGen = Gen value NoEbe
    val addGen = add.gen map Add.apply
    val factorGen = fac.gen map Factor.apply

    Arbitrary[Ebe] (Gen.oneOf[Ebe] (noEbeGen, addGen, factorGen))
  }

  implicit val EbeDefault = Default.default[Ebe] (NoEbe)
}

// vim: set ts=2 sw=2 et:
