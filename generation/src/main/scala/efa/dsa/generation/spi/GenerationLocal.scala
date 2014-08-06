package efa.dsa.generation.spi

import efa.core.Default

trait GenerationLocal {
}

object GenerationLocal extends GenerationLocal {
  implicit val defImpl: Default[GenerationLocal] = Default.default(this)
}

// vim: set ts=2 sw=2 et:
