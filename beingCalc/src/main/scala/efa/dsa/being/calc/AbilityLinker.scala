package efa.dsa.being.calc

import efa.dsa.abilities._
import efa.dsa.being.abilities._
import efa.dsa.being.HeroData
import efa.rpg.core.{RpgItem, DB}
import scalaz._, Scalaz._

sealed abstract class AbilityLinker[I,D](
  implicit val AI:RpgItem[I],
  val AD:AbilityData[D]
){

  import AbilityLinker.SMap
  type A = Ability[I,D]

  def items: Lens[AbilityItems,DB[I]]

  def data: Lens[AbilityDatas,SMap[D]]

  def abilities: Lens[Abilities,SMap[A]]
  
  def delete(d: D): State[AbilityDatas,SMap[D]] = data -= (AD name d)

  def update(d: D): State[AbilityDatas,SMap[D]] = data += (AD.name(d) → d)

  def heroAbilities(h: HeroData, as: AbilityItems): SMap[A] = {
    def toAbility(p: (String,D)) = p match {
      case (s,d) ⇒ items get as get AD.id(d) map (i ⇒ (s, Ability(i,d)))
    }

    data get h.abilities flatMap toAbility
  }
}

object AbilityLinker {
  type SMap[X] = Map[String,X]

  def heroAbilities (h: HeroData, as: AbilityItems): Abilities = Abilities (
    AdvantageLinker heroAbilities (h, as),
    HandicapLinker heroAbilities (h, as),
    FeatLinker heroAbilities (h, as)
  )

  private def al[I:RpgItem,D:AbilityData](
    il: Lens[AbilityItems,DB[I]],
    dl: Lens[AbilityDatas,SMap[D]],
    al: Lens[Abilities,SMap[Ability[I,D]]]
  ) = new AbilityLinker[I,D]{
    val items = il
    val data = dl
    val abilities = al
  }

  implicit val AdvantageLinker = al[AdvantageItem,AdvantageData](
    AbilityItems.advantages,
    AbilityDatas.advantages,
    Abilities.advantages
  )

  implicit val HandicapLinker = al[HandicapItem,AdvantageData](
    AbilityItems.handicaps,
    AbilityDatas.handicaps,
    Abilities.handicaps
  )

  implicit val FeatLinker = al[FeatItem,FeatData](
    AbilityItems.feats,
    AbilityDatas.feats,
    Abilities.feats
  )
}

// vim: set ts=2 sw=2 et:
