package efa.dsa.being.services

import efa.dsa.being._, calc.calcHero
import efa.rpg.being.BeingLoader

class HeroLoader
   extends BeingLoader("hero", "text/x-hero", HeroLoader.controller)

object HeroLoader {
  val controller = BeingLoader.default[HeroData,Hero,World] (
    heroInfo.get, world, (hd, w) ⇒  calcHero(hd, w.abilities, w.equipment)
  )
}

// vim: set ts=2 sw=2 et:
