package efa.dsa.being

import efa.core.{Efa, TaggedToXml, Default}, Efa._
import efa.dsa.being.abilities.AbilityDatas
import efa.dsa.being.equipment.EquipmentDatas
import efa.dsa.being.skills.SkillDatas
import efa.rpg.core.Util
import org.scalacheck.Arbitrary, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class HeroData(
  base: HeroBaseData,
  attributes: AttributesData,
  humanoid: HumanoidBaseData,
  abilities: AbilityDatas,
  skills: SkillDatas,
  equipment: EquipmentDatas
) {

  lazy val modifiers =
    base.modifiers ⊹ attributes.modifiers ⊹ humanoid.modifiers

}

object HeroData extends Util {
  lazy val default = HeroData(!!, !!, !!, !!, !!, !!)

  implicit val HeroDataDefault = Default default default

  implicit val HeroDataEqual = Equal.equalA[HeroData]

  implicit val HeroDataArbitrary = Arbitrary(
    ^(a[HeroBaseData],
      a[AttributesData],
      a[HumanoidBaseData],
      a[AbilityDatas],
      a[SkillDatas],
      a[EquipmentDatas])(HeroData.apply)
  )

  implicit val HeroDataToXml = new TaggedToXml[HeroData] {
    val tag = "dsa_hero"

    def fromXml (ns: Seq[Node]) =
      ^(HeroBaseData.read(ns),
        AttributesData.read(ns),
        HumanoidBaseData.read(ns),
        AbilityDatas.read(ns),
        SkillDatas.read(ns),
        EquipmentDatas.read(ns))(HeroData.apply)

    def toXml (a: HeroData) =
      HeroBaseData.write(a.base) ++
      AttributesData.write(a.attributes) ++
      HumanoidBaseData.write(a.humanoid) ++
      AbilityDatas.write(a.abilities) ++
      SkillDatas.write(a.skills) ++
      EquipmentDatas.write(a.equipment)
  }

  val base: HeroData @> HeroBaseData =
    Lens.lensu((a,b) ⇒ a copy (base = b), _.base)

  val attributes: HeroData @> AttributesData =
    Lens.lensu((a,b) ⇒ a copy (attributes = b), _.attributes)
  
  val humanoid: HeroData @> HumanoidBaseData =
    Lens.lensu((a,b) ⇒ a copy (humanoid = b), _.humanoid)

  val abilities: HeroData @> AbilityDatas =
    Lens.lensu((a,b) ⇒ a copy (abilities = b), _.abilities)

  val skills: HeroData @> SkillDatas =
    Lens.lensu((a,b) ⇒ a copy (skills = b), _.skills)
  
  val equipment: HeroData @> EquipmentDatas =
    Lens.lensu((a,b) ⇒ a copy (equipment = b), _.equipment)
}

// vim: set ts=2 sw=2 et:
