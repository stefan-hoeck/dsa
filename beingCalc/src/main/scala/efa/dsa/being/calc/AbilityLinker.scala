package efa.dsa.being.calc

import efa.core.{ValSt, ValRes}
import efa.dsa.abilities._
import efa.dsa.being.abilities._
import efa.dsa.being.HeroData
import efa.rpg.core.{RpgItem, DB}
import scalaz._, Scalaz._

sealed abstract class AbilityLinker[I,D](
  implicit AI:RpgItem[I],
  AD:AbilityData[D]
){

  import AbilityLinker.SMap
  final type A = Ability[I,D]

  def items: Lens[AbilityItems,DB[I]]

  def data: Lens[AbilityDatas,SMap[D]]

  def abilities: Lens[Abilities,SMap[A]]

  def abilityList: Abilities ⇒ List[A] = a ⇒ 
    abilities.get(a).toList map (_._2) sortBy (_.name)
  
  def delete(a: A): State[AbilityDatas,Unit] = data -= a.name void

  def itemToData (i: I): D = (for {
    _ ← AD.idL := AI.id(i)
    _ ← AD.nameL := AI.name(i)
  } yield ()) exec AD.default

  def addI (i: I): State[AbilityDatas,Unit] =
    data += (AI.name(i) → itemToData(i)) void

  def update(a: A, d: D): State[AbilityDatas,Unit] =
    delete(a) >> (data += (AD.name(d) → d) void)

  def rename(a: A, s: String): State[AbilityDatas,Unit] =
    update(a, AD.nameL set (a.data, s))

  def setActive(a: A, b: Boolean): State[AbilityDatas,Unit] =
    update (a, AD.isActiveL set (a.data, b))

  def heroAbilities(h: HeroData, as: AbilityItems): SMap[A] = {
    val abilities = data get h.abilities
    val names = abilities.keySet

    def toAbility(p: (String,D)) = p match {
      case (s,d) ⇒ items get as get AD.id(d) map (i ⇒ (s, Ability(i,d,names)))
    }

    abilities flatMap toAbility
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
