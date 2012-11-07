package efa.dsa.abilities

import efa.rpg.core.{RpgItemLikes, RpgItemLike, ItemData}
import efa.core.{ToXml, Efa}, Efa._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node

case class FeatItem (data: ItemData, ap: Int, featType: String)
   extends RpgItemLike[FeatItem] {
  require (Ap validate ap isRight)
  def data_= (v: ItemData) = copy (data = v)
}

object FeatItem extends RpgItemLikes[FeatItem] {
  lazy val default = FeatItem (ItemData(loc.feat), 0, "")

  def shortDesc (i: FeatItem) = {
    def apTag = (loc.ap, i.ap.toString)
    def featTypeTag = (loc.featType, i.featType.toString)

    tagShortDesc (i, apTag, featTypeTag)
  }

  implicit lazy val FeatItemToXml = new ToXml[FeatItem] {

    def fromXml (ns: Seq[Node]) =
      ^^(readData (ns),
        Ap.read (ns),
        ns.readTag[String]("featType"))(FeatItem.apply)

    def toXml (a: FeatItem) =
      dataToNode (a) ++ Ap.write(a.ap) ++ ("featType" xml a.featType)
  }

  implicit lazy val FeatItemArbitrary =
    Arbitrary (^^(a[ItemData], Ap.gen, Gen.identifier)(FeatItem.apply))

  val ap: FeatItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(ap = b), _.ap)

  val featType: FeatItem @> String =
    Lens.lensu((a,b) ⇒ a.copy(featType = b), _.featType)
}

// vim: set ts=2 sw=2 et:
