package efa.dsa.world

import efa.core._, Efa._
import efa.rpg.core.RangeVals
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class Wm (at: Int, pa: Int) {
  require (Wm.wm validate at isRight)
  require (Wm.wm validate pa isRight)

  override def toString = "%d/%d" format (at, pa)
}

object Wm extends RangeVals {
  val wm  = genInfo (-99, 99)
  
  private[this] val wmR = "(-?[0-9]+)/(-?[0-9]+)".r

  implicit val WmEqual: Equal[Wm] = Equal.equalA

  implicit val WmShow = Show.shows[Wm](_.toString)

  implicit val WmRead = Read.readV[Wm] { _ match {
      case wmR (at, pa) ⇒ ^(wm.read(at), wm.read(pa))(Wm.apply)
      case _ ⇒ efa.dsa.world.loc.invalidWm.failureNel
    }
  }

  implicit val WmToXml = TaggedToXml.readShow[Wm]("wm")

  implicit val WmArbitrary =
    Arbitrary (^(wm.gen, wm.gen)(Wm.apply))

  implicit val WmDefault = Default default Wm(0, 0)

  // Lenses
  val at: Wm @> Int = Lens.lensu((a,b) ⇒ a.copy(at = b), _.at)
  val pa: Wm @> Int = Lens.lensu((a,b) ⇒ a.copy(pa = b), _.pa)

  implicit class WmLenses[A](val l: A @> Wm) extends AnyVal {
    def at = l >=> Wm.at
    def pa = l >=> Wm.pa
  }
    
}

// vim: set ts=2 sw=2 et:
