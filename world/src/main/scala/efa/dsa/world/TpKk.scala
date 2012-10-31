package efa.dsa.world

import efa.core._, Efa._
import efa.rpg.core.RangeVals
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class TpKk (tp: Int, kk: Int) {
  require (TpKk.tpkk validate tp isRight)
  require (TpKk.tpkk validate kk isRight)

  override def toString = "%d/%d" format (tp, kk)
}

object TpKk extends RangeVals {
  val tpkk  = genInfo (0, 999)
  
  private[this] val tpkkR = "(-?[0-9]+)/(-?[0-9]+)".r

  implicit val TpKkEqual: Equal[TpKk] = Equal.equalA

  implicit val TpKkShow = Show.shows[TpKk](_.toString)

  implicit val TpKkRead = Read.readV[TpKk] { _ match {
      case tpkkR (at, pa) ⇒ ^(tpkk.read(at), tpkk.read(pa))(TpKk.apply)
      case _ ⇒ efa.dsa.world.loc.invalidTpKk.failureNel
    }
  }

  implicit val TpKkToXml = TaggedToXml.readShow[TpKk]("tpkk")

  implicit val TpKkArbitrary =
    Arbitrary (^(tpkk.gen, tpkk.gen)(TpKk.apply))

  implicit lazy val TpKkDefault = Default default TpKk(0, 0)

  // Lenses
  val tp: TpKk @> Int = Lens.lensu((a,b) ⇒ a.copy(tp = b), _.tp)
  val kk: TpKk @> Int = Lens.lensu((a,b) ⇒ a.copy(kk = b), _.kk)

  implicit def TpKkLens[A](l: A @> TpKk) = new {
    lazy val tp = l >=> TpKk.tp
    lazy val kk = l >=> TpKk.kk
  }
    
}

// vim: set ts=2 sw=2 et:
