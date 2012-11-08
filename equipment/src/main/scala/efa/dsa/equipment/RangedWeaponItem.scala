package efa.dsa.equipment

import efa.core.{ToXml, Efa, ValRes}, Efa._
import efa.dsa.world.{TpKk, RangedDistance}
import efa.rpg.core.DieRoller
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.{Arbitrary, Gen}

case class RangedWeaponItem(
  eData: EquipmentItemData,
  tp: DieRoller,
  tpkk: TpKk,
  improvised: Boolean,
  talent: String,
  reach: Reach,
  tpPlus: TpPlus,
  makesWound: Boolean,
  timeToLoad: Int,
  usesAmmo: Boolean
) extends EquipmentLike[RangedWeaponItem] {
  require(Ttl validate timeToLoad isRight)
  
  def eData_= (v: EquipmentItemData) = copy (eData = v)
}

object RangedWeaponItem extends EquipmentItemLikes[RangedWeaponItem] {

  lazy val default = RangedWeaponItem (eData(loc.rangedWeapon), !!,
    !!, false, "", Reach.!!, TpPlus.!!, false, 0, false)

  def shortDesc (i: RangedWeaponItem) = {
    def tpTag = (loc.tp, i.tp.toString)
    def tpkkTag = (loc.tpkk, i.tpkk.toString)
    def reachTag = (loc.reach, Reach shows i.reach)
    def tpPlusTag = (loc.tpPlus, TpPlus shows i.tpPlus)
    def ttlTag = (loc.timeToLoad, i.timeToLoad.toString)

    tagShortDesc (i, tpTag, tpkkTag, reachTag, tpPlusTag, ttlTag,
                  priceTag(i), weightTag(i))
  }

  implicit lazy val RangedWeaponItemToXml = new ToXml[RangedWeaponItem] {
    def fromXml (ns: Seq[Node]) = Apply[ValRes].apply10(
      readEData (ns),
      Tp read ns,
      ns.tagged[TpKk],
      ns.readTag[Boolean]("improvised"),
      Talent read ns,
      Reach read ns,
      TpPlus read ns,
      ns.readTag[Boolean]("wound"),
      Ttl read ns,
      ns.readTag[Boolean]("ammunition")
    )(RangedWeaponItem.apply)

    def toXml (a: RangedWeaponItem) =
      dataToNode(a) ++
      Tp.write(a.tp) ++
      xml(a.tpkk) ++
      ("improvised" xml a.improvised) ++
      Talent.write(a.talent) ++
      Reach.write(a.reach) ++
      TpPlus.write(a.tpPlus) ++
      ("wound" xml a.makesWound) ++
      Ttl.write(a.timeToLoad) ++
      ("ammunition" xml a.usesAmmo)
  }

  implicit lazy val RangedWeaponItemArbitrary = Arbitrary(
    Apply[Gen].apply10(
      a[EquipmentItemData],
      a[DieRoller],
      a[TpKk],
      a[Boolean],
      Gen.identifier,
      Reach.gen,
      TpPlus.gen,
      a[Boolean],
      Ttl.gen,
      a[Boolean]
    )(RangedWeaponItem.apply)
  )

  val tp: RangedWeaponItem @> DieRoller =
    Lens.lensu((a,b) ⇒ a.copy(tp = b), _.tp)

  val tpkk: RangedWeaponItem @> TpKk =
    Lens.lensu((a,b) ⇒ a.copy(tpkk = b), _.tpkk)

  val improvised: RangedWeaponItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(improvised = b), _.improvised)

  val talent: RangedWeaponItem @> String =
    Lens.lensu((a,b) ⇒ a.copy(talent = b), _.talent)

  val reach: RangedWeaponItem @> Reach =
    Lens.lensu((a,b) ⇒ a.copy(reach = b), _.reach)

  val tpPlus: RangedWeaponItem @> TpPlus =
    Lens.lensu((a,b) ⇒ a.copy(tpPlus = b), _.tpPlus)

  val timeToLoad: RangedWeaponItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(timeToLoad = b), _.timeToLoad)
  
  val makesWound: RangedWeaponItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(makesWound = b), _.makesWound)

  val usesAmmo: RangedWeaponItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(usesAmmo = b), _.usesAmmo)
}

// vim: set ts=2 sw=2 et:
