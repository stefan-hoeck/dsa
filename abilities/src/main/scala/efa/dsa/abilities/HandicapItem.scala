package efa.dsa.abilities

import efa.rpg.core.{RpgItemLikes, ItemData, RpgItemLike}
import efa.core.{ToXml, Efa}, Efa._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import org.scalacheck.Arbitrary
import scala.xml.Node

case class HandicapItem (data: ItemData, gp: HandicapGp)
   extends RpgItemLike[HandicapItem] {
   def data_= (v: ItemData) = copy (data = v)
}

object HandicapItem extends RpgItemLikes[HandicapItem] {
  lazy val default = HandicapItem (ItemData(loc.handicap), !!)

  def shortDesc (i: HandicapItem) = {
    def gpTag = (loc.gp, i.gp.toString)
    tagShortDesc (i, gpTag)
  }

  implicit lazy val HandicapItemToXml = new ToXml[HandicapItem] {
    def fromXml (ns: Seq[Node]) =
      ^(readData (ns), ns.tagged[HandicapGp])(HandicapItem.apply)

    def toXml (a: HandicapItem) = dataToNode (a) ++ xml(a.gp)
  }

  implicit lazy val HandicapItemArbitrary =
    Arbitrary (^(a[ItemData], a[HandicapGp])(HandicapItem.apply))

  val gp: HandicapItem @> HandicapGp =
    Lens.lensu((a,b) ⇒ a.copy(gp = b), _.gp)
}

// vim: set ts=2 sw=2 et:
