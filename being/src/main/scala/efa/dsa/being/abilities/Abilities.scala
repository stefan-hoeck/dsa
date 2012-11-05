package efa.dsa.being.abilities

import efa.core.Default
import efa.rpg.core.DB
import scalaz.{Equal, Lens, @>}

case class Abilities (
  advantages: Map[String,Advantage],
  handicaps: Map[String,Handicap],
  feats: Map[String,Feat]
) {
  lazy val advantageList = advantages.toList map (_._2) sortBy (_.name)

  lazy val handicapList = handicaps.toList map (_._2) sortBy (_.name)

  lazy val featList = feats.toList map (_._2) sortBy (_.name)
}

object Abilities {
  lazy val default = Abilities(Map.empty, Map.empty, Map.empty)

  implicit lazy val AbilitiesDefault = Default default default

  implicit val AbilitiesEqual = Equal.equalA[Abilities]

  val advantages: Abilities @> Map[String,Advantage] =
    Lens.lensu((a,b) ⇒ a.copy(advantages = b), _.advantages)

  val handicaps: Abilities @> Map[String,Handicap] =
    Lens.lensu((a,b) ⇒ a.copy(handicaps = b), _.handicaps)

  val feats: Abilities @> Map[String,Feat] =
    Lens.lensu((a,b) ⇒ a.copy(feats = b), _.feats)
}

// vim: set ts=2 sw=2 et:
