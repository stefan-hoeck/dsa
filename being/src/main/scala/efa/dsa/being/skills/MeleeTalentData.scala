package efa.dsa.being.skills

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.{RangeVals, Util}
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class MeleeTalentData (talentData: TalentData, at: Int) {
  require (MeleeTalentData atI talentData  validate at isRight)

  def id = talentData.id
}

object MeleeTalentData extends Util with RangeVals {
  val default = MeleeTalentData(!!, 0)

  def atI(sd: TalentData) = fullInfo(0, sd.tap, "at")

  implicit val MeleeTalentDataDefault = Default default default

  implicit val MeleeTalentDataEqual = Equal.equalA[MeleeTalentData]

  implicit val MeleeTalentDataArbitrary = Arbitrary(
    for {
      sd ← a[TalentData]
      at ← atI(sd).gen
    } yield MeleeTalentData(sd, at)
  )

  implicit val MeleeTalentDataToXml = new ToXml[MeleeTalentData] {
    def fromXml (ns: Seq[Node]) = for {
      sd ← TalentData read ns
      a  ← atI(sd) read ns
    } yield MeleeTalentData(sd, a)

    def toXml (a: MeleeTalentData) =
      TalentData.write(a.talentData) ++ ("at" xml a.at)
  }

  val talentData: MeleeTalentData @> TalentData =
    Lens.lensu((a,b) ⇒ a.copy(talentData = b), _.talentData)

  val at: MeleeTalentData @> Int = Lens.lensu((a,b) ⇒ a.copy(at = b), _.at)

  implicit val MeleeTalentDataTalentData =
    skillData[MeleeTalentData](talentData)
}

// vim: set ts=2 sw=2 et:
