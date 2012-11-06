package efa.dsa.being.ui.abilities

import efa.core._
import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.abilities._
import efa.dsa.being.calc.AbilityLinker
import efa.dsa.being.ui.{Nodes, loc ⇒ uiLoc}
import efa.nb.dialog.DialogEditable
import efa.nb.node.{NodeOut, NbNode ⇒ N, NbChildren ⇒ NC}, NC._
import efa.rpg.core.{Described}
import scalaz._, Scalaz._

object AbilityNodes {
  type AbilitiesOut[A] = NodeOut[A,ValSt[AbilityDatas]]

  lazy val default: AbilitiesOut[Abilities] =
    abilitiesOut(abilityOut, abilityOut, abilityOut)

  def abilitiesOut (
    ao: AbilitiesOut[Advantage],
    ho: AbilitiesOut[Handicap],
    fo: AbilitiesOut[Feat]
  ): AbilitiesOut[Abilities] = NC.children(
    NC singleF parent(ao, bLoc.advantages)(_.advantageList),
    NC singleF parent(ho, bLoc.handicaps)(_.handicapList),
    NC singleF parent(fo, bLoc.feats)(_.featList)
  )

  def abilityOut[A,B](
    implicit L: AbilityLinker[A,B],
    D: DialogEditable[Ability[A,B],B]
  ): AbilitiesOut[Ability[A,B]] = 
    N.destroyEs(L.delete) ⊹
    (N.editDialog(D) withIn (L update (_, _) success)) ⊹
    (N renameEs L.rename contramap (d ⇒ (d, d.nameVal))) ⊹ 
    Nodes.described[Ability[A,B]] ⊹
    Nodes.childActions("ContextActions/DsaAdvantageNode") ⊹
    (
      N.booleanRwProp(bLoc.isActiveLoc.name) contramap (
        (_: Ability[A,B]).isActive) withIn L.setActive
    )

  private def parent[A,B] (
    o: AbilitiesOut[Ability[A,B]], name: String
  )(get: Abilities ⇒ List[Ability[A,B]]) =
    NC.children(NC.uniqueIdF[Ability[A,B],ValSt[AbilityDatas],String](o) ∙ get) ⊹
    N.nameA(name)
}

// vim: set ts=2 sw=2 et:
