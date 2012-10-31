package efa.dsa.being

import efa.core.Default
import efa.dsa.world.Attribute
import efa.rpg.core.EnumMap
import scalaz.{Scalaz, Equal}

case class HeroAttributes (
  /**
   * Attributes from creation (base value plus modifiers from race, culture, and
   * (Dis)Advantages
   */
  creation: Attributes,
  
  /**
   * Non-temporary modifiers for Attributes (usually same as creation plus bought
   * attributes).
   */
  immutable: Attributes,
  
  /**
   * The maximum additional attributes a hero can buy. Usually depends on the
   * creation value only.
   */
  maxBought: Attributes
)

object HeroAttributes {
  lazy val default = HeroAttributes(EnumMap(0), EnumMap(0), EnumMap(0))

  def fromHeroData (h: HeroData): HeroAttributes = {
    def crea(a: Attribute): Int =
      (h.attributes initial a) +
      (h.base.race attributes a) +
      (h.base.culture attributes a)

    def immu(a: Attribute) = creation(a) + h.attributes.bought(a) 

    lazy val creation = EnumMap(Attribute.values map (a ⇒ (a, crea(a))) toMap)

    lazy val immutable = EnumMap(Attribute.values map (a ⇒ (a, immu(a))) toMap)

    HeroAttributes(creation, immutable, EnumMap(0))
  }

  implicit lazy val HeroAttributesDefault = Default default default

  implicit lazy val HeroAttributesEqual = Equal.equalA[HeroAttributes]
}

// vim: set ts=2 sw=2 et:
