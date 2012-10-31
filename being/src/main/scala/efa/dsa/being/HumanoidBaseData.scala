package efa.dsa.being

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.dsa.being.equipment.HandsData
import efa.rpg.core.{Modifiers, Util}
import org.scalacheck.Arbitrary, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class HumanoidBaseData(
  lostAe: Int,
  boughtAe: Int,
  lostAu: Int,
  boughtAu: Int,
  lostKe: Int,
  boughtKe: Int, 
  lostLe: Int,
  boughtLe: Int,
  boughtMr: Int,
  wounds: Int,
  exhaustion: Int,
  zoneWounds: ZoneWounds,
  hands: HandsData
) {
  require(LostAe validate lostAe isRight)
  require(LostAu validate lostAu isRight)
  require(LostKe validate lostKe isRight)
  require(LostLe validate lostLe isRight)
  require(BoughtAe validate boughtAe isRight)
  require(BoughtAu validate boughtAu isRight)
  require(BoughtKe validate boughtKe isRight)
  require(BoughtLe validate boughtLe isRight)
  require(BoughtMr validate boughtMr isRight)
  require(Wounds validate wounds isRight)
  require(Exhaustion validate exhaustion isRight)

  lazy val modifiers: Modifiers = Modifiers (
    AeKey → modSeq(boughtAe, loc.bought),
    AuKey → modSeq(boughtAu, loc.bought),
    KeKey → modSeq(boughtKe, loc.bought),
    LeKey → modSeq(boughtLe, loc.bought),
    MrKey → modSeq(boughtMr, loc.bought)
  )
}

object HumanoidBaseData extends Util {
  lazy val default =
    HumanoidBaseData(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ZoneWounds.!!, !!)

  implicit val HumanoidBaseDataDefault = Default default default

  implicit val HumanoidBaseDataEqual = Equal.equalA[HumanoidBaseData]

  implicit val HumanoidBaseDataArbitrary = Arbitrary(
    for {
      lAe ← LostAe.gen
      bAe ← BoughtAe.gen
      lAu ← LostAu.gen
      bAu ← BoughtAu.gen
      lKe ← LostKe.gen
      bKe ← BoughtKe.gen
      lLe ← LostLe.gen
      bLe ← BoughtLe.gen
      bMr ← BoughtMr.gen
      wounds ← Wounds.gen
      exhaustion ← Exhaustion.gen
      zw ← ZoneWounds.gen
      hands ← a[HandsData]
    } yield HumanoidBaseData(lAe, bAe, lAu, bAu, lKe, bKe, lLe, bLe,
      bMr, wounds, exhaustion, zw, hands)
  )

  implicit val HumanoidBaseDataToXml = new ToXml[HumanoidBaseData] {
    def fromXml (ns: Seq[Node]) = {
      def fst =
        ^(LostAe.read(ns),
          BoughtAe.read(ns),
          LostAu.read(ns),
          BoughtAu.read(ns),
          LostKe.read(ns),
          BoughtKe.read(ns),
          LostLe.read(ns))(Tuple7.apply)

      def snd =
        ^(BoughtLe.read(ns),
          BoughtMr.read(ns),
          Wounds.read(ns),
          Exhaustion.read(ns),
          ZoneWounds.read(ns),
          ns.tagged[HandsData])(Tuple6.apply)

      ^(fst, snd){
        case ((a, b, c, d, e, f, g), (h, i, j, k, l, m)) ⇒ 
          HumanoidBaseData(a, b, c, d, e, f, g, h, i, j, k, l, m)
      }
    }

    def toXml (a: HumanoidBaseData) =
      LostAe.write(a.lostAe) ++
      BoughtAe.write(a.boughtAe) ++
      LostAu.write(a.lostAu) ++
      BoughtAu.write(a.boughtAu) ++
      LostKe.write(a.lostKe) ++
      BoughtKe.write(a.boughtKe) ++
      LostLe.write(a.lostLe) ++
      BoughtLe.write(a.boughtLe) ++
      BoughtMr.write(a.boughtMr) ++
      Wounds.write(a.wounds) ++
      Exhaustion.write(a.exhaustion) ++
      ZoneWounds.write(a.zoneWounds) ++
      xml(a.hands)
  }

  def read (ns: Seq[Node]) = HumanoidBaseDataToXml fromXml ns

  def write (h: HumanoidBaseData) = HumanoidBaseDataToXml toXml h

  val lostAe: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (lostAe = b), _.lostAe)

  val boughtAe: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (boughtAe = b), _.boughtAe)

  val lostAu: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (lostAu = b), _.lostAu)

  val boughtAu: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (boughtAu = b), _.boughtAu)

  val lostKe: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (lostKe = b), _.lostKe)

  val boughtKe: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (boughtKe = b), _.boughtKe)

  val lostLe: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (lostLe = b), _.lostLe)

  val boughtLe: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (boughtLe = b), _.boughtLe)

  val boughtMr: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (boughtMr = b), _.boughtMr)

  val exhaustion: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (exhaustion = b), _.exhaustion)

  val wounds: HumanoidBaseData @> Int =
    Lens.lensu((a,b) ⇒ a copy (wounds = b), _.wounds)
  
  val zoneWounds: HumanoidBaseData @> ZoneWounds =
    Lens.lensu((a,b) ⇒ a copy (zoneWounds = b), _.zoneWounds)

  val hands: HumanoidBaseData @> HandsData =
    Lens.lensu((a,b) ⇒ a copy (hands = b), _.hands)
}

// vim: set ts=2 sw=2 et:
