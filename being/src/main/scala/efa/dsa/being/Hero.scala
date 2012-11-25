package efa.dsa.being

import efa.core.{Default, ValSt, Validators}
import efa.dsa.being.abilities.Abilities
import efa.dsa.being.equipment.{Equipments, AttackMode}
import efa.dsa.being.skills.Skills
import efa.dsa.world.Attribute
import efa.rpg.core._
import scalaz._, Scalaz._

case class Hero (
  abilities: Abilities,
  attackModes: List[AttackMode],
  attributes: HeroAttributes,
  data: HeroData,
  derived: HeroDerived,
  equipment: Equipments,
  modifiers: Modifiers,
  skills: Skills
) {
  import Hero._

  def intProp (k: ModifierKey): Int = prop(k).toInt

  def prop (k: ModifierKey): Long = modifiers property k

//  def setBoughtAttribute (a: Attribute, i: Int): ValSt[HeroData] =
//    setInt(0, attributes maxBought a, i, Attributes.bought at a)
}

object Hero extends Util {
  lazy val default = Hero(!!, Nil, !!, !!, !!, !!, Modifiers.empty, !!)

  private val Humanoid: HeroData @> HumanoidData = HeroData.humanoid

  implicit lazy val HeroDefault = Default default default

  implicit lazy val HeroEqual = Equal.equalA[Hero]

  implicit lazy val HeroTC = new AsHero[Hero] {
    def abilities (h: Hero) = h.abilities
    val attributes = Hero.attributes
    val derived = Hero.derived
    def equipment (h: Hero) = h.equipment
    def heroData (h: Hero) = h.data
    val modifiersL = Hero.modifiers
  }

  val modifiers: Hero @> Modifiers =
    Lens.lensu((a,b) ⇒ a.copy(modifiers = b), _.modifiers)
  
  val attributes: Hero @> HeroAttributes =
    Lens.lensu((a,b) ⇒ a copy (attributes = b), _.attributes)

  val derived: Hero @> HeroDerived =
    Lens.lensu((a,b) ⇒ a copy (derived = b), _.derived)
}

// vim: set ts=2 sw=2 et:
