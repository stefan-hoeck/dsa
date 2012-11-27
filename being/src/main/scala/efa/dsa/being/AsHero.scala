package efa.dsa.being

import efa.core.ValSt
import efa.dsa.being.{HeroData ⇒ HD}
import efa.dsa.world._
import abilities.HasAbilities
import equipment.HasEquipment
import efa.rpg.core.{Modified, Described}
import scalaz._, Scalaz._

trait AsHero[A]
   extends AsHumanoid[A]
   with HasAbilities[A]
   with HasEquipment[A] 
   with Described[A] {
  def ap (a: A): Long = baseData(a).ap
  def apUsed (a: A): Long = baseData(a).apUsed
  def attributes: A @> HeroAttributes
  def baseData (a: A): HeroBaseData = heroData(a).base
  def bought (a: A): Attributes = heroData(a).bought
  def boughtAe (a: A): Long = heroData(a).boughtAe
  def boughtAtt (at: Attribute)(a: A): Long = heroData(a) bought at
  def boughtAu (a: A): Long = heroData(a).boughtAu
  def boughtKe (a: A): Long = heroData(a).boughtKe
  def boughtLe (a: A): Long = heroData(a).boughtLe
  def boughtMr (a: A): Long = heroData(a).boughtMr

  def canRaiseAtt (at: Attribute)(a: A): Boolean =
    (boughtAtt(at)(a) < maxBought(at)(a)) &&
    (raiseAttAp(at)(a) <= restAp(a))

  def derived: A @> HeroDerived
  def desc (a: A) = baseData(a).desc
  def fullDesc (h: Hero) = h.data.base.desc
  def heroData (a: A): HD
  def humanoidData (a: A) = heroData(a).humanoid
  def maxBought(at: Attribute)(a: A): Long = attributes get a maxBought at
  def maxBoughtAe (a: A): Long = derived.get(a).maxBoughtAe
  def maxBoughtAu (a: A): Long = derived.get(a).maxBoughtAu
  def maxBoughtKe (a: A): Long = derived.get(a).maxBoughtKe
  def maxBoughtLe (a: A): Long = derived.get(a).maxBoughtLe
  def maxBoughtMr (a: A): Long = derived.get(a).maxBoughtMr
  def name (a: A) = baseData(a).name

  def raiseAtt (at: Attribute)(a: A): ValSt[HeroData] = {
    def st: State[HeroData,Unit] = for {
      _ ← (HeroData.bought at at) += 1
      _ ← HeroData.base.apUsed += raiseAttAp(at)(a)
      _ ← (HeroData.specialExp at at) := false
    } yield ()

    (if (canRaiseAtt(at)(a)) st else init[HeroData].void) success
  }

  def raiseAttAp (at: Attribute)(a: A): Long = {
    val rc = if (specialExp(a)(at)) RaisingCost.G else RaisingCost.H

    Skt cost (rc, attributes get a immutable at toInt)
  }

  def restAp (a: A): Long = baseData(a).restAp

  def setBought(at: Attribute)(a: A, l: Long): ValSt[HD] =
    setLong(0L, attributes.get(a) maxBought at, l, HD.bought at at)

  def setBoughtAe (a: A, i: Long): ValSt[HD] =
    setLong(0L, maxBoughtAe(a), i, HD.boughtAe)

  def setBoughtAu (a: A, i: Long): ValSt[HD] =
    setLong(0L, maxBoughtAu(a), i, HD.boughtAu)

  def setBoughtKe (a: A, i: Long): ValSt[HD] =
    setLong(0L, maxBoughtKe(a), i, HD.boughtKe)

  def setBoughtLe (a: A, i: Long): ValSt[HD] =
    setLong(0L, maxBoughtLe(a), i, HD.boughtLe)

  def setBoughtMr (a: A, i: Long): ValSt[HD] =
    setLong(0L, maxBoughtMr(a), i, HD.boughtMr)

  def shortDesc (a: A) = desc(a)
  def specialExp (a: A): BoolAtts = heroData(a).specialExp
}

trait AsHeroFunctions extends AsHumanoidFunctions {
  import efa.dsa.being.{AsHero ⇒ AH}

  def ap[A:AH] (a: A): Long = AH[A] ap a
  def apUsed[A:AH] (a: A): Long = AH[A] apUsed a
  def baseData[A:AH] (a: A): HeroBaseData = AH[A] baseData a
  def bought[A:AH] (a: A): Attributes = AH[A] bought a
  def boughtAe[A:AH] (a: A): Long = AH[A] boughtAe a
  def boughtAtt[A:AH] (at: Attribute)(a: A): Long = AH[A].boughtAtt(at)(a)
  def boughtAu[A:AH] (a: A): Long = AH[A] boughtAu a
  def boughtKe[A:AH] (a: A): Long = AH[A] boughtKe a
  def boughtLe[A:AH] (a: A): Long = AH[A] boughtLe a
  def boughtMr[A:AH] (a: A): Long = AH[A] boughtMr a

  def canRaiseAtt[A:AH] (at: Attribute)(a: A): Boolean =
    AH[A].canRaiseAtt(at)(a)

  def heroData[A:AH] (a: A): HD = AH[A] heroData a
  def maxBought[A:AH](at: Attribute)(a: A): Long = AH[A].maxBought(at)(a)
  def maxBoughtAe[A:AH] (a: A): Long = AH[A] maxBoughtAe a
  def maxBoughtAu[A:AH] (a: A): Long = AH[A] maxBoughtAu a
  def maxBoughtKe[A:AH] (a: A): Long = AH[A] maxBoughtKe a
  def maxBoughtLe[A:AH] (a: A): Long = AH[A] maxBoughtLe a
  def maxBoughtMr[A:AH] (a: A): Long = AH[A] maxBoughtMr a

  def raiseAtt[A:AH] (at: Attribute)(a: A): ValSt[HeroData] =
    AH[A].raiseAtt(at)(a)

  def raiseAttAp[A:AH] (at: Attribute)(a: A): Long =
    AH[A].raiseAttAp(at)(a)

  def restAp[A:AH] (a: A): Long = AH[A] restAp a

  def setBought[A:AH](at: Attribute)(a: A, l: Long): ValSt[HD] =
    AH[A].setBought(at)(a, l)

  def setBoughtAe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtAe (a, i)
  def setBoughtAu[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtAu (a, i)
  def setBoughtKe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtKe (a, i)
  def setBoughtLe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtLe (a, i)
  def setBoughtMr[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtMr (a, i)

  def shortDesc[A:AH] (a: A): String = AH[A] shortDesc a
  def specialExp[A:AH] (a: A): BoolAtts = AH[A] specialExp a
}

object AsHero extends AsHeroFunctions {
  def apply[A:AsHero]: AsHero[A] = implicitly
}

// vim: set ts=2 sw=2 et:
