package efa.dsa

import efa.dsa.being.ZoneWounds
import efa.dsa.equipment.{Reach, ZoneRs, TpPlus}
import efa.dsa.world.{Wm, TpKk, BodyPart, RangedDistance}
import efa.dsa.world.mittelreich.{Distance, Coin, Weight}
import efa.rpg.core._
import org.scalacheck._, Arbitrary.arbitrary
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

package object test {

  def rpg[A:RpgItem] = implicitly[RpgItem[A]]

  def itemDatas (names: List[String]): List[ItemData] =
    names.zipWithIndex map {case (n,i) ⇒ ItemData(i, n, "")}

  def enumGen[A:RpgEnum](min: Int, max: Int): Gen[EnumMap[A,Int]] =
    EnumMaps.int[A](min, max, min, "").gen

  lazy val advValueGen = Gen choose (0, 5)

  lazy val beGen = rsGen

  lazy val bfGen = Gen.choose(0, 6)

  lazy val boughtAeGen = Gen choose (0, 10)

  lazy val boughtAuGen = Gen choose (0, 10)

  lazy val boughtKeGen = Gen choose (0, 10)

  lazy val boughtLeGen = Gen choose (0, 10)

  lazy val boughtMrGen = Gen choose (0, 5)

  lazy val complexityGen = Gen.choose(18, 23)

  lazy val countGen = Gen choose (1, 20)

  lazy val exhaustionGen = Gen choose (0, 10)

  lazy val genAeGen = Gen choose (0, 10)

  lazy val genAuGen = Gen choose (0, 10)

  lazy val genLeGen = Gen choose (0, 10)

  lazy val genMrGen = Gen choose (-3, 3)

  lazy val iniGen = Gen.choose(-2, 2)

  lazy val lengthGen =
    Gen choose (Distance.S.multiplier / 10L, Distance.S.multiplier * 2L)

  lazy val lostAeGen = Gen choose (0, 30)

  lazy val lostAuGen = Gen choose (0, 30)

  lazy val lostKeGen = Gen choose (0, 30)

  lazy val lostLeGen = Gen choose (0, 30)

  lazy val priceGen = Gen choose (1L, 10000L)

  lazy val reachGen = enumGen[RangedDistance](1, 200)

  lazy val rsGen = Gen choose (1,8)

  lazy val singleTpPlusGen = Gen choose (-10, 2)

  lazy val skillProtoGen = Gen choose (1, 7)

  lazy val talentGen = Gen oneOf AbilityGens.meleeTalentNames

  lazy val tapGen = Gen choose (0, 20)

  lazy val tpGen = 
    ^^(Gen.choose(1,2),
      Gen.oneOf(4, 6, 8, 10, 20),
      Gen.choose(-1, 10))(DieRoller.apply)

  lazy val tpkkGen = ^(Gen.choose(10,15), Gen.choose(2,5))(TpKk.apply)

  lazy val tpPlusGen = enumGen[RangedDistance](-10, 2)

  lazy val timeToLoadGen = Gen choose (1, 20)

  lazy val weightGen =
    Gen choose (Weight.U.multiplier, 100L * Weight.ST.multiplier)

  lazy val woundsGen = Gen choose (0, 3)

  lazy val wmGen = ^(Gen.choose(-2, 2), Gen.choose(-2, 2))(Wm.apply)

  lazy val zoneRsGen = enumGen[BodyPart](0, 8)

  lazy val zoneWoundsGen = enumGen[BodyPart](0, 3)
}

// vim: set ts=2 sw=2 et:
