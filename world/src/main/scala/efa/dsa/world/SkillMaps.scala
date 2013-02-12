package efa.dsa.world

import efa.core._, Efa._
import efa.rpg.core.{DB, Util}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class SkillMaps[Lan,Mel,Ran,Rit,Scr,Spe,Tal](
    languages: DB[Lan],
    meleeTalents: DB[Mel],
    rangedTalents: DB[Ran],
    rituals: DB[Rit],
    scriptures: DB[Scr],
    spells: DB[Spe],
    talents: DB[Tal])

object SkillMaps extends Util {
  implicit def SMDefault[A,B,C,D,E,F,G] = 
    Default default SkillMaps[A,B,C,D,E,F,G](db, db, db, db, db, db, db)

  implicit def SMEqual[A:Equal,B:Equal,C:Equal,D:Equal,E:Equal,
                       F:Equal,G:Equal]: Equal[SkillMaps[A,B,C,D,E,F,G]] = 
    Equal.equalBy(SkillMaps unapply _)

  implicit def SMArbitrary[A:Arbitrary:IntIdL,B:Arbitrary:IntIdL,
                           C:Arbitrary:IntIdL,D:Arbitrary:IntIdL,
                           E:Arbitrary:IntIdL,F:Arbitrary:IntIdL,
                           G:Arbitrary:IntIdL] =
    Arbitrary(
      ^^^^^^(mapGen[A,Int](0, 10),
             mapGen[B,Int](0, 10),
             mapGen[C,Int](0, 10),
             mapGen[D,Int](0, 10),
             mapGen[E,Int](0, 10),
             mapGen[F,Int](0, 10),
             mapGen[G,Int](0, 10))(SkillMaps.apply)
    )

  implicit def SMToXml[A:TaggedToXml:IntId,B:TaggedToXml:IntId,
                       C:TaggedToXml:IntId,D:TaggedToXml:IntId,
                       E:TaggedToXml:IntId,F:TaggedToXml:IntId,
                       G:TaggedToXml:IntId] =
    new ToXml[SkillMaps[A,B,C,D,E,F,G]] {
      val asXml = mapToXmlTagged[A,Int]
      val bsXml = mapToXmlTagged[B,Int]
      val csXml = mapToXmlTagged[C,Int]
      val dsXml = mapToXmlTagged[D,Int]
      val esXml = mapToXmlTagged[E,Int]
      val fsXml = mapToXmlTagged[F,Int]
      val gsXml = mapToXmlTagged[G,Int]

      def fromXml (ns: Seq[Node]) =
        ^^^^^^(
          asXml.readTag(ns, "languages"),
          bsXml.readTag(ns, "meleeTalents"),
          csXml.readTag(ns, "rangedTalents"),
          dsXml.readTag(ns, "rituals"),
          esXml.readTag(ns, "scriptures"),
          fsXml.readTag(ns, "spells"),
          gsXml.readTag(ns, "talents"))(SkillMaps.apply)

      def toXml (a: SkillMaps[A,B,C,D,E,F,G]) = 
        asXml.writeTag("languages", a.languages) ++
        bsXml.writeTag("meleeTalents", a.meleeTalents) ++
        csXml.writeTag("rangedTalents", a.rangedTalents) ++
        dsXml.writeTag("rituals", a.rituals) ++
        esXml.writeTag("scriptures", a.scriptures) ++
        fsXml.writeTag("spells", a.spells) ++
        gsXml.writeTag("talents", a.talents)
    }

  def languages[A,B,C,D,E,F,G]: SkillMaps[A,B,C,D,E,F,G] @> DB[A] =
    Lens.lensu((a,b) ⇒ a.copy(languages = b), _.languages)

  def meleeTalents[A,B,C,D,E,F,G]: SkillMaps[A,B,C,D,E,F,G] @> DB[B] =
    Lens.lensu((a,b) ⇒ a.copy(meleeTalents = b), _.meleeTalents)

  def rangedTalents[A,B,C,D,E,F,G]: SkillMaps[A,B,C,D,E,F,G] @> DB[C] =
    Lens.lensu((a,b) ⇒ a.copy(rangedTalents = b), _.rangedTalents)

  def rituals[A,B,C,D,E,F,G]: SkillMaps[A,B,C,D,E,F,G] @> DB[D] =
    Lens.lensu((a,b) ⇒ a.copy(rituals = b), _.rituals)

  def scriptures[A,B,C,D,E,F,G]: SkillMaps[A,B,C,D,E,F,G] @> DB[E] =
    Lens.lensu((a,b) ⇒ a.copy(scriptures = b), _.scriptures)

  def spells[A,B,C,D,E,F,G]: SkillMaps[A,B,C,D,E,F,G] @> DB[F] =
    Lens.lensu((a,b) ⇒ a.copy(spells = b), _.spells)

  def talents[A,B,C,D,E,F,G]: SkillMaps[A,B,C,D,E,F,G] @> DB[G] =
    Lens.lensu((a,b) ⇒ a.copy(talents = b), _.talents)
  
  implicit class Lenses[A,B,C,D,E,F,G,Z](
      val l: Z @> SkillMaps[A,B,C,D,E,F,G])
    extends AnyVal {
    def languages = l >=> SkillMaps.languages
    def meleeTalents = l >=> SkillMaps.meleeTalents
    def rangedTalents = l >=> SkillMaps.rangedTalents
    def rituals = l >=> SkillMaps.rituals
    def scriptures = l >=> SkillMaps.scriptures
    def spells = l >=> SkillMaps.spells
    def talents = l >=> SkillMaps.talents
  }
}

// vim: set ts=2 sw=2 et:
