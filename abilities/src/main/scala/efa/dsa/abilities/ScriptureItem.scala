package efa.dsa.abilities

import efa.rpg.core.ItemData
import efa.core.{ToXml, Efa}, Efa._
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.Arbitrary
import efa.dsa.world.RaisingCost

case class ScriptureItem (
  data: ItemData,
  raisingCost: RaisingCost,
  complexity: Int
) extends SkillItemLike[ScriptureItem] {
  require (Complexity validate complexity isRight)

  def data_= (v: ItemData) = copy (data = v)
}

object ScriptureItem extends SkillItemLikes[ScriptureItem] {
  lazy val default = ScriptureItem (ItemData(loc.scripture), !!, 0)

  def shortDesc (i: ScriptureItem) = {
    def rcTag = (loc.raisingCost, i.raisingCost.toString)
    def complexityTag = (loc.complexity, i.complexity.toString)

    tagShortDesc (i, rcTag, complexityTag)
  }

  //type classes
  implicit lazy val ScriptureItemToXml = new ToXml[ScriptureItem] {
    def fromXml (ns: Seq[Node]) =
      ^^(readData (ns),
        ns.tagged[RaisingCost],
        Complexity.read(ns))(ScriptureItem.apply)

    def toXml (a: ScriptureItem) =
      dataToNode(a) ++
      Efa.toXml(a.raisingCost) ++
      Complexity.write(a.complexity)
  }

  implicit lazy val ScriptureItemArbitrary = Arbitrary (
    ^^(a[ItemData], a[RaisingCost], Complexity.gen)(ScriptureItem.apply)
  )

  val raisingCost: ScriptureItem @> RaisingCost =
    Lens.lensu((a,b) ⇒ a.copy(raisingCost = b), _.raisingCost)

  val complexity: ScriptureItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(complexity = b), _.complexity)
}

// vim: set ts=2 sw=2 et:
