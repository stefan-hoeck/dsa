package efa.dsa.being

import efa.core.{Efa, ToXml, Default, ValSt}, Efa._
import efa.core.syntax.{string, nodeSeq}
import efa.dsa.being.generation.{GenData, GenDataAttributes}
import efa.rpg.core.{Gender, Util}
import org.scalacheck.{Arbitrary, Gen}, Arbitrary.arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class HeroBaseData(
  name: String,
  gender: Gender,
  race: GenDataAttributes,
  culture: GenDataAttributes,
  profession: GenData,
  position: String,
  title: String,
  so: Int,
  ap: Long,
  birthday: Long,
  height: Long,
  weight: Long,
  eyeColor: String,
  hairColor: String,
  desc: String,
  apUsed: Long
) {
  require(So validate so isRight)
  require(Ap validate ap isRight, "ap")
  require(Height validate height isRight)
  require(Weight validate weight isRight)
  require(apUsedI(ap) validate apUsed isRight, "apUsed")

  lazy val modifiers =
    race.modifiers ⊹ culture.modifiers ⊹ profession.modifiers

  lazy val restAp = ap - apUsed

  def setAp (i: Long): ValSt[HeroBaseData] =
    setLong(apUsed, Long.MaxValue, i, HeroBaseData.ap)

  def setApUsed (i: Long): ValSt[HeroBaseData] =
    setLong(0L, ap, i, HeroBaseData.apUsed)
}

object HeroBaseData extends Util {
  lazy val default = HeroBaseData(loc.hero, !!, !!, !!, !!, "",
    "", So.min, 0, 0L, 0L, 0L, "", "", "", 0)

  implicit val HeroBaseDataDefault = Default default default

  implicit val HeroBaseDataEqual = Equal.equalA[HeroBaseData]

  implicit val HeroBaseDataArbitrary = Arbitrary(
    for {
      name ← Gen.identifier
      gender ← a[Gender]
      race ← a[GenDataAttributes]
      culture ← a[GenDataAttributes]
      profession ← a[GenData]
      position ← Gen.identifier
      title ← Gen.identifier
      so ← So.gen
      ap ← Ap.gen
      birthday ← a[Long]
      height ← Height.gen
      weight ← Weight.gen
      eyeColor ← Gen.identifier
      hairColor ← Gen.identifier
      desc ← Gen.identifier
      apUsed ← apUsedI(ap).gen
    } yield HeroBaseData(name, gender, race, culture, profession, position,
      title, so, ap, birthday, height, weight, eyeColor, hairColor,
      desc, apUsed)
  )

  implicit val HeroBaseDataToXml = new ToXml[HeroBaseData] {
    def fromXml (ns: Seq[Node]) = {
      def fst =
        ^^^(ns.readTag[String]("name"),
          ns.tagged[Gender],
          ns.readTag[GenDataAttributes]("race"),
          ns.readTag[GenDataAttributes]("culture"))(Tuple4.apply)

      def snd =
        ^^^(ns.readTag[GenData]("profession"),
          ns.readTag[String]("position"),
          ns.readTag[String]("title"),
          So read ns)(Tuple4.apply)

      def thrd =
        ^^^^^^(Ap.read(ns),
          ns.readTag[Long]("birthday"),
          Height read ns,
          Weight read ns,
          ns.readTag[String]("eyeColor"),
          ns.readTag[String]("hairColor"),
          ns.readTag[String]("desc"))(Tuple7.apply)

      def total = ^^(fst, snd, thrd)(Tuple3.apply)

      import scalaz.Validation.FlatMap._

      for {
        t ← total
        ((a, b, c, d), (e, f, g, h), (i, j, k, l, m, n, o)) = t
        p ← apUsedI(i).read(ns)
      } yield HeroBaseData(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)
    }

    def toXml (a: HeroBaseData) =
      ("name" xml a.name) ++
      Efa.toXml(a.gender) ++
      ("race" xml a.race) ++
      ("culture" xml a.culture) ++
      ("profession" xml a.profession) ++
      ("position" xml a.position) ++
      ("title" xml a.title) ++
      So.write(a.so) ++
      Ap.write(a.ap) ++
      ("birthday" xml a.birthday) ++
      Height.write(a.height) ++
      Weight.write(a.weight) ++
      ("eyeColor" xml a.eyeColor) ++
      ("hairColor" xml a.hairColor) ++
      ("desc" xml a.desc) ++
      apUsedI(a.ap).write(a.apUsed)
  }

  def read (ns: Seq[Node]) = HeroBaseDataToXml fromXml ns

  def write (h: HeroBaseData) = HeroBaseDataToXml toXml h

  val name: HeroBaseData @> String =
    Lens.lensu((a,b) ⇒ a copy (name = b), _.name)

  val gender: HeroBaseData @> Gender =
    Lens.lensu((a,b) ⇒ a copy (gender = b), _.gender)

  val race: HeroBaseData @> GenDataAttributes =
    Lens.lensu((a,b) ⇒ a copy (race = b), _.race)

  val culture: HeroBaseData @> GenDataAttributes =
    Lens.lensu((a,b) ⇒ a copy (culture = b), _.culture)

  val profession: HeroBaseData @> GenData =
    Lens.lensu((a,b) ⇒ a copy (profession = b), _.profession)

  val position: HeroBaseData @> String =
    Lens.lensu((a,b) ⇒ a.copy(position = b), _.position)

  val title: HeroBaseData @> String =
    Lens.lensu((a,b) ⇒ a copy (title = b), _.title)

  val so: HeroBaseData @> Int = Lens.lensu((a,b) ⇒ a copy (so = b), _.so)

  val ap: HeroBaseData @> Long = Lens.lensu((a,b) ⇒ a copy (ap = b), _.ap)
    
  val birthday: HeroBaseData @> Long =
    Lens.lensu((a,b) ⇒ a copy (birthday = b), _.birthday)

  val height: HeroBaseData @> Long =
    Lens.lensu((a,b) ⇒ a copy (height = b), _.height)

  val weight: HeroBaseData @> Long =
    Lens.lensu((a,b) ⇒ a copy (weight = b), _.weight)

  val eyeColor: HeroBaseData @> String =
    Lens.lensu((a,b) ⇒ a copy (eyeColor = b), _.eyeColor)

  val hairColor: HeroBaseData @> String =
    Lens.lensu((a,b) ⇒ a copy (hairColor = b), _.hairColor)

  val apUsed: HeroBaseData @> Long =
    Lens.lensu((a,b) ⇒ a copy (apUsed = b), _.apUsed)

  val desc: HeroBaseData @> String =
    Lens.lensu((a,b) ⇒ a copy (desc = b), _.desc)

  implicit class HeroBaseDataLenses[A] (val l: A @> HeroBaseData) extends AnyVal {
    def name = l >=> HeroBaseData.name
    def gender = l >=> HeroBaseData.gender
    def race = l >=> HeroBaseData.race
    def culture = l >=> HeroBaseData.culture
    def profession = l >=> HeroBaseData.profession
    def position = l >=> HeroBaseData.position
    def title = l >=> HeroBaseData.title
    def so = l >=> HeroBaseData.so
    def ap = l >=> HeroBaseData.ap
    def birthday = l >=> HeroBaseData.birthday
    def height = l >=> HeroBaseData.height
    def weight = l >=> HeroBaseData.weight
    def eyeColor = l >=> HeroBaseData.eyeColor
    def hairColor = l >=> HeroBaseData.hairColor
    def apUsed = l >=> HeroBaseData.apUsed
    def desc = l >=> HeroBaseData.desc
  }
}

// vim: set ts=2 sw=2 et:
