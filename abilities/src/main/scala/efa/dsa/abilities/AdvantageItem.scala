package efa.dsa.abilities

import efa.rpg.core.{RpgItemLike, RpgItemLikes, ItemData}
import efa.core.{ToXml, Efa}, Efa._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.Arbitrary

case class AdvantageItem(data: ItemData, gp: Int) 
   extends RpgItemLike[AdvantageItem] {
  require (Gp validate gp isRight)

  def data_= (v: ItemData) = copy (data = v)
}

object AdvantageItem extends RpgItemLikes[AdvantageItem] {
  override lazy val default = AdvantageItem(ItemData(loc.advantage), 0)

  override def shortDesc (i: AdvantageItem) = {
    def gpTag = (loc.gp, i.gp.toString)
    tagShortDesc (i, gpTag)
  }

  implicit lazy val AdvantageItemToXml = new ToXml[AdvantageItem] {
    def fromXml (ns: Seq[Node]) =
      ^(readData (ns), Gp read ns)(AdvantageItem.apply)

    def toXml (a: AdvantageItem) = dataToNode (a) ++ Gp.write(a.gp)
  }

  implicit lazy val AdvantageItemArbitrary =
    Arbitrary (^(a[ItemData], Gp.gen)(AdvantageItem.apply))

  implicit lazy val AdvantageItemEqual = Equal.equalA[AdvantageItem]

  val gp: AdvantageItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(gp = b), _.gp)
  
}

// vim: set ts=2 sw=2 et:
