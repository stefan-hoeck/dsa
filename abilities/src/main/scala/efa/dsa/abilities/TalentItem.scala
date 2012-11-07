package efa.dsa.abilities

import efa.rpg.core.ItemData
import efa.core.{ToXml, Efa}, Efa._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck._, Prop._
import efa.dsa.world.{RaisingCost, Ebe}

case class TalentItem (
  data: ItemData,
  attributes: Attributes,
  ebe: Ebe,
  raisingCost: RaisingCost,
  isBaseTalent: Boolean,
  talentType: TalentType
) extends SkillItemLike[TalentItem] {
  def data_= (v: ItemData) = copy (data = v)
}

object TalentItem extends SkillItemLikes[TalentItem] {
  lazy val default =
    TalentItem (ItemData(loc.talent), !!, !!, !!, false, !!)

  def shortDesc (i: TalentItem) = {
    def ebeTag = (loc.ebe, i.ebe.toString)
    def rcTag = (loc.raisingCost, i.raisingCost.toString)
    def attributesTag = (loc.attributes, i.attributes.shows)
    def talentTypeTag = (loc.talentType, i.talentType.toString)

    tagShortDesc (i, attributesTag, ebeTag, rcTag, talentTypeTag)
  }

  //type classes
  implicit lazy val TalentItemToXml = new ToXml[TalentItem] {
    def fromXml (ns: Seq[Node]) = 
      ^^^^^(readData (ns),
        ns.tagged[Attributes],
        ns.tagged[Ebe],
        ns.tagged[RaisingCost],
        BaseTalent.read(ns),
        ns.tagged[TalentType])(TalentItem.apply)

    def toXml (a: TalentItem) =
      dataToNode (a) ++
      xml(a.attributes) ++
      xml(a.ebe) ++
      xml(a.raisingCost) ++
      BaseTalent.write(a.isBaseTalent) ++
      xml(a.talentType)
  }

  implicit lazy val TalentItemArbitrary = Arbitrary (
    ^^^^^(a[ItemData],
      a[Attributes],
      a[Ebe],
      a[RaisingCost],
      a[Boolean],
      a[TalentType])(TalentItem.apply)
  )

  val ebe: TalentItem @> Ebe =
    Lens.lensu((a,b) ⇒ a.copy(ebe = b), _.ebe)

  val raisingCost: TalentItem @> RaisingCost =
    Lens.lensu((a,b) ⇒ a.copy(raisingCost = b), _.raisingCost)

  val isBaseTalent: TalentItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(isBaseTalent = b), _.isBaseTalent)

  val talentType: TalentItem @> TalentType =
    Lens.lensu((a,b) ⇒ a.copy(talentType = b), _.talentType)
    
  val attributes: TalentItem @> Attributes =
    Lens.lensu((a,b) ⇒ a.copy(attributes = b), _.attributes)
}

// vim: set ts=2 sw=2 et:
