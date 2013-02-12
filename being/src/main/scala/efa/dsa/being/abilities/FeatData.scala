package efa.dsa.being.abilities

import efa.core.{Efa, TaggedToXml, Default, UniqueIdL}, Efa._
import efa.rpg.core.Util
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class FeatData (
  parentId: Int,
  name: String,
  isActive: Boolean,
  desc: String
)

object FeatData extends Util {
  val default = FeatData(0, "", true, "")

  implicit val FeatDataEqual = Equal.equalA[FeatData]

  implicit val FeatDataArbitrary = Arbitrary(
    ^^^(a[Int], Gen.identifier, a[Boolean], Gen.identifier)(FeatData.apply)
  )

  implicit lazy val FeatDataToXml = new TaggedToXml[FeatData] {
    val tag = "dsa_feat"

    def fromXml (ns: Seq[Node]) =
      ^^^(ns.readTag[Int]("parentId"),
        ns.readTag[String]("name"),
        ns.readTag[Boolean]("active"),
        ns.readTag[String]("userDesc"))(FeatData.apply)

    def toXml (a: FeatData) = 
      ("parentId" xml a.parentId) ++
      ("name" xml a.name) ++
      ("active" xml a.isActive) ++
      ("userDesc" xml a.desc)
  }

  def read (ns: Seq[Node]) = FeatDataToXml fromXml ns

  def write (f: FeatData) = FeatDataToXml toXml f

  val parentId: FeatData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(parentId = b), _.parentId)

  val name: FeatData @> String = Lens.lensu((a,b) ⇒ a.copy(name = b), _.name)

  val isActive: FeatData @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(isActive = b), _.isActive)

  val desc: FeatData @> String = Lens.lensu((a,b) ⇒ a.copy(desc = b), _.desc)
  
  implicit val FeatDataAbilityData =
    abilityData[FeatData](Lens.self, default)
}

// vim: set ts=2 sw=2 et:
