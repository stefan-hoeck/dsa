package efa.dsa.generation

import efa.core.UniqueId
import scalaz._, Scalaz._

case class NamedPrototype (name: String, id: Int, value: Int)

object NamedPrototype {
  implicit val NamedPrototypeEqual = Equal.equalA[NamedPrototype]

  implicit val NamedPrototypeUniqueId: UniqueId[NamedPrototype,Int] =
    new UniqueId[NamedPrototype,Int] {
      def id (n: NamedPrototype) = n.id
    }
}

// vim: set ts=2 sw=2 et:
