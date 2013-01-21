package efa.dsa.generation

import efa.rpg.core.Util
import scalaz._, Scalaz._

case class NamedPrototype (name: String, id: Int, value: Int)

object NamedPrototype extends Util {
  implicit lazy val NamedPrototypeEqual = Equal.equalA[NamedPrototype]

  implicit lazy val NamedPrototypeIntIdL = intIdL (id)

  val name: NamedPrototype @> String =
    Lens.lensu((a,b) ⇒ a.copy(name = b), _.name)

  val id: NamedPrototype @> Int =
    Lens.lensu((a,b) ⇒ a.copy(id = b), _.id)

  val value: NamedPrototype @> Int =
    Lens.lensu((a,b) ⇒ a.copy(value = b), _.value)
}

// vim: set ts=2 sw=2 et:
