package efa.dsa.equipment

import efa.core.{ToXml, Efa}, Efa._
import efa.dsa.world.{TpKk, Wm, DistanceClass}
import efa.rpg.core.DieRoller
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._
import scala.xml.Node
import org.scalacheck.{Arbitrary, Gen}

case class MeleeWeaponItem(
  eData: EquipmentItemData,
  tp: DieRoller,
  tpkk: TpKk,
  improvised: Boolean,
  talent: String,
  bf: Int,
  twoHanded: Boolean,
  wm: Wm,
  ini: Int,
  dk: DistanceClass,
  length: Long
) extends EquipmentLike[MeleeWeaponItem] {
  require(Bf validate bf isRight)
  require(Ini validate ini isRight)
  require(Length validate length isRight)
  
  def eData_= (v: EquipmentItemData) = copy (eData = v)
}

object MeleeWeaponItem extends EquipmentItemLikes[MeleeWeaponItem] {
  lazy val default = MeleeWeaponItem (eData(loc.meleeWeapon), !!,
    !!, false, "", 0, false, !!, 0, !!, 0L)

  def shortDesc (i: MeleeWeaponItem) = {
    def tpTag = (loc.tp, i.tp.toString)
    def tpkkTag = (loc.tpkk, i.tpkk.toString)
    def bfTag = (loc.bf, i.bf.toString)
    def wmTag = (loc.wm, i.wm.shows)
    def iniTag = (loc.ini, i.ini.toString)
    def dkTag = (loc.dk, i.dk.toString)

    tagShortDesc (i, tpTag, tpkkTag, bfTag, wmTag, iniTag, dkTag, 
                  priceTag(i), weightTag(i))
  }

  implicit lazy val MeleeWeaponItemToXml = new ToXml[MeleeWeaponItem] {
    def fromXml (ns: Seq[Node]) = {
      def first = 
        ^^^^^^(readEData(ns),
          Tp read ns,
          ns.tagged[TpKk],
          ns.readTag[Boolean]("improvised"),
          Talent read ns,
          Bf read ns,
          ns.readTag[Boolean]("twohanded"))(Tuple7.apply)
      def second =
        ^^^(ns.tagged[Wm],
          Ini read ns,
          ns.tagged[DistanceClass],
          Length read ns)(Tuple4.apply)

      ^(first, second)((t1, t2) ⇒ MeleeWeaponItem(
        t1._1, t1._2, t1._3, t1._4, t1._5, t1._6, t1._7,
        t2._1, t2._2, t2._3, t2._4))
    }

    def toXml (a: MeleeWeaponItem) = 
      dataToNode(a) ++
      Tp.write(a.tp) ++
      xml(a.tpkk) ++
      ("improvised" xml a.improvised) ++
      Talent.write(a.talent) ++
      Bf.write(a.bf) ++
      ("twohanded" xml a.twoHanded) ++
      xml(a.wm) ++
      Ini.write(a.ini) ++
      xml(a.dk) ++
      Length.write(a.length)
  }

  implicit lazy val MeleeWeaponItemArbitrary = {
    def first =
      ^^^^^^(a[EquipmentItemData],
        a[DieRoller],
        a[TpKk],
        a[Boolean],
        Gen.identifier,
        Bf.gen,
        a[Boolean])(Tuple7.apply)
    def second =
      ^^^(a[Wm],
        Ini.gen,
        a[DistanceClass],
        Length.gen)(Tuple4.apply)

      Arbitrary (^(first, second)((t1, t2) ⇒ MeleeWeaponItem(
        t1._1, t1._2, t1._3, t1._4, t1._5, t1._6, t1._7,
        t2._1, t2._2, t2._3, t2._4)))
  }

  val tp: MeleeWeaponItem @> DieRoller =
    Lens.lensu((a,b) ⇒ a.copy(tp = b), _.tp)

  val tpkk: MeleeWeaponItem @> TpKk =
    Lens.lensu((a,b) ⇒ a.copy(tpkk = b), _.tpkk)

  val improvised: MeleeWeaponItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(improvised = b), _.improvised)

  val talent: MeleeWeaponItem @> String =
    Lens.lensu((a,b) ⇒ a.copy(talent = b), _.talent)

  val bf: MeleeWeaponItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(bf = b), _.bf)

  val twoHanded: MeleeWeaponItem @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(twoHanded = b), _.twoHanded)

  val wm: MeleeWeaponItem @> Wm =
    Lens.lensu((a,b) ⇒ a.copy(wm = b), _.wm)

  val ini: MeleeWeaponItem @> Int =
    Lens.lensu((a,b) ⇒ a.copy(ini = b), _.ini)

  val dk: MeleeWeaponItem @> DistanceClass =
    Lens.lensu((a,b) ⇒ a.copy(dk = b), _.dk)

  val length: MeleeWeaponItem @> Long =
    Lens.lensu((a,b) ⇒ a.copy(length = b), _.length)
}

// vim: set ts=2 sw=2 et:
