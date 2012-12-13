package efa.dsa.being

import efa.core.Default
import efa.dsa.world.Attribute
import efa.rpg.core.EnumMap
import scalaz.{Scalaz, Equal, Lens, @>}

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
  lazy val default = HeroAttributes(EnumMap(0L), EnumMap(0L),
    EnumMap(maxBoughtAtt))

  def fromHeroData (h: HeroData): HeroAttributes = {
    def crea(a: Attribute): Long =
      (h.humanoid initial a) +
      (h.base.race attributes a) +
      (h.base.culture attributes a)

    def immu(a: Attribute): Long = creation(a) + h.bought(a) 

    lazy val creation =
      EnumMap(Attribute.values map (a ⇒ (a, crea(a))) toMap)

    lazy val immutable =
      EnumMap(Attribute.values map (a ⇒ (a, immu(a))) toMap)

    HeroAttributes(creation, immutable, EnumMap(0L))
  }

  implicit lazy val HeroAttributesDefault = Default default default

  implicit lazy val HeroAttributesEqual = Equal.equalA[HeroAttributes]

  val creation: HeroAttributes @> Attributes =
    Lens.lensu((a,b) ⇒ a copy (creation = b), _.creation)

  val immutable: HeroAttributes @> Attributes =
    Lens.lensu((a,b) ⇒ a copy (immutable = b), _.immutable)
  
  val maxBought: HeroAttributes @> Attributes =
    Lens.lensu((a,b) ⇒ a copy (maxBought = b), _.maxBought)
  
  implicit class HeroAttributesLenses[A] (val l: A @> HeroAttributes) extends AnyVal {
    def creation = l >=> HeroAttributes.creation
    def immutable = l >=> HeroAttributes.immutable
    def maxBought = l >=> HeroAttributes.maxBought
  }
}

// vim: set ts=2 sw=2 et:
