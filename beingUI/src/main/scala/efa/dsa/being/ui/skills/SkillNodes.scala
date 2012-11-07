package efa.dsa.being.ui.skills

import efa.core._
import efa.dsa.being.{loc ⇒ bLoc}
import efa.dsa.being.skills._
import efa.dsa.being.calc.SkillLinker
import efa.dsa.being.ui.{Nodes, loc ⇒ uiLoc}
import efa.dsa.world.RaisingCost
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
}
//    
//    override protected def addItem(i: I): Unit = {
//      //add new skill if not yet in Hero's list of skills
//      val sdNow = sd.now
//      //use skillsMutator from linker and not accessor. The accessor might filter part
//      //of the skills (in the case of talents).
//      if (linker.skillsMutator(sdNow).find(_.parentId == i.id).isEmpty) { 
//        setData(linker addS (sdNow, linker item2Skill i))
//      }
//    }
//  }
//  
//  protected def talentsNode(tt: TalentType) = {
//    def acc = new Accessor.Impl[SkilledData, List[Talent]](
//      SkilledData.talentsMut.loc, 
//      (sd: SkilledData) => sd.talents filter (_.talentType == Some(tt)))
//    new ParentNode(TalentLinker, acc, talentNode){
//      setDisplayName(tt.locName)
//    }
//  }
//  protected def languagesNode = 
//    new ParentNode(LanguageLinker, LanguageLinker.skillsMutator, languageNode)
//  protected def scripturesNode = 
//    new ParentNode(ScriptureLinker, ScriptureLinker.skillsMutator, scriptureNode)
//  protected def spellsNode = 
//    new ParentNode(SpellLinker, SpellLinker.skillsMutator, spellNode)
//  protected def ritualsNode = 
//    new ParentNode(RitualLinker, RitualLinker.skillsMutator, ritualNode)
//  protected def meleeTalentsNode = 
//    new ParentNode(MeleeTalentLinker, MeleeTalentLinker.skillsMutator, meleeTalentNode)
//  protected def rangedTalentsNode = 
//    new ParentNode(RangedTalentLinker, RangedTalentLinker.skillsMutator, rangedTalentNode)
//  
//    val modSignal: Signal[Option[M]] ={
//      def modSkill(m: B) =
//        linker modsMutator m.skills find (_.parent.parentId == ss.now.parentId)
//      calcSignal map (modSkill)
//    }
//    modSignal foreach {a => 
//      setShortDescription(a map (_.shortDesc) getOrElse "")
//    }
//    
//    override lazy val controllers = List(tawController, dataController)
//    
//    //displays raising costs and special experience
//    protected lazy val dataController: PropertyController[_] = new PropertyController[S] {
//      override protected def factories = List[Factory[S, _]](
//        selectionRw(raisingCostMut, RaisingCost.values), booleanRw(specialExpMut),
//        textR(SkillLike.ebeAcc, Alignment.Right)
//      )
//    }
//    
//    //displays the (modified) TaW derived from the calcSignal.
//    protected lazy val tawController: PropertyController[_] = 
//      new PropertyController[Option[M]] {
//        override def signal: Signal[Option[M]] = modSignal
//      
//        override protected def factories = List[Factory[Option[M], _]](
//          ModifiedProps.fromList[Option[M]](SkillLike.TapTotal, 
//                                            _.map(_.modifiers) getOrElse Nil)
//        )
//      }
//  
//  protected class TalentNode(a: Signal[Talent]) extends SkillLikeNode(a, TalentLinker) {
//    override lazy val controllers = List(tawController, dataController, attrController)
//    lazy val attrController = new PropertyController[Talent] {
//      override def signal = a
//      protected def factories = List[Factory[Talent, _]](textR(Talent.attributesAcc))
//    }
//  }
//  
//  protected class SpellNode(a: Signal[Spell]) extends SkillLikeNode(a, SpellLinker) {
//    override lazy val controllers = List(tawController, dataController, attrController)
//    lazy val attrController = new PropertyController[Spell] {
//      override def signal = a
//      edits foreach {e => setData(linker updateS (sd.now, e))}
//      protected def factories = List[Factory[Spell, _]](
//        textR(Spell.attributesAcc), textRw(Spell.representationMut),
//        booleanRw(Spell.houseSpellMut)
//      )
//    }
//  }
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
//  
//  protected def talentNode (a: Signal[Talent]) = new TalentNode(a)
//  protected def languageNode(a: Signal[Language]) = new SkillLikeNode(a, LanguageLinker)
//  protected def scriptureNode (a: Signal[Scripture]) = new SkillLikeNode(a, ScriptureLinker) 
//  protected def spellNode (a: Signal[Spell]) = new SpellNode(a) 
//  protected def ritualNode (a: Signal[Ritual]) = new SkillLikeNode(a, RitualLinker) 
//  protected def meleeTalentNode(a: Signal[MeleeTalent]) = new MeleeTalentNode(a)
//  protected def rangedTalentNode(a: Signal[RangedTalent]) = new SkillLikeNode(a, RangedTalentLinker)
//}

// vim: set ts=2 sw=2 et:
