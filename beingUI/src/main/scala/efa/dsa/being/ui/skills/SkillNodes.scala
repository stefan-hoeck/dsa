package efa.dsa.being.ui.skills

import dire.swing.HAlign.Trailing
import efa.core._, Validators.dummy
import efa.dsa.abilities._
import efa.dsa.being.{loc ⇒ bLoc, HeroData ⇒ HD}
import efa.dsa.being.skills._
import efa.dsa.being.calc.SkillLinker
import SkillLinker.{TalentLinker ⇒ TL, MeleeTalentLinker ⇒ MTL}
import efa.dsa.being.ui.{Nodes, loc ⇒ uiLoc}
import efa.dsa.world.{RaisingCost, Ebe}
import efa.nb.dialog.DialogEditable
import efa.nb.node._
import efa.rpg.core.{Described, RpgEnum, prettyMods}
import scalaz._, Scalaz._

object SkillNodes extends StNodeFunctions {
  type VSSD = ValSt[SkillDatas]
  type SkillsOut[A] = ValStOut[A,SkillDatas]
  type HDOut[A] = ValStOut[A,HD]

  import implicits._

  def skillOut[A,B](
    implicit L: SkillLinker[A,B],
    D: DialogEditable[Skill[A,B],B]
  ): SkillsOut[Skill[A,B]] = 
    destroyEs(L.delete) ⊹
    (editE(D) map (L add _ success)) ⊹
    Nodes.described[Skill[A,B],VSSD] ⊹
    Nodes.childActions("ContextActions/DsaSkillNode") ⊹
    sg(L.special)(_.special)(booleanRw(bLoc.specialExpLoc.name)) ⊹ 
    sg(L.raisingCost)(_.rc)(comboRw(RpgEnum[RaisingCost].values,
      bLoc.raisingCostLoc.name)) ⊹ 
    textW[Skill[A,B],Int,VSSD](bLoc.tawLoc.name,
      _.taw, _.taw.toString,
      s ⇒ Some(prettyMods(s.taw, s.modifiers)), Trailing)

  lazy val languageOut: SkillsOut[Language] = skillOut

  lazy val meleeOut: SkillsOut[MeleeTalent] =
    skillOut[MeleeTalentItem,MeleeTalentData] ⊹
    showTrailingNO[Ebe,MeleeTalent](bLoc.ebeLoc.name)(_.item.ebe) ⊹
    (intRw(bLoc.atLoc.name, dummy) contramap MTL.at withIn MTL.setAt) ⊹
    (intRw(bLoc.paLoc.name, dummy) contramap MTL.pa withIn MTL.setPa)

  lazy val rangedOut: SkillsOut[RangedTalent] =
    skillOut[RangedTalentItem,TalentData] ⊹
    showTrailingNO[Ebe,RangedTalent](bLoc.ebeLoc.name)(_.item.ebe)

  lazy val ritualOut: SkillsOut[Ritual] = skillOut

  lazy val scriptureOut: SkillsOut[Scripture] = skillOut

  lazy val spellOut: SkillsOut[Spell] =
    skillOut[SpellItem,SpellData] ⊹
    showTrailingNO[Attributes,Spell](bLoc.attributesLoc.name)(
      _.item.attributes)

  lazy val talentOut: SkillsOut[Talent] =
    skillOut[TalentItem,TalentData] ⊹
    showTrailingNO[Ebe,Talent](bLoc.ebeLoc.name)(_.item.ebe) ⊹
    showTrailingNO[Attributes,Talent](bLoc.attributesLoc.name)(
      _.item.attributes)

  val battleOut: HDOut[Skills] = children(
    singleF (parentHD(meleeOut, bLoc.meleeTalents)),
    singleF (parentHD(rangedOut, bLoc.rangedTalents))
  )

  val languagesOut: HDOut[Skills] = children(
    singleF (parentHD(languageOut, bLoc.languages)),
    singleF (parentHD(scriptureOut, bLoc.scriptures))
  )

  val talentsOut: HDOut[Skills] = children(
    RpgEnum[TalentType].values map (tt ⇒
      singleF (talentParentHD(talentOut, tt))): _*
  )

  val spellsOut: HDOut[Skills] = children(
    singleF (parentHD(ritualOut, bLoc.rituals)),
    singleF (parentHD(spellOut, bLoc.spells))
  )

  def parent[A,B] (o: SkillsOut[Skill[A,B]], n: String)
  (implicit L: SkillLinker[A,B], M: Manifest[A]) =
    Nodes.parentNode(n, o)(L.skillList)(L.addI)

  def parentHD[A,B] (o: SkillsOut[Skill[A,B]], n: String)
  (implicit L: SkillLinker[A,B], M: Manifest[A]): HDOut[Skills] = {
    Nodes.parentNode(n, rl(o))(L.skillList)(L.addIHD)
  }

  def rl[A,B](o: SkillsOut[Skill[A,B]])(implicit L: SkillLinker[A,B])
    : HDOut[Skill[A,B]] =
    mapSt[Skill[A,B],SkillDatas,HD](o)(HD.humanoid.skills) ⊹ 
    NodeOut[Skill[A,B],ValSt[HD]](
      (o,n) ⇒ s ⇒ n.updateCookies[LowerCookie] apply 
      LowerCookie(o(L lower s success))) ⊹ 
    NodeOut((o,n) ⇒ s ⇒ n.updateCookies[RaiseCookie] apply 
      RaiseCookie(o(L raise s success)))

  def talentParent (o: SkillsOut[Talent], tt: TalentType) =
    Nodes.parentNode(tt.loc.locName, o)(byTalentType(tt))(TL.addI)

  def talentParentHD (o: SkillsOut[Talent], tt: TalentType) =
    Nodes.parentNode(tt.loc.locName, rl(o))(byTalentType(tt))(TL.addIHD)

  private def byTalentType (tt: TalentType): Skills ⇒ List[Talent] =
    TL.skillList map (_ filter (_.item.talentType ≟ tt))

  private def showTrailingNO[A:Show,B](s: String)(f: B ⇒ A): SkillsOut[B] =
    showWTrailing[A,VSSD](s) contramap f
}


// vim: set ts=2 sw=2 et:
