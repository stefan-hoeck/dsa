package efa.dsa.generation

import efa.core._, Efa._
import efa.core.syntax.{string, nodeSeq}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._

case class GenLists(
    abilities: AbilityPrototypes,
    abilityChoices: AbilityChoices,
    equipment: EquipmentPrototypes,
    equipmentChoices: EquipmentChoices,
    skills: SkillPrototypes,
    skillChoices: SkillChoices)

object GenLists {
  implicit val GLDefault = deriveDefault[GenLists]
  implicit val GLEqual = deriveEqual[GenLists]
  implicit val GLArb = deriveArbitrary[GenLists]

  final val L = shapeless.lens[GenLists]
  
  //implicit class Lenses[A](val l: A @> GenLists) extends AnyVal {
  //  def abilities = l >=> zlens(L >> 'abilities)
  //  def abilityChoices = l >=> zlens(L >> 'abilityChoices)
  //  def equipment = l >=> zlens(L >> 'equipment)
  //  def equipmentChoices = zlens(L >> 'equipmentChoices)
  //  def skills = l >=> zlens(L >> 'skills)
  //  def skillChoices = l >=> zlens(L >> 'skillChoices)
  //}

  implicit val GLToXml = new ToXml[GenLists] {
    def fromXml(ns: Seq[Node]) = ^^^^^(
      ns.readTag[AbilityPrototypes]("abilities"),
      ns.readTag[AbilityChoices]("abilityChoices"),
      ns.readTag[EquipmentPrototypes]("equipment"),
      ns.readTag[EquipmentChoices]("equipmentChoices"),
      ns.readTag[SkillPrototypes]("skills"),
      ns.readTag[SkillChoices]("skillChoices")
    )(GenLists.apply)

    def toXml(c: GenLists) =
      ("abilities" xml c.abilities) ++
      ("abilityChoices" xml c.abilityChoices) ++
      ("equipment" xml c.equipment) ++
      ("equipmentChoices" xml c.equipmentChoices) ++
      ("skills" xml c.skills) ++
      ("skillChoices" xml c.skillChoices)
  }
}

// vim: set ts=2 sw=2 et:
