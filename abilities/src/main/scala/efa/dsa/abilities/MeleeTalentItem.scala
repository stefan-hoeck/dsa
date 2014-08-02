package efa.dsa.abilities

import efa.core.{ToXml, Efa}, Efa._
import efa.core.syntax.{string, nodeSeq}
import efa.dsa.world.{RaisingCost, Ebe}
import efa.rpg.core.ItemData
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.Arbitrary, Arbitrary.arbitrary

case class MeleeTalentItem (
  data: ItemData,
  ebe: Ebe,
  raisingCost: RaisingCost,
  isBaseTalent: Boolean
) extends SkillItemLike[MeleeTalentItem] {
  def data_= (v: ItemData) = copy (data = v)
}

object MeleeTalentItem extends SkillItemLikes[MeleeTalentItem] {
  lazy val default =
    MeleeTalentItem (ItemData(loc.meleeTalent), !!, !!, false)

  def shortDesc (i: MeleeTalentItem) = {
    def ebeTag = (loc.ebe, i.ebe.toString)
    def rcTag = (loc.raisingCost, i.raisingCost.toString)

    tagShortDesc (i, ebeTag, rcTag)
  }

  //type classes
  implicit lazy val MeleeTalentItemToXml = new ToXml[MeleeTalentItem] {
    def fromXml (ns: Seq[Node]) =
      ^^^(readData (ns),
        ns.tagged[Ebe],
        ns.tagged[RaisingCost],
        BaseTalent.read(ns))(MeleeTalentItem.apply)

    def toXml (a: MeleeTalentItem) =
      dataToNode(a) ++
      Efa.toXml(a.ebe) ++
      Efa.toXml(a.raisingCost) ++
      BaseTalent.write(a.isBaseTalent)
  }

  implicit lazy val MeleeTalentItemArbitrary = Arbitrary (
    ^^^(a[ItemData], a[Ebe], a[RaisingCost], a[Boolean])(
      MeleeTalentItem.apply)
  )

  val ebe: MeleeTalentItem @> Ebe =
    Lens.lensu((a,b) ⇒ a.copy(ebe = b), _.ebe)

  val raisingCost: MeleeTalentItem @> RaisingCost =
    Lens.lensu((a,b) ⇒ a.copy(raisingCost = b), _.raisingCost)

  val isBaseTalent: MeleeTalentItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(isBaseTalent = b), _.isBaseTalent)
}

// vim: set ts=2 sw=2 et:
