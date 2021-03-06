package efa.dsa.being

import efa.dsa.being.skills.Tap
import efa.dsa.world.Attribute
import efa.rpg.core.{RangeVals, EnumMaps, EnumMap}

package object generation extends RangeVals {
  private val (minBase, maxBase) = (-999, 999)

  type Attributes = efa.dsa.being.Attributes

  val Ae = fullInfo(minBase, maxBase, "ae")
  val Au = fullInfo(minBase, maxBase, "au")
  val Le = fullInfo(minBase, maxBase, "le")
  val Mr = fullInfo(minBase, maxBase, "mr")
  val Value = fullInfo(-99, Tap.max, "value")

  val Attributes = EnumMaps.long[Attribute](-99L, 99L, 0L, "attributes")
}

// vim: set ts=2 sw=2 et:
