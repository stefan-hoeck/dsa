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
  
  lazy val ae = maxAe - data.humanoid.lostAe
  
  lazy val au = maxAu - data.humanoid.lostAu
  
  lazy val le = maxLe - data.humanoid.lostLe
  
  lazy val ke = maxKe - data.humanoid.lostKe

  lazy val maxAe = intProp(AeKey)

  lazy val maxAu = intProp(AuKey)

  lazy val maxLe = intProp(LeKey)

  lazy val maxKe = intProp(KeKey)

  def setAe (i: Int): ValSt[HeroData] = setDamage(maxAe, i, Humanoid.lostAe)

  def setAu (i: Int): ValSt[HeroData] = setDamage(maxAu, i, Humanoid.lostAu)

  def setKe (i: Int): ValSt[HeroData] = setDamage(maxKe, i, Humanoid.lostKe)

  def setLe (i: Int): ValSt[HeroData] = setDamage(maxLe, i, Humanoid.lostLe)

  def setBoughtAttribute (a: Attribute, i: Int): ValSt[HeroData] =
    setInt(0, attributes maxBought a, i, Attributes.bought at a)
}

object Hero extends Util {
  lazy val default = Hero(!!, Nil, !!, !!, !!, !!, Modifiers.empty, !!)

  private val Humanoid: HeroData @> HumanoidBaseData = HeroData.humanoid
  private val Attributes: HeroData @> AttributesData = HeroData.attributes

  implicit lazy val HeroDefault = Default default default

  implicit lazy val HeroEqual = Equal.equalA[Hero]

  implicit lazy val HeroTC = new Described[Hero] with AsHero[Hero] {
    def fullDesc (h: Hero) = h.data.base.desc
    def shortDesc (h: Hero) = h.data.base.desc
    def name (h: Hero) = h.data.base.name
    def desc (h: Hero) = h.data.base.desc
    def abilities (h: Hero) = h.abilities
    def equipment (h: Hero) = h.equipment
    val modifiersL = Hero.modifiers
    val attributes = Hero.attributes
    val derived = Hero.derived
  }

  val modifiers: Hero @> Modifiers =
    Lens.lensu((a,b) ⇒ a.copy(modifiers = b), _.modifiers)
  
  val attributes: Hero @> HeroAttributes =
    Lens.lensu((a,b) ⇒ a copy (attributes = b), _.attributes)

  val derived: Hero @> HeroDerived =
    Lens.lensu((a,b) ⇒ a copy (derived = b), _.derived)
}

// vim: set ts=2 sw=2 et:
