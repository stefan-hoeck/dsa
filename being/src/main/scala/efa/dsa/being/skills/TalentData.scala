package efa.dsa.being.skills

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.dsa.world.RaisingCost
import efa.rpg.core.Util
import org.scalacheck.Arbitrary, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class TalentData (
  id: Int,
  tap: Int,
  raisingCost: RaisingCost,
  specialExp: Boolean
) {
  require(Tap validate tap isRight)
}

object TalentData extends Util {
  val default = TalentData(0, 0, !!, false)

  implicit val TalentDataDefault = Default default default

  implicit val TalentDataEqual = Equal.equalA[TalentData]

  implicit val TalentDataArbitrary = Arbitrary(
    ^^^(a[Int], Tap.gen, a[RaisingCost], a[Boolean])(TalentData.apply)
  )

  implicit val TalentDataToXml = new ToXml[TalentData] {
    def fromXml (ns: Seq[Node]) =
      ^^^(ns.readTag[Int]("parentId"),
        Tap read ns,
        ns.tagged[RaisingCost],
        ns.readTag[Boolean]("special"))(TalentData.apply)

    def toXml (a: TalentData) = 
      ("parentId" xml a.id) ++
      Tap.write(a.tap) ++
      xml(a.raisingCost) ++
      ("special" xml a.specialExp)
  }

  implicit val TalentDataSkillData = skillData[TalentData](Lens.self)

  def read (ns: Seq[Node]) = TalentDataToXml fromXml ns

  def write (sd: TalentData) = TalentDataToXml toXml sd

  val id: TalentData @> Int = Lens.lensu((a,b) ⇒ a.copy(id = b), _.id)

  val tap: TalentData @> Int = Lens.lensu((a,b) ⇒ a.copy(tap = b), _.tap)

  val raisingCost: TalentData @> RaisingCost =
    Lens.lensu((a,b) ⇒ a.copy(raisingCost = b), _.raisingCost)

  val specialExp: TalentData @> Boolean =
    Lens.lensu((a,b) ⇒ a.copy(specialExp = b), _.specialExp)
}

// vim: set ts=2 sw=2 et:
