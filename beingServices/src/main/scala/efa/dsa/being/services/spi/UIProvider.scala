package efa.dsa.being.services.spi

import efa.core.Provider
import efa.dsa.being.services.HeroInfo
import scalaz.effect.IO

trait UIProvider extends Provider[IO[HeroInfo]]

// vim: set ts=2 sw=2 et:
