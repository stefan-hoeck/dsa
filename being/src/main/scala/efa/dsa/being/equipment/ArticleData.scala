package efa.dsa.being.equipment

import efa.core.{ToXml, Efa}, Efa._
import efa.dsa.equipment.{EquipmentItemData}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class ArticleData(
  eData: EquipmentItemData,
  parentId: Int,
  count: Int
) extends EquipmentLike[ArticleData] {
  require(Count validate count isRight)

  def eData_= (v: EquipmentItemData) = copy (eData = v)

  def parentId_= (v: Int) = copy (parentId = v)

  override lazy val fullPrice = count * price
  override lazy val fullWeight = count * weight
}

object ArticleData extends EquipmentLikes[ArticleData] {
  lazy val default = ArticleData(eData(_.equipment), 0, 1)

  implicit lazy val ArticleDataArbitrary = Arbitrary (
    ^^(eDataGen, parentIdGen, Count.gen)(ArticleData.apply)
  )

  implicit lazy val ArticleDataToXml = new ToXml[ArticleData] {
    def fromXml (ns: Seq[Node]) =
      ^^(readEData (ns), readParentId (ns), Count read ns)(ArticleData.apply)

    def toXml (a: ArticleData) = 
      eDataNodes(a) ++ Count.write(a.count)
  }

  val eData: ArticleData @> EquipmentItemData =
    Lens.lensu((a,b) ⇒ a.copy(eData = b), _.eData)

  val parentId: ArticleData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val count: ArticleData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(count = b), _.count)
}

// vim: set ts=2 sw=2 et:
