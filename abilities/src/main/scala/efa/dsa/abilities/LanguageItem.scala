package efa.dsa.abilities

import efa.core.{ToXml, Efa}, Efa._
import efa.core.syntax.{string, nodeSeq}
import efa.dsa.world.RaisingCost
import efa.rpg.core.ItemData
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class LanguageItem (
  data: ItemData,
  raisingCost: RaisingCost,
  complexity: Int,
  scripture: String,
  family: String
) extends SkillItemLike[LanguageItem] {
  require (Complexity validate complexity isRight)
  
  def data_= (v: ItemData) = copy (data = v)
}

object LanguageItem extends SkillItemLikes[LanguageItem] {
  lazy val default = LanguageItem (ItemData(loc.language), !!, 0, "", "")

  def shortDesc (i: LanguageItem) = {
    def rcTag = (loc.raisingCost, i.raisingCost.toString)
    def complexityTag = (loc.complexity, i.complexity.toString)
    def scriptureTag = (loc.scripture, i.scripture)
    def familyTag = (loc.family, i.family)

    tagShortDesc (i, rcTag, complexityTag, scriptureTag, familyTag)
  }

  //type classes
  implicit lazy val LanguageItemToXml = new ToXml[LanguageItem] {
    def fromXml (ns: Seq[Node]) =
      ^^^^(readData (ns),
        ns.tagged[RaisingCost],
        Complexity.read(ns),
        ns.readTag[String]("scripture"),
        ns.readTag[String]("family"))(LanguageItem.apply)

    def toXml (a: LanguageItem) =
      dataToNode (a) ++
      Efa.toXml(a.raisingCost) ++
      Complexity.write(a.complexity) ++
      ("scripture" xml a.scripture) ++
      ("family" xml a.family)
  }

  implicit lazy val LanguageItemArbitrary = Arbitrary (
    ^^^^(a[ItemData],
      a[RaisingCost],
      Complexity.gen,
      Gen.identifier,
      Gen.identifier)(LanguageItem.apply)
  )

  val raisingCost: LanguageItem @> RaisingCost =
    Lens.lensu((a,b) ⇒ a.copy(raisingCost = b), _.raisingCost)

  val complexity: LanguageItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(complexity = b), _.complexity)
  
  val scripture: LanguageItem @> String =
    Lens.lensu((a,b) ⇒ a.copy(scripture = b), _.scripture)

  val family: LanguageItem @> String =
    Lens.lensu((a,b) ⇒ a.copy(family = b), _.family)
}

// vim: set ts=2 sw=2 et:
