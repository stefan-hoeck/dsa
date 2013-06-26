package efa.dsa.being.ui.hero

import dire._, dire.swing._, Swing._
import efa.core._, Efa._
import efa.nb.VStSF
import efa.dsa.being._, efa.dsa.being.{AsHero ⇒ AH}
import efa.dsa.being.ui.{loc ⇒ uiLoc}
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

object HeroHumanoidPanel {
  import AsHero._

  def apply[A:AH](): IO[BeingPanel[A,HeroData,Panel]] = for {
    ae ← numeric
    au ← numeric
    ke ← numeric
    le ← numeric
    
    aeB ← numeric
    auB ← numeric
    keB ← numeric
    leB ← numeric
    mrB ← numeric
      
    aeM ← disabledNumeric
    at  ← disabledNumeric
    auM ← disabledNumeric
    aw  ← disabledNumeric
    fk  ← disabledNumeric
    gs  ← disabledNumeric
    ini ← disabledNumeric
    keM ← disabledNumeric
    leM ← disabledNumeric
    mr  ← disabledNumeric
    pa  ← disabledNumeric
    ws  ← disabledNumeric

    p ← (
          ("" <> uiLoc.actual <> uiLoc.max <> uiLoc.bought <> "" <> uiLoc.actual) ^^
          (LeKey <> le <> leM <> leB <> AtKey <> at) ^^
          (AuKey <> au <> auM <> auB <> PaKey <> pa) ^^
          (AeKey <> ae <> aeM <> aeB <> FkKey <> fk) ^^
          (KeKey <> ke <> keM <> keB <> AwKey <> aw) ^^
          (MrKey <> mr <> "" <> mrB <> IniKey <> ini) ^^
          (WsKey <> ws <> "" <> "" <> GsKey <> gs)
        ).panel(border := Border.title(uiLoc.derived))

  sf = valInHuman(AH.ae[A], AH.setAe[A])(ae) ⊹ 
       valInHuman(AH.au[A], AH.setAu[A])(au) ⊹ 
       valInHuman(AH.ke[A], AH.setKe[A])(ke) ⊹ 
       valInHuman(AH.le[A], AH.setLe[A])(le) ⊹ 
       valInHero(AH.boughtAe[A], AH.setBoughtAe[A])(aeB) ⊹ 
       valInHero(AH.boughtAu[A], AH.setBoughtAu[A])(auB) ⊹ 
       valInHero(AH.boughtKe[A], AH.setBoughtKe[A])(keB) ⊹ 
       valInHero(AH.boughtLe[A], AH.setBoughtLe[A])(leB) ⊹ 
       valInHero(AH.boughtMr[A], AH.setBoughtMr[A])(mrB) ⊹ 
       modifiedProp(AeKey, aeM) ⊹ 
       modifiedProp(AtKey, at) ⊹ 
       modifiedProp(AuKey, auM) ⊹ 
       modifiedProp(AwKey, aw) ⊹ 
       modifiedProp(FkKey, fk) ⊹ 
       modifiedProp(GsKey, gs) ⊹ 
       modifiedProp(IniKey, ini) ⊹ 
       modifiedProp(KeKey, keM) ⊹ 
       modifiedProp(LeKey, leM) ⊹ 
       modifiedProp(MrKey, mr) ⊹ 
       modifiedProp(PaKey, pa) ⊹ 
       modifiedProp(WsKey, ws) ⊹
       maxBoughtTT(AH.maxBoughtAe[A], aeB) ⊹
       maxBoughtTT(AH.maxBoughtAu[A], auB) ⊹
       maxBoughtTT(AH.maxBoughtKe[A], keB) ⊹
       maxBoughtTT(AH.maxBoughtLe[A], leB) ⊹
       maxBoughtTT(AH.maxBoughtMr[A], mrB)
  } yield BeingPanel(p, sf)

  private def valInHero[A:AH](
    get: A ⇒ Long,
    set: (A, Long) ⇒ ValSt[HeroData]
  )(t: TextField): VStSF[A,HeroData] =
    getSet(get)(set, readShow[Long](t.sf))

  private def valInHuman[A:AH](
    get: A ⇒ Long,
    set: (A, Long) ⇒ ValSt[HumanoidData]
  )(t: TextField): VStSF[A,HeroData] =
    valInHero(get, set ∘ (_ ∘ HeroData.humanoid.lifts))(t)

  private def maxBoughtTT[A:AH](f: A ⇒ Long, t: TextField)
    :VStSF[A,HeroData] =
    outOnly(t.tooltip ∙ (uiLoc maxBought f(_).toString some))
}

// vim: set ts=2 sw=2 et:
