package efa.dsa.being.skills

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.rpg.core.{RangeVals, Util}
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class SpellData (
  talentData: TalentData,
  houseSpell: Boolean,
  representation: String
){
  def id = talentData.id
}

object SpellData extends Util with RangeVals {
  val default = SpellData(!!, false, "")

  implicit val SpellDataDefault = Default default default

  implicit val SpellDataEqual = Equal.equalA[SpellData]

  implicit val SpellDataArbitrary = Arbitrary(
    ^^(a[TalentData], a[Boolean], Gen.identifier)(SpellData.apply)
  )

  implicit val SpellDataToXml = new ToXml[SpellData] {
    def fromXml (ns: Seq[Node]) =
      ^^(TalentData.read(ns),
        ns.readTag[Boolean]("houseSpell"),
        ns.readTag[String]("representation"))(SpellData.apply)

    def toXml (a: SpellData) =
      TalentData.write(a.talentData) ++
      ("houseSpell" xml a.houseSpell) ++
      ("representation" xml a.representation)
  }

  val talentData: SpellData @> TalentData =
    Lens.lensu((a,b) ⇒ a.copy(talentData = b), _.talentData)

  val houseSpell: SpellData @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(houseSpell = b), _.houseSpell)
  
  val representation: SpellData @> String =
    Lens.lensu((a,b) ⇒ a.copy(representation = b), _.representation)

  implicit val SpellDataSkillData = skillData[SpellData](talentData)
}

// vim: set ts=2 sw=2 et:
