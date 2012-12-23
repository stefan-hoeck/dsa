package efa.dsa.abilities

import efa.rpg.core.ItemData
import efa.core.{ToXml, Efa}, Efa._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck._, Prop._
import efa.dsa.world.{RaisingCost, Ebe}

case class SpellItem (
  data: ItemData,
  raisingCost: RaisingCost,
  attributes: Attributes
) extends SkillItemLike[SpellItem] {
  def data_= (v: ItemData) = copy (data = v)
}

object SpellItem extends SkillItemLikes[SpellItem] {
  lazy val default = SpellItem (ItemData(loc.spell), !!, !!)

  def shortDesc (i: SpellItem) = {
    def rcTag = (loc.raisingCost, i.raisingCost.toString)
    def attributesTag = (loc.attributes, i.attributes.shows)

    tagShortDesc (i, attributesTag, rcTag)
  }

  //type classes
  implicit lazy val SpellItemToXml = new ToXml[SpellItem] {
    def fromXml (ns: Seq[Node]) =
      ^^(readData (ns),
        ns.tagged[RaisingCost],
        ns.tagged[Attributes])(SpellItem.apply)

    def toXml (a: SpellItem) =
      dataToNode (a) ++ Efa.toXml(a.raisingCost) ++ Efa.toXml(a.attributes)
  }

  implicit lazy val SpellItemArbitrary = Arbitrary (
    ^^(a[ItemData], a[RaisingCost], a[Attributes])(SpellItem.apply)
  )

  val raisingCost: SpellItem @> RaisingCost =
    Lens.lensu((a,b) ⇒ a.copy(raisingCost = b), _.raisingCost)

  val attributes: SpellItem @> Attributes =
    Lens.lensu((a,b) ⇒ a.copy(attributes = b), _.attributes)
}

// vim: set ts=2 sw=2 et:
