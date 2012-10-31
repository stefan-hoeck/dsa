package efa.dsa.abilities

import efa.rpg.core.ItemData
import efa.core.{ToXml, Efa}, Efa._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.Arbitrary
import efa.dsa.world.{RaisingCost, Ebe}

case class RitualItem (
  data: ItemData,
  raisingCost: RaisingCost
) extends SkillItemLike[RitualItem] {
  def data_= (v: ItemData) = copy (data = v)
}

object RitualItem extends SkillItemLikes[RitualItem] {
  lazy val default = RitualItem (ItemData(loc.ritual), !!)

  def shortDesc (i: RitualItem) = {
    def rcTag = (loc.raisingCost, i.raisingCost.toString)

    tagShortDesc (i, rcTag)
  }

  //type classes
  implicit lazy val RitualItemToXml = new ToXml[RitualItem] {
    def fromXml (ns: Seq[Node]) =
      ^(readData (ns), ns.tagged[RaisingCost])(RitualItem.apply)

    def toXml (a: RitualItem) =
      dataToNode (a) ++ xml(a.raisingCost)
  }

  implicit lazy val RitualItemArbitrary =
    Arbitrary (^(a[ItemData], a[RaisingCost])(RitualItem.apply))

  val raisingCost: RitualItem @> RaisingCost =
    Lens.lensu((a,b) ⇒ a.copy(raisingCost = b), _.raisingCost)
}

// vim: set ts=2 sw=2 et:
