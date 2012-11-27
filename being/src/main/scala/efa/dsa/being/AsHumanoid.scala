package efa.dsa.being

import efa.core.{ValSt, Validators, EndoVal}, Validators.interval
import efa.dsa.being.{HumanoidData ⇒ HD}
import efa.dsa.being.equipment.{Hands, HandsData}
import efa.dsa.world.Attribute
import scalaz._, Scalaz._

trait AsHumanoid[A] extends AsBeing[A] {
  def ae (a: A): Long = maxAe(a) - humanoidData(a).lostAe
  def au (a: A): Long = maxAu(a) - humanoidData(a).lostAu
  def be (a: A): Long = prop(a, BeKey)
  def exhaustion (a: A): Long = humanoidData(a).exhaustion
  def gs (a: A): Long = prop(a, GsKey)
  def hands (a: A): HandsData = humanoidData(a).hands
  def humanoidData (a: A): HD
  def initial (a: A): Attributes = humanoidData(a).initial
  def initialAtt (at: Attribute)(a: A): Long = humanoidData(a) initial at
  def ke (a: A): Long = maxKe(a) - humanoidData(a).lostKe
  def le (a: A): Long = maxLe(a) - humanoidData(a).lostLe
  def mr (a: A): Long = prop(a, MrKey)
  def mrBody (a: A): Long = mr(a)
  def mrMind (a: A): Long = mr(a)
  def setAe (a: A, i: Long): ValSt[HD] = setDamage(maxAe(a), i, HD.lostAe)
  def setAu (a: A, i: Long): ValSt[HD] = setDamage(maxAu(a), i, HD.lostAu)
  def setKe (a: A, i: Long): ValSt[HD] = setDamage(maxKe(a), i, HD.lostKe)
  def setLe (a: A, i: Long): ValSt[HD] = setDamage(maxLe(a), i, HD.lostLe)
  def wounds (a: A): Long = humanoidData(a).wounds
  def zoneWounds (a: A): ZoneWounds = humanoidData(a).zoneWounds
}

trait AsHumanoidFunctions extends AsBeingFunctions {
  import efa.dsa.being.{AsHumanoid ⇒ AH}
  def be[A:AH] (a: A): Long = AH[A] be a
  def gs[A:AH] (a: A): Long = AH[A] gs a
  def hands[A:AH] (a: A): HandsData = AH[A] hands a
  def humanoidData[A:AH] (a: A): HD = AH[A] humanoidData a
  def initial[A:AH] (a: A): Attributes = AH[A] initial a
  def initialAtt[A:AH] (at: Attribute)(a: A): Long = AH[A].initialAtt(at)(a)
  def mr[A:AH] (a: A): Long = AH[A] mr a
  def setAe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setAe (a, i)
  def setAu[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setAu (a, i)
  def setKe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setKe (a, i)
  def setLe[A:AH] (a: A, i: Long): ValSt[HD] = AH[A] setLe (a, i)
  def zoneWounds[A:AH] (a: A): ZoneWounds = AH[A] zoneWounds a
}

object AsHumanoid extends AsHumanoidFunctions {
  def apply[A:AsHumanoid]: AsHumanoid[A] = implicitly
}

// vim: set ts=2 sw=2 et:
