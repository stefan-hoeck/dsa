package efa.dsa.generation

import efa.core._, Efa._
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._
import shapeless.{Lens ⇒ _, _}, Nat._

case class GenLists(
    abilities: AbilityPrototypes,
    abilityChoices: AbilityChoices,
    equipment: EquipmentPrototypes,
    equipmentChoices: EquipmentChoices,
    skills: SkillPrototypes,
    skillChoices: SkillChoices)

object GenLists {
  implicit val GLIso = Iso.hlist(GenLists.apply _, GenLists.unapply _)
  implicit val GLDefault: Default[GenLists] = ccDefault
  implicit val GLEqual: Equal[GenLists] = ccEqual
  implicit val GLArb: Arbitrary[GenLists] = ccArbitrary

  final val lenses = SLens[GenLists]
  
  implicit class Lenses[A](val l: A @> GenLists) extends AnyVal {
    def abilities = l >=> lenses.at(_0)
    def abilityChoices = l >=> lenses.at(_1)
    def equipment = l >=> lenses.at(_2)
    def equipmentChoices = l >=> lenses.at(_3)
    def skills = l >=> lenses.at(_4)
    def skillChoices = l >=> lenses.at(_5)
  }

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
