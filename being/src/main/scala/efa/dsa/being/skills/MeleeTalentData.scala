package efa.dsa.being.skills

import efa.core.{Efa, ToXml}, Efa._
import efa.rpg.core.{RangeVals, Util}
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class MeleeTalentData (talentData: TalentData, at: Int) {
  require (At validate at isRight)

  def id = talentData.id
}

object MeleeTalentData extends Util with RangeVals {
  val default = MeleeTalentData(!!, 0)

  implicit val MeleeTalentDataEqual = Equal.equalA[MeleeTalentData]

  implicit val MeleeTalentDataArbitrary = Arbitrary(
    ^(a[TalentData], At.gen)(MeleeTalentData.apply)
  )

  implicit val MeleeTalentDataToXml = new ToXml[MeleeTalentData] {
    def fromXml (ns: Seq[Node]) =
      ^(TalentData read ns, At read ns)(MeleeTalentData.apply)

    def toXml (a: MeleeTalentData) =
      TalentData.write(a.talentData) ++ At.write(a.at)
  }

  val talentData: MeleeTalentData @> TalentData =
    Lens.lensu((a,b) ⇒ a.copy(talentData = b), _.talentData)

  val at: MeleeTalentData @> Int = Lens.lensu((a,b) ⇒ a.copy(at = b), _.at)

  implicit val MeleeTalentDataTalentData =
    skillData[MeleeTalentData](talentData, default)
}

// vim: set ts=2 sw=2 et:
