package efa.dsa.being.ui.skills

import efa.core._
import efa.dsa.abilities._
import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.skills._
import efa.dsa.being.calc.SkillLinker
import SkillLinker.{TalentLinker ⇒ TL}
import efa.dsa.being.ui.{Nodes, loc ⇒ uiLoc}
import efa.dsa.world.{RaisingCost, Ebe}
import efa.nb.dialog.DialogEditable
import efa.nb.node.{NodeOut, NbNode ⇒ N, NbChildren ⇒ NC}, NC._
import efa.rpg.core.{Described, RpgEnum, prettyMods}
import scalaz._, Scalaz._
import scala.swing.Alignment.Trailing

object SkillNodes {
  type SkillsOut[A] = NodeOut[A,ValSt[SkillDatas]]

  def skillOut[A,B](
    implicit L: SkillLinker[A,B],
    D: DialogEditable[Skill[A,B],B]
  ): SkillsOut[Skill[A,B]] = 
    N.destroyEs(L.delete) ⊹
    (N.editDialog(D) map (L add _ success)) ⊹
    Nodes.described[Skill[A,B]] ⊹
    Nodes.childActions("ContextActions/DsaSkillNode") ⊹
    N.booleanRwPropSetGet(L.special)(_.special, bLoc.specialExpLoc.name) ⊹
    N.comboRwPropSetGet(L.raisingCost)(
      _.rc, RpgEnum[RaisingCost].values, bLoc.raisingCostLoc.name
    ) ⊹
    N.textProp(bLoc.tawLoc.name, _.taw, _.taw.toString,
               s ⇒ Some(prettyMods(s.taw, s.modifiers)), Trailing)

  lazy val languageOut: SkillsOut[Language] = skillOut

  lazy val meleeOut: SkillsOut[MeleeTalent] =
    skillOut[MeleeTalentItem,MeleeTalentData] ⊹
    (N.showPropTrailing[Ebe](bLoc.ebeLoc.name) ∙ (_.item.ebe)) ⊹
    (N.intProp(bLoc.atLoc.name) ∙ (_.skill.at)) ⊹
    (N.intProp(bLoc.paLoc.name) ∙ (t ⇒ t.taw - t.skill.at))

  lazy val rangedOut: SkillsOut[RangedTalent] =
    skillOut[RangedTalentItem,TalentData] ⊹
    (N.showPropTrailing[Ebe](bLoc.ebeLoc.name) ∙ (_.item.ebe))

  lazy val ritualOut: SkillsOut[Ritual] = skillOut

  lazy val scriptureOut: SkillsOut[Scripture] = skillOut

  lazy val spellOut: SkillsOut[Spell] =
    skillOut[SpellItem,SpellData] ⊹
    (N.showPropTrailing[Attributes](bLoc.attributesLoc.name) ∙ (
      _.item.attributes))

  lazy val talentOut: SkillsOut[Talent] =
    skillOut[TalentItem,TalentData] ⊹
    (N.showPropTrailing[Ebe](bLoc.ebeLoc.name) ∙ (_.item.ebe)) ⊹
    (N.showPropTrailing[Attributes](bLoc.attributesLoc.name) ∙ (
      _.item.attributes))

  val battleOut: SkillsOut[Skills] = NC.children(
    NC singleF parent(meleeOut, bLoc.meleeTalents),
    NC singleF parent(rangedOut, bLoc.rangedTalents)
  )

  val languagesOut: SkillsOut[Skills] = NC.children(
    NC singleF parent(languageOut, bLoc.languages),
    NC singleF parent(scriptureOut, bLoc.scriptures)
  )

  val talentsOut: SkillsOut[Skills] = NC.children(
    RpgEnum[TalentType].values map (tt ⇒
      NC singleF talentParent(talentOut, tt)): _*)

  val spellsOut: SkillsOut[Skills] = NC.children(
    NC singleF parent(ritualOut, bLoc.rituals),
    NC singleF parent(spellOut, bLoc.spells)
  )

  def parent[A,B] (o: SkillsOut[Skill[A,B]], n: String)
  (implicit L: SkillLinker[A,B], M: Manifest[A]) =
    Nodes.parentNode(n, o)(L.skillList)(L.addI)

  def talentParent (o: SkillsOut[Talent], tt: TalentType) =
    Nodes.parentNode(tt.loc.locName, o)(byTalentType(tt))(TL.addI)

  private def byTalentType (tt: TalentType): Skills ⇒ List[Talent] =
    TL.skillList map (_ filter (_.item.talentType ≟ tt))
}
//        textR(Spell.attributesAcc), textRw(Spell.representationMut),
//        booleanRw(Spell.houseSpellMut)
//  
//  protected class MeleeTalentNode (a: Signal[MeleeTalent])
//  extends SkillLikeNode(a, MeleeTalentLinker) {
//    override lazy val controllers = 
//      List(tawController, dataController, atController, paController)
//    lazy val atController = new PropertyController[MeleeTalent] {
//      override def signal = a
//      protected def factories = List[Factory[MeleeTalent, _]](
//        textRw(MeleeTalent.atMut, Alignment.Right))
//      edits foreach {e => setData(linker updateS (sd.now, e))}
//    }
//    lazy val paController = new PropertyController[Option[ModMeleeTalent]] {
//      override def signal = modSignal
//      protected def factories = List[Factory[Option[ModMeleeTalent], _]](
//        textR(SkilledComponent.paAcc, Alignment.Right))
//    }
//  }

// vim: set ts=2 sw=2 et:
