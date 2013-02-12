package efa.dsa.world

import efa.core._, Efa._
import efa.rpg.core.{Util}
import org.scalacheck.{Arbitrary, Gen}
import scala.xml.Node
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

case class AbilityMaps[Adv,Han,Fea](
  advantages: StringMap[Adv],
  handicaps: StringMap[Han],
  feats: StringMap[Fea])

object AbilityMaps extends Util {
  private def e[A,B] = Map.empty[A,B]

  implicit def AMDefault[A,B,C] = Default default AbilityMaps[A,B,C](e, e, e)

  implicit def AMEqual[A:Equal,B:Equal,C:Equal]
    : Equal[AbilityMaps[A,B,C]] = 
    Equal.equalBy(s ⇒ (s.advantages, s.handicaps, s.feats))

  implicit def AMArbitrary[A:Arbitrary:StringId,
                           B:Arbitrary:StringId,
                           C:Arbitrary:StringId] =
    Arbitrary(
      ^^(stringMapG[A], stringMapG[B], stringMapG[C])(AbilityMaps.apply)
    )

  implicit def AMToXml[A:TaggedToXml:StringId,
                       B:TaggedToXml:StringId,
                       C:TaggedToXml:StringId] =
    new ToXml[AbilityMaps[A,B,C]] {
      val asXml = mapToXmlTagged[A,String]
      val bsXml = mapToXmlTagged[B,String]
      val csXml = mapToXmlTagged[C,String]

      def fromXml (ns: Seq[Node]) =
        ^^(
          asXml.readTag(ns, "advantages"),
          bsXml.readTag(ns, "handicaps"),
          csXml.readTag(ns, "feats"))(AbilityMaps.apply)

      def toXml (a: AbilityMaps[A,B,C]) = 
        asXml.writeTag("advantages", a.advantages) ++
        bsXml.writeTag("handicaps", a.handicaps) ++
        csXml.writeTag("feats", a.feats)
    }

  def advantages[A,B,C]: AbilityMaps[A,B,C] @> StringMap[A] =
    Lens.lensu((a,b) ⇒ a.copy(advantages = b), _.advantages)

  def handicaps[A,B,C]: AbilityMaps[A,B,C] @> StringMap[B] =
    Lens.lensu((a,b) ⇒ a.copy(handicaps = b), _.handicaps)

  def feats[A,B,C]: AbilityMaps[A,B,C] @> StringMap[C] =
    Lens.lensu((a,b) ⇒ a.copy(feats = b), _.feats)
  
  implicit class Lenses[A,B,C,Z](val l: Z @> AbilityMaps[A,B,C])
    extends AnyVal {
    def advantages = l >=> AbilityMaps.advantages
    def handicaps = l >=> AbilityMaps.handicaps
    def feats = l >=> AbilityMaps.feats
  }

  private def stringMapG[A:Arbitrary:StringId]: Gen[StringMap[A]] = for {
      i  ← Gen choose (0, 10)
      as ← Gen listOfN (i, a[A]) 
    } yield StringId[A] idMap as
}

// vim: set ts=2 sw=2 et:
