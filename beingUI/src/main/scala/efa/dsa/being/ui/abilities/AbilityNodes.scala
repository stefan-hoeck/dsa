package efa.dsa.being.ui.abilities

import efa.core._, efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.abilities._
import efa.dsa.being.abilities.{Value ⇒ V}
import efa.dsa.being.calc.AbilityLinker
import efa.dsa.being.ui.{Nodes, loc ⇒ uiLoc}
import efa.nb.dialog.DialogEditable
import efa.nb.node._
import efa.rpg.core.{Described}
import scalaz._, Scalaz._

object AbilityNodes extends StNodeFunctions {
  type AbilitiesOut[A] = ValStOut[A,AbilityDatas]

  lazy val default: ValStOut[Abilities,AbilityDatas] =
    abilitiesOut(advantageOut, advantageOut, abilityOut)

  def abilitiesOut (
    ao: AbilitiesOut[Advantage],
    ho: AbilitiesOut[Handicap],
    fo: AbilitiesOut[Feat]
  ): AbilitiesOut[Abilities] = children(
    singleF (parent(ao, bLoc.advantages)),
    singleF (parent(ho, bLoc.handicaps)),
    singleF (parent(fo, bLoc.feats))
  )

  def abilityOut[A,B](
    implicit L: AbilityLinker[A,B],
    D: DialogEditable[Ability[A,B],B]
  ): AbilitiesOut[Ability[A,B]] = 
    destroyEs(L.delete) ⊹
    (editDialog(D) withIn (L update (_, _) success)) ⊹
    (renameEs (L.rename) contramap (d ⇒ (d, d.nameVal))) ⊹ 
    Nodes.described[Ability[A,B]] ⊹
    Nodes.childActions("ContextActions/DsaAdvantageNode") ⊹
    sg(L.setActive)(_.isActive)(booleanRw(bLoc.isActiveLoc.name))

  def advantageOut[A](
    implicit L: AbilityLinker[A,AdvantageData],
    D: DialogEditable[Ability[A,AdvantageData],AdvantageData]
  ): AbilitiesOut[Ability[A,AdvantageData]] =
    abilityOut ⊹ 
    sg(L set AdvantageData.value)(_.data.value)(
      intRw(cLoc.valueLoc.name, V.validate))

  private def parent[A,B] (o: AbilitiesOut[Ability[A,B]], n: String)
  (implicit L: AbilityLinker[A,B], M: Manifest[A]) =
    Nodes.parentNode(n, o)(L.abilityList)(L.addI)
}

// vim: set ts=2 sw=2 et:
