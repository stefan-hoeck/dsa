package efa.dsa.being

import efa.core.{Efa, ToXml, Default, ValRes}, Efa._
import efa.dsa.being.abilities.AbilityDatas
import efa.dsa.being.equipment.{EquipmentDatas, HandsData}
import efa.dsa.being.skills.SkillDatas
import efa.rpg.core.{Modifiers, Util}
import org.scalacheck.Arbitrary, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class HumanoidData(
  initial: Attributes,
  lostAe: Long,
  lostAu: Long,
  lostKe: Long,
  lostLe: Long,
  wounds: Long,
  exhaustion: Long,
  zoneWounds: ZoneWounds,
  hands: HandsData,
  abilities: AbilityDatas,
  equipment: EquipmentDatas,
  skills: SkillDatas
) {
  require (InitialAttributes validate initial isRight)
  require(LostAe validate lostAe isRight, LostAe validate lostAe toString)
  require(LostAu validate lostAu isRight)
  require(LostKe validate lostKe isRight)
  require(LostLe validate lostLe isRight)
  require(Wounds validate wounds isRight)
  require(Exhaustion validate exhaustion isRight)

  lazy val modifiers: Modifiers = attributeMods(initial, loc.initial)
}

object HumanoidData extends Util {
  lazy val default = HumanoidData(InitialAttributes.!!, 0, 0, 0, 0, 0,
    0, ZoneWounds.!!, !!, !!, !!, !!)

  implicit val HumanoidDataDefault = Default default default

  implicit val HumanoidDataEqual = Equal.equalA[HumanoidData]

  implicit val HumanoidDataArbitrary = Arbitrary(
    for {
      att ← InitialAttributes.gen
      lAe ← LostAe.gen
      lAu ← LostAu.gen
      lKe ← LostKe.gen
      lLe ← LostLe.gen
      wounds ← Wounds.gen
      exhaustion ← Exhaustion.gen
      zw ← ZoneWounds.gen
      hands ← a[HandsData]
      abilities ← a[AbilityDatas]
      equipment ← a[EquipmentDatas]
      skills ← a[SkillDatas]
    } yield HumanoidData(att, lAe, lAu, lKe, lLe, wounds,
      exhaustion, zw, hands, abilities, equipment, skills)
  )

  implicit val HumanoidDataToXml = new ToXml[HumanoidData] {
    def fromXml (ns: Seq[Node]) = Apply[ValRes].apply12(
      InitialAttributes read ns,
      LostAe.read(ns),
      LostAu.read(ns),
      LostKe.read(ns),
      LostLe.read(ns),
      Wounds.read(ns),
      Exhaustion.read(ns),
      ZoneWounds.read(ns),
      ns.tagged[HandsData],
      ToXml[AbilityDatas] fromXml ns,
      ToXml[EquipmentDatas] fromXml ns,
      ToXml[SkillDatas] fromXml ns
    )(HumanoidData.apply)

    def toXml (a: HumanoidData) =
      InitialAttributes.write(a.initial) ++
      LostAe.write(a.lostAe) ++
      LostAu.write(a.lostAu) ++
      LostKe.write(a.lostKe) ++
      LostLe.write(a.lostLe) ++
      Wounds.write(a.wounds) ++
      Exhaustion.write(a.exhaustion) ++
      ZoneWounds.write(a.zoneWounds) ++
      Efa.toXml(a.hands) ++
      ToXml[AbilityDatas].toXml(a.abilities) ++
      ToXml[EquipmentDatas].toXml(a.equipment) ++
      ToXml[SkillDatas].toXml(a.skills)
  }

  def read (ns: Seq[Node]) = HumanoidDataToXml fromXml ns

  def write (h: HumanoidData) = HumanoidDataToXml toXml h

  val initial: HumanoidData @> Attributes =
    Lens.lensu((a,b) ⇒ a.copy(initial = b), _.initial)

  val lostAe: HumanoidData @> Long =
    Lens.lensu((a,b) ⇒ a copy (lostAe = b), _.lostAe)

  val lostAu: HumanoidData @> Long =
    Lens.lensu((a,b) ⇒ a copy (lostAu = b), _.lostAu)

  val lostKe: HumanoidData @> Long =
    Lens.lensu((a,b) ⇒ a copy (lostKe = b), _.lostKe)

  val lostLe: HumanoidData @> Long =
    Lens.lensu((a,b) ⇒ a copy (lostLe = b), _.lostLe)

  val exhaustion: HumanoidData @> Long =
    Lens.lensu((a,b) ⇒ a copy (exhaustion = b), _.exhaustion)

  val wounds: HumanoidData @> Long =
    Lens.lensu((a,b) ⇒ a copy (wounds = b), _.wounds)
  
  val zoneWounds: HumanoidData @> ZoneWounds =
    Lens.lensu((a,b) ⇒ a copy (zoneWounds = b), _.zoneWounds)

  val hands: HumanoidData @> HandsData =
    Lens.lensu((a,b) ⇒ a copy (hands = b), _.hands)

  val equipment: HumanoidData @> EquipmentDatas =
    Lens.lensu((a,b) ⇒ a.copy(equipment = b), _.equipment)

  val abilities: HumanoidData @> AbilityDatas =
    Lens.lensu((a,b) ⇒ a.copy(abilities = b), _.abilities)

  val skills: HumanoidData @> SkillDatas =
    Lens.lensu((a,b) ⇒ a.copy(skills = b), _.skills)

  implicit class HumanoidDataLenses[A] (val l: A @> HumanoidData) extends AnyVal {
    def initial = l >=> HumanoidData.initial
    def lostAe = l >=> HumanoidData.lostAe
    def lostAu = l >=> HumanoidData.lostAu
    def lostKe = l >=> HumanoidData.lostKe
    def lostLe = l >=> HumanoidData.lostLe
    def exhaustion = l >=> HumanoidData.exhaustion
    def wounds = l >=> HumanoidData.wounds
    def zoneWounds = l >=> HumanoidData.zoneWounds
    def hands = l >=> HumanoidData.hands
    def abilities = l >=> HumanoidData.abilities
    def equipment = l >=> HumanoidData.equipment
    def skills = l >=> HumanoidData.skills
  }
}

// vim: set ts=2 sw=2 et:
