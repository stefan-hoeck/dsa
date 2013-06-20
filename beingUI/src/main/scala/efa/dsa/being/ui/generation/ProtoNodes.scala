package efa.dsa.being.ui.generation

import efa.core.{loc ⇒ cLoc}
import efa.dsa.abilities._
import efa.dsa.being.{Hero, HeroData, loc ⇒ bLoc}
import efa.dsa.being.generation.GenData
import efa.dsa.being.skills._
import efa.dsa.being.calc.SkillLinker
import efa.dsa.generation.{NamedPrototype, SkillPrototypes, Tap}
import efa.dsa.being.ui.{Nodes, loc ⇒ uiLoc}
import efa.nb.node._
import scalaz._, Scalaz._

object ProtoNodes extends StNodeFunctions {
  type ProtoOut[A] = ValStOut[A,SkillPrototypes]

  def protoOut[A,B](implicit L: SkillLinker[A,B]): ProtoOut[NamedPrototype] = 
    sg(L.protoValue)(_.value)(intRw(cLoc.valueLoc.name, Tap.validate)) ⊹
    name[NamedPrototype](_.name)

  def parent[A,B](l: HeroData @> GenData, n: String)(
    implicit L: SkillLinker[A,B], M: Manifest[A])
    : ValStOut[Hero,HeroData] = {
    
    val o = protoOut[A,B] map (_ map (l.skills lifts _))
    val get = (h: Hero) ⇒ l get h.data

    Nodes.parentNode(n, o)(L.protoList(get))(L.addIProto(l))
  }

  def all(l: HeroData @> GenData): ValStOut[Hero,HeroData] = children (
    singleF (parent[LanguageItem,TalentData](l, bLoc.languages)),
    singleF (parent[MeleeTalentItem,MeleeTalentData](l, bLoc.meleeTalents)),
    singleF (parent[RangedTalentItem,TalentData](l, bLoc.rangedTalents)),
    singleF (parent[RitualItem,TalentData](l, bLoc.rituals)),
    singleF (parent[ScriptureItem,TalentData](l, bLoc.scriptures)),
    singleF (parent[SpellItem,SpellData](l, bLoc.spells)),
    singleF (parent[TalentItem,TalentData](l, bLoc.talents))
  )
}

// vim: set ts=2 sw=2 et:
