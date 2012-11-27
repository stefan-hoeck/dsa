package efa.dsa.being

import efa.core.ValSt
import efa.dsa.being.{HeroData ⇒ HD}
import efa.dsa.world.Attribute
import abilities.HasAbilities
import equipment.HasEquipment
import efa.rpg.core.{Modified, Described}
import scalaz.@>

trait AsHero[A]
   extends AsHumanoid[A]
   with HasAbilities[A]
   with HasEquipment[A] 
   with Described[A] {
  def attributes: A @> HeroAttributes
  def baseData (a: A): HeroBaseData = heroData(a).base
  def bought (a: A): Attributes = heroData(a).bought
  def boughtAe (a: A): Long = heroData(a).boughtAe
  def boughtAtt (at: Attribute)(a: A): Long = heroData(a) bought at
  def boughtAu (a: A): Long = heroData(a).boughtAu
  def boughtKe (a: A): Long = heroData(a).boughtKe
  def boughtLe (a: A): Long = heroData(a).boughtLe
  def boughtMr (a: A): Long = heroData(a).boughtMr
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
  def name (h: Hero) = h.data.base.name

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

  def shortDesc (h: Hero) = h.data.base.desc
}

trait AsHeroFunctions extends AsHumanoidFunctions {
  import efa.dsa.being.{AsHero ⇒ AH}

  def baseData[A:AH] (a: A): HeroBaseData = AH[A] baseData a
  def bought[A:AH] (a: A): Attributes = AH[A] bought a
  def boughtAe[A:AH] (a: A): Long = AH[A] boughtAe a
  def boughtAtt[A:AH] (at: Attribute)(a: A): Long = AH[A].boughtAtt(at)(a)
  def boughtAu[A:AH] (a: A): Long = AH[A] boughtAu a
  def boughtKe[A:AH] (a: A): Long = AH[A] boughtKe a
  def boughtLe[A:AH] (a: A): Long = AH[A] boughtLe a
  def boughtMr[A:AH] (a: A): Long = AH[A] boughtMr a
  def heroData[A:AH] (a: A): HD = AH[A] heroData a
  def maxBought[A:AH](at: Attribute)(a: A): Long = AH[A].maxBought(at)(a)
  def maxBoughtAe[A:AH] (a: A): Long = AH[A] maxBoughtAe a
  def maxBoughtAu[A:AH] (a: A): Long = AH[A] maxBoughtAu a
  def maxBoughtKe[A:AH] (a: A): Long = AH[A] maxBoughtKe a
  def maxBoughtLe[A:AH] (a: A): Long = AH[A] maxBoughtLe a
  def maxBoughtMr[A:AH] (a: A): Long = AH[A] maxBoughtMr a
  def setBought[A:AH](at: Attribute)(a: A, l: Long): ValSt[HD] =
    AH[A].setBought(at)(a, l)
  def setBoughtAe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtAe (a, i)
  def setBoughtAu[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtAu (a, i)
  def setBoughtKe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtKe (a, i)
  def setBoughtLe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtLe (a, i)
  def setBoughtMr[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setBoughtMr (a, i)
}

object AsHero extends AsHeroFunctions {
  def apply[A:AsHero]: AsHero[A] = implicitly
}

// vim: set ts=2 sw=2 et:
