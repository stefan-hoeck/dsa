package efa.dsa.being.abilities

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.Util
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class FeatData (
  id: Int,
  name: String,
  isActive: Boolean,
  desc: String
)

object FeatData extends Util {
  val default = FeatData(0, "", true, "")

  implicit val FeatDataDefault = Default default default

  implicit val FeatDataEqual = Equal.equalA[FeatData]

  implicit val FeatDataArbitrary = Arbitrary(
    ^(a[Int], Gen.identifier, a[Boolean], Gen.identifier)(FeatData.apply)
  )

  implicit val FeatDataToXml = new ToXml[FeatData] {
    def fromXml (ns: Seq[Node]) =
      ^(ns.readTag[Int]("parentId"),
        ns.readTag[String]("name"),
        ns.readTag[Boolean]("active"),
        ns.readTag[String]("userDesc"))(FeatData.apply)

    def toXml (a: FeatData) = 
      ("parentId" xml a.id) ++
      ("name" xml a.name) ++
      ("active" xml a.isActive) ++
      ("userDesc" xml a.desc)
  }
  
  implicit val FeatDataAbilityData = abilityData[FeatData](Lens.self)

  def read (ns: Seq[Node]) = FeatDataToXml fromXml ns

  def write (f: FeatData) = FeatDataToXml toXml f

  val id: FeatData @> Int = Lens.lensu((a,b) ⇒ a.copy(id = b), _.id)

  val name: FeatData @> String = Lens.lensu((a,b) ⇒ a.copy(name = b), _.name)

  val isActive: FeatData @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(isActive = b), _.isActive)

  val desc: FeatData @> String = Lens.lensu((a,b) ⇒ a.copy(desc = b), _.desc)
  
  
  
  
}

// vim: set ts=2 sw=2 et:
