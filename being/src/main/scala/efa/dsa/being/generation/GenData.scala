package efa.dsa.being.generation

import efa.core.{Efa, ToXml, Default}, Efa._
import efa.dsa.being._
import efa.dsa.generation.SkillPrototypes
import efa.rpg.core.{Modifiers, Util}
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class GenData(
  name: String,
  ae: Int,
  au: Int,
  le: Int,
  mr: Int,
  skills: SkillPrototypes
) {
  require(Ae validate ae isRight)
  require(Au validate au isRight)
  require(Le validate le isRight)
  require(Mr validate mr isRight)

  lazy val modifiers: Modifiers = Modifiers (
    AeKey → modSeq(ae, name),
    AuKey → modSeq(au, name),
    LeKey → modSeq(le, name),
    MrKey → modSeq(mr, name)
  )
}

object GenData extends Util {
  val default = GenData("", 0, 0, 0, 0, !!)

  implicit val GenDataDefault = Default default default

  implicit val GenDataEqual = Equal.equalA[GenData]

  implicit val GenDataArbitrary = Arbitrary(
    ^^^^^(Gen.identifier,
      Ae.gen,
      Au.gen,
      Le.gen,
      Mr.gen,
      a[SkillPrototypes])(GenData.apply)
  )

  implicit val GenDataToXml = new ToXml[GenData] {
    def fromXml (ns: Seq[Node]) =
      ^^^^^(ns.readTag[String]("name"),
        Ae read ns,
        Au read ns,
        Le read ns,
        Mr read ns,
        SkillPrototypes read ns)(GenData.apply)

    def toXml (a: GenData) =
      ("name" xml a.name) ++
      Ae.write(a.ae) ++
      Au.write(a.au) ++
      Le.write(a.le) ++
      Mr.write(a.mr) ++
      SkillPrototypes.write(a.skills)
  }

  def read (ns: Seq[Node]) = GenDataToXml fromXml ns

  def write (s: GenData) = GenDataToXml toXml s

  val name: GenData @> String = Lens.lensu((a,b) ⇒ a.copy(name = b), _.name)

  val ae: GenData @> Int = Lens.lensu((a,b) ⇒ a.copy(ae = b), _.ae)

  val au: GenData @> Int = Lens.lensu((a,b) ⇒ a.copy(au = b), _.au)

  val le: GenData @> Int = Lens.lensu((a,b) ⇒ a.copy(le = b), _.le)

  val mr: GenData @> Int = Lens.lensu((a,b) ⇒ a.copy(mr = b), _.mr)

  val skills: GenData @> SkillPrototypes =
    Lens.lensu((a,b) ⇒ a.copy(skills = b), _.skills)

  implicit class GenDataLenses[A](val l: A @> GenData) extends AnyVal {
    def name = l >=> GenData.name
    def ae = l >=> GenData.ae
    def au = l >=> GenData.au
    def le = l >=> GenData.le
    def mr = l >=> GenData.mr
    def skills = l >=> GenData.skills
  }
}

// vim: set ts=2 sw=2 et:
