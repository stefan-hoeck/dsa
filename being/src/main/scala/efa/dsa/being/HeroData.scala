package efa.dsa.being

import efa.core.{Efa, TaggedToXml, Default, ValRes}, Efa._
import efa.core.syntax.{string, nodeSeq}
import efa.dsa.being.abilities.AbilityDatas
import efa.dsa.being.equipment.EquipmentDatas
import efa.dsa.being.skills.SkillDatas
import efa.rpg.core.{Util, Modifiers}
import org.scalacheck.{Gen, Arbitrary}, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class HeroData(
  base: HeroBaseData,
  humanoid: HumanoidData,
  bought: Attributes,
  boughtAe: Long,
  boughtAu: Long,
  boughtKe: Long, 
  boughtLe: Long,
  boughtMr: Long,
  specialExp: BoolAtts
) {
  require (BoughtAttributes validate bought isRight)
  require(BoughtAe validate boughtAe isRight)
  require(BoughtAu validate boughtAu isRight)
  require(BoughtKe validate boughtKe isRight)
  require(BoughtLe validate boughtLe isRight)
  require(BoughtMr validate boughtMr isRight)

  lazy val modifiers =
    base.modifiers ⊹ humanoid.modifiers ⊹
    attributeMods(bought, loc.bought) ⊹ Modifiers (
      AeKey → modSeq(boughtAe, loc.bought),
      AuKey → modSeq(boughtAu, loc.bought),
      KeKey → modSeq(boughtKe, loc.bought),
      LeKey → modSeq(boughtLe, loc.bought),
      MrKey → modSeq(boughtMr, loc.bought)
    )

}

object HeroData extends Util {
  lazy val default = HeroData(!!, !!, BoughtAttributes.!!,
    0L, 0L, 0L, 0L, 0L, AttributesSpecialExp.!!)

  implicit val HeroDataDefault = Default default default

  implicit val HeroDataEqual = Equal.equalA[HeroData]

  implicit val HeroDataArbitrary = Arbitrary(Apply[Gen].apply9(
      a[HeroBaseData],
      a[HumanoidData],
      BoughtAttributes.gen,
      BoughtAe.gen,
      BoughtAu.gen,
      BoughtKe.gen,
      BoughtLe.gen,
      BoughtMr.gen,
      AttributesSpecialExp.gen
    )(HeroData.apply)
  )

  implicit val HeroDataToXml = new TaggedToXml[HeroData] {
    val tag = "dsa_hero"

    def fromXml (ns: Seq[Node]) = Apply[ValRes].apply9(
      HeroBaseData read ns,
      HumanoidData read ns,
      BoughtAttributes read ns,
      BoughtAe read ns,
      BoughtAu read ns,
      BoughtKe read ns,
      BoughtLe read ns,
      BoughtMr read ns,
      AttributesSpecialExp read ns
    )(HeroData.apply)

    def toXml (a: HeroData) =
      HeroBaseData.write(a.base) ++
      HumanoidData.write(a.humanoid) ++
      BoughtAttributes.write(a.bought) ++
      BoughtAe.write(a.boughtAe) ++
      BoughtAu.write(a.boughtAu) ++
      BoughtKe.write(a.boughtKe) ++
      BoughtLe.write(a.boughtLe) ++
      BoughtMr.write(a.boughtMr) ++
      AttributesSpecialExp.write(a.specialExp)
  }

  val base: HeroData @> HeroBaseData =
    Lens.lensu((a,b) ⇒ a copy (base = b), _.base)
  
  val humanoid: HeroData @> HumanoidData =
    Lens.lensu((a,b) ⇒ a copy (humanoid = b), _.humanoid)

  val bought: HeroData @> Attributes =
    Lens.lensu((a,b) ⇒ a.copy(bought = b), _.bought)

  val boughtAe: HeroData @> Long =
    Lens.lensu((a,b) ⇒ a copy (boughtAe = b), _.boughtAe)

  val boughtAu: HeroData @> Long =
    Lens.lensu((a,b) ⇒ a copy (boughtAu = b), _.boughtAu)

  val boughtKe: HeroData @> Long =
    Lens.lensu((a,b) ⇒ a copy (boughtKe = b), _.boughtKe)

  val boughtLe: HeroData @> Long =
    Lens.lensu((a,b) ⇒ a copy (boughtLe = b), _.boughtLe)

  val boughtMr: HeroData @> Long =
    Lens.lensu((a,b) ⇒ a copy (boughtMr = b), _.boughtMr)

  val specialExp: HeroData @> BoolAtts =
    Lens.lensu((a,b) ⇒ a copy (specialExp = b), _.specialExp)

  implicit class HeroDataLenses[A] (val l: A @> HeroData) extends AnyVal {
    def base = l >=> HeroData.base
    def humanoid = l >=> HeroData.humanoid
    def bought = l >=> HeroData.bought
    def boughtAe = l >=> HeroData.boughtAe
    def boughtAu = l >=> HeroData.boughtAu
    def boughtKe = l >=> HeroData.boughtKe
    def boughtLe = l >=> HeroData.boughtLe
    def boughtMr = l >=> HeroData.boughtMr
    def specialExp = l >=> HeroData.specialExp
  }
}

// vim: set ts=2 sw=2 et:
