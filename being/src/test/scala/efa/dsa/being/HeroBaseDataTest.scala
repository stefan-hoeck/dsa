package efa.dsa.being

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop
import scalaz._, Scalaz._

object HeroBaseDataTest extends ToXmlProps[HeroBaseData]("HeroBaseData"){
  property("validateSo") =
    Prop forAll validated(HeroBaseData.so.set)(So.validate)

  property("validateHeight") =
    Prop forAll validated(HeroBaseData.height.set)(Height.validate)

  property("validateWeight") =
    Prop forAll validated(HeroBaseData.weight.set)(Weight.validate)

  property("validateAp") = Prop forAll {t: (HeroBaseData, Long, Long) ⇒
    val (h, ap, used) = t
    val valRes = for {
      _ ← Ap validate ap
      _ ← apUsedI(ap) validate used
    } yield ()

    val exp = valRes.isRight

    val res = try {
      def set = for {
        _ ← HeroBaseData.apUsed := 0L //make sure that old used is valid
        _ ← HeroBaseData.ap := ap
        _ ← HeroBaseData.apUsed := used
      } yield ()

      val doSet = set eval h

      true
    } catch {case e: IllegalArgumentException ⇒ false}

    exp ≟ res
  }
}

// vim: set ts=2 sw=2 et:
