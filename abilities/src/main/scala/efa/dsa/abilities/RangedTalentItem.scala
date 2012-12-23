package efa.dsa.abilities

import efa.dsa.world.{RaisingCost, Ebe}
import efa.rpg.core.ItemData
import efa.core.{ToXml, Efa}, Efa._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.Arbitrary

case class RangedTalentItem (
  data: ItemData,
  ebe: Ebe,
  raisingCost: RaisingCost,
  isBaseTalent: Boolean
) extends SkillItemLike[RangedTalentItem] {
  def data_= (v: ItemData) = copy (data = v)
}

object RangedTalentItem extends SkillItemLikes[RangedTalentItem] {
  lazy val default =
    RangedTalentItem (ItemData(loc.rangedTalent), !!, !!, false)

  def shortDesc (i: RangedTalentItem) = {
    def ebeTag = (loc.ebe, i.ebe.toString)
    def rcTag = (loc.raisingCost, i.raisingCost.toString)

    tagShortDesc (i, ebeTag, rcTag)
  }

  //type classes
  implicit lazy val RangedTalentItemToXml = new ToXml[RangedTalentItem] {
    def fromXml (ns: Seq[Node]) =
      ^^^(readData (ns),
        ns.tagged[Ebe],
        ns.tagged[RaisingCost],
        BaseTalent.read(ns))(RangedTalentItem.apply)

    def toXml (a: RangedTalentItem) =
      dataToNode (a) ++
      Efa.toXml(a.ebe) ++
      Efa.toXml( a.raisingCost) ++
      BaseTalent.write(a.isBaseTalent)
  }

  implicit lazy val RangedTalentItemArbitrary = Arbitrary (
    ^^^(a[ItemData], a[Ebe], a[RaisingCost], a[Boolean])(
      RangedTalentItem.apply)
  )

  val ebe: RangedTalentItem @> Ebe =
    Lens.lensu((a,b) ⇒ a.copy(ebe = b), _.ebe)

  val raisingCost: RangedTalentItem @> RaisingCost =
    Lens.lensu((a,b) ⇒ a.copy(raisingCost = b), _.raisingCost)

  val isBaseTalent: RangedTalentItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(isBaseTalent = b), _.isBaseTalent)
}

// vim: set ts=2 sw=2 et:
