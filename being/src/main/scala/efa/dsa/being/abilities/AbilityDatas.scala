package efa.dsa.being.abilities

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.data.Maps
import efa.rpg.core.{Util}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class AbilityDatas(
  advantages: Map[String,AdvantageData],
  handicaps: Map[String,AdvantageData],
  feats: Map[String,FeatData]
)

object AbilityDatas extends Util with Maps {

  lazy val default = AbilityDatas(Map.empty, Map.empty, Map.empty)

  implicit lazy val AbilityDatasDefault = Default default default

  implicit lazy val AbilityDatasEqual = Equal.equalA[AbilityDatas]

  implicit lazy val AbilityDatasArbitrary = {
    implicit val featMapArbitrary = mapArbitrary[FeatData,String]
    implicit val advMapArbitrary = mapArbitrary[AdvantageData,String]

    Arbitrary(
      ^^(a[Map[String,AdvantageData]],
        a[Map[String,AdvantageData]],
        a[Map[String,FeatData]])(AbilityDatas.apply)
    )
  }

  implicit lazy val AbilityDatasToXml = new ToXml[AbilityDatas] {
    implicit val featXml = mapToXml[FeatData,String]("dsa_feat")
    implicit val advXml = mapToXml[AdvantageData,String]("dsa_advantageData")

    def fromXml (ns: Seq[Node]) =
      ^^(ns.readTag[Map[String,AdvantageData]]("advantages"),
        ns.readTag[Map[String,AdvantageData]]("handicaps"),
        ns.readTag[Map[String,FeatData]]("feats"))(AbilityDatas.apply)

    def toXml (a: AbilityDatas) = 
      ("advantages" xml a.advantages) ++
      ("handicaps" xml a.handicaps) ++
      ("feats" xml a.feats)
  }

  def read (ns: Seq[Node]) = AbilityDatasToXml fromXml ns

  def write (h: AbilityDatas) = AbilityDatasToXml toXml h

  val advantages: AbilityDatas @> Map[String,AdvantageData] =
    Lens.lensu((a,b) ⇒ a.copy(advantages = b), _.advantages)

  val handicaps: AbilityDatas @> Map[String,AdvantageData] =
    Lens.lensu((a,b) ⇒ a.copy(handicaps = b), _.handicaps)

  val feats: AbilityDatas @> Map[String,FeatData] =
    Lens.lensu((a,b) ⇒ a.copy(feats = b), _.feats)
}

// vim: set ts=2 sw=2 et:
