package efa.dsa.world

import efa.core._, Efa._
import efa.rpg.core.{DB, Util}
import org.scalacheck.Arbitrary
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class EquipmentMaps[Amm,Arm,Art,Mel,Ran,Shi,Zon] (
    ammunition: DB[Amm],
    armor: DB[Arm],
    articles: DB[Art],
    meleeWeapons: DB[Mel],
    rangedWeapons: DB[Ran],
    shields: DB[Shi],
    zoneArmor: DB[Zon])

object EquipmentMaps extends Util {
  implicit def EMDefault[A,B,C,D,E,F,G] = 
    Default default EquipmentMaps[A,B,C,D,E,F,G](db, db, db, db, db, db, db)

  implicit def EMEqual[A:Equal,B:Equal,C:Equal,D:Equal,E:Equal,
                       F:Equal,G:Equal]: Equal[EquipmentMaps[A,B,C,D,E,F,G]] = 
    Equal.equalBy(s ⇒ (s.ammunition, s.armor, s.articles,
      s.meleeWeapons, s.rangedWeapons, s.shields, s.zoneArmor))

  implicit def EMArbitrary[A:Arbitrary:IntIdL,B:Arbitrary:IntIdL,
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
             mapGen[G,Int](0, 10))(EquipmentMaps.apply)
    )

  implicit def EMToXml[A:TaggedToXml:IntId,B:TaggedToXml:IntId,
                       C:TaggedToXml:IntId,D:TaggedToXml:IntId,
                       E:TaggedToXml:IntId,F:TaggedToXml:IntId,
                       G:TaggedToXml:IntId] =
    new ToXml[EquipmentMaps[A,B,C,D,E,F,G]] {
      val asXml = mapToXmlTagged[A,Int]
      val bsXml = mapToXmlTagged[B,Int]
      val csXml = mapToXmlTagged[C,Int]
      val dsXml = mapToXmlTagged[D,Int]
      val esXml = mapToXmlTagged[E,Int]
      val fsXml = mapToXmlTagged[F,Int]
      val gsXml = mapToXmlTagged[G,Int]

      def fromXml (ns: Seq[Node]) =
        ^^^^^^(
          asXml.readTag(ns, "ammunition"),
          bsXml.readTag(ns, "armor"),
          csXml.readTag(ns, "articles"),
          dsXml.readTag(ns, "meleeWeapons"),
          esXml.readTag(ns, "rangedWeapons"),
          fsXml.readTag(ns, "shields"),
          gsXml.readTag(ns, "zoneArmor"))(EquipmentMaps.apply)

      def toXml (a: EquipmentMaps[A,B,C,D,E,F,G]) = 
        asXml.writeTag("ammunition", a.ammunition) ++
        bsXml.writeTag("armor", a.armor) ++
        csXml.writeTag("articles", a.articles) ++
        dsXml.writeTag("meleeWeapons", a.meleeWeapons) ++
        esXml.writeTag("rangedWeapons", a.rangedWeapons) ++
        fsXml.writeTag("shields", a.shields) ++
        gsXml.writeTag("zoneArmor", a.zoneArmor)
    }

  def ammunition[A,B,C,D,E,F,G]: EquipmentMaps[A,B,C,D,E,F,G] @> DB[A] =
    Lens.lensu((a,b) ⇒ a.copy(ammunition = b), _.ammunition)

  def armor[A,B,C,D,E,F,G]: EquipmentMaps[A,B,C,D,E,F,G] @> DB[B] =
    Lens.lensu((a,b) ⇒ a.copy(armor = b), _.armor)

  def articles[A,B,C,D,E,F,G]: EquipmentMaps[A,B,C,D,E,F,G] @> DB[C] =
    Lens.lensu((a,b) ⇒ a.copy(articles = b), _.articles)

  def meleeWeapons[A,B,C,D,E,F,G]: EquipmentMaps[A,B,C,D,E,F,G] @> DB[D] =
    Lens.lensu((a,b) ⇒ a.copy(meleeWeapons = b), _.meleeWeapons)

  def rangedWeapons[A,B,C,D,E,F,G]: EquipmentMaps[A,B,C,D,E,F,G] @> DB[E] =
    Lens.lensu((a,b) ⇒ a.copy(rangedWeapons = b), _.rangedWeapons)

  def shields[A,B,C,D,E,F,G]: EquipmentMaps[A,B,C,D,E,F,G] @> DB[F] =
    Lens.lensu((a,b) ⇒ a.copy(shields = b), _.shields)

  def zoneArmor[A,B,C,D,E,F,G]: EquipmentMaps[A,B,C,D,E,F,G] @> DB[G] =
    Lens.lensu((a,b) ⇒ a.copy(zoneArmor = b), _.zoneArmor)
  
  implicit class Lenses[A,B,C,D,E,F,G,Z](
      val l: Z @> EquipmentMaps[A,B,C,D,E,F,G])
    extends AnyVal {
    def ammunition = l >=> EquipmentMaps.ammunition
    def armor = l >=> EquipmentMaps.armor
    def articles = l >=> EquipmentMaps.articles
    def meleeWeapons = l >=> EquipmentMaps.meleeWeapons
    def rangedWeapons = l >=> EquipmentMaps.rangedWeapons
    def shields = l >=> EquipmentMaps.shields
    def zoneArmor = l >=> EquipmentMaps.zoneArmor
  }
}

// vim: set ts=2 sw=2 et:
