package efa.dsa.being.equipment

import efa.core.{Efa, TaggedToXml}, Efa._
import efa.dsa.equipment.EquipmentItemData
import efa.dsa.world.{Be, Rs}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class ArmorData(
  eData: EquipmentItemData,
  parentId: Int,
  rs: Int,
  be: Int,
  equipped: Boolean
) extends EquipmentLike[ArmorData] {
  require(Rs validate rs isRight)
  require(Be validate be isRight)

  def eData_= (v: EquipmentItemData) = copy (eData = v)

  def parentId_= (v: Int) = copy (parentId = v)
}

object ArmorData extends EquipmentLikes[ArmorData] {
  lazy val default = ArmorData(eData(_.armor), 0, 0, 0, false)

  implicit lazy val ArmorDataArbitrary = Arbitrary (
    ^^^^(eDataGen, parentIdGen, Rs.gen, Be.gen, a[Boolean])(ArmorData.apply)
  )

  implicit lazy val ArmorDataToXml = new TaggedToXml[ArmorData] {
    val tag = "dsa_armor"

    def fromXml (ns: Seq[Node]) =
      ^^^^(readEData (ns),
        readParentId (ns),
        Rs read ns,
        Be read ns,
        Equipped read ns)(ArmorData.apply)

    def toXml (a: ArmorData) = 
      eDataNodes(a) ++
      Rs.write(a.rs) ++
      Be.write(a.be) ++
      Equipped.write(a.equipped)
  }

  val eData: ArmorData @> EquipmentItemData =
    Lens.lensu((a,b) ⇒ a.copy(eData = b), _.eData)

  val parentId: ArmorData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val rs: ArmorData @> Int = Lens.lensu((a,b) ⇒ a.copy(rs = b), _.rs)

  val be: ArmorData @> Int = Lens.lensu((a,b) ⇒ a.copy(be = b), _.be)

  val equipped: ArmorData @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(equipped = b), _.equipped)
}

// vim: set ts=2 sw=2 et:
