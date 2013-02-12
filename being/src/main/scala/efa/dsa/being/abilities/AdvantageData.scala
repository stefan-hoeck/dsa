package efa.dsa.being.abilities

import efa.core.{Efa, TaggedToXml, Default}, Efa._
import efa.rpg.core.Util
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class AdvantageData(data: FeatData, value: Int) {
  require(Value validate value isRight)

  def name = data.name
  def parentId = data.parentId
}

object AdvantageData extends Util {
  val default = AdvantageData(!!, 0)

  implicit val AdvantageDataEqual = Equal.equalA[AdvantageData]

  implicit val AdvantageDataArbitrary =
    Arbitrary(^(a[FeatData], Value.gen)(AdvantageData.apply))

  implicit val AdvantageDataToXml = new TaggedToXml[AdvantageData] {
    val tag = "dsa_advantageData"

    def fromXml (ns: Seq[Node]) =
      ^(FeatData read ns, Value read ns)(AdvantageData.apply)

    def toXml (a: AdvantageData) =
      FeatData.write(a.data) ++ Value.write(a.value)
  }
  
  val data: AdvantageData @> FeatData =
    Lens.lensu((a,b) ⇒ a.copy(data = b), _.data)

  val value: AdvantageData @> Int =
    Lens.lensu((a,b) ⇒ a.copy(value = b), _.value)

  implicit val AdvantageDataAbilityData =
    abilityData[AdvantageData](data, default)
}

// vim: set ts=2 sw=2 et:
