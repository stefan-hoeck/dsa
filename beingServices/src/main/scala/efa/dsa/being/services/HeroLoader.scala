package efa.dsa.being.services

import efa.dsa.being._, calc.calcHero
import efa.rpg.being.BeingLoader
import scalaz._, Scalaz._

class HeroLoader
  extends BeingLoader("hero", "text/x-hero", HeroLoader.controller)

object HeroLoader {
  val controller = BeingLoader.default[HeroData,Hero,World](
    heroInfo,
    world,
    (hd, w) ⇒  calcHero(hd, w.abilities, w.equipment),
    heroRules
  )
}

// vim: set ts=2 sw=2 et:
