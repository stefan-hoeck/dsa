package efa.dsa.being.ui.generation

import dire.swing._, Swing._
import efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{Hero, HeroData ⇒ HD, loc ⇒ bLoc}
import efa.dsa.being.generation._
import efa.dsa.being.ui.{NodePanel, NP}
import efa.dsa.world.Attribute
import efa.nb.VStSF
import efa.rpg.core.RpgEnum
import efa.rpg.being.BeingPanel, BeingPanel._
import scalaz._, Scalaz._, effect.IO

final class GenPanel private(
  val np: NP[Hero,HD],
  val panel: Panel,
  val sf: VStSF[Hero,HD]
)

object GenPanel {
  private type GPPair = (Elem, VStSF[Hero,HD])

  private val locs = List(cLoc.valueLoc)
  private val atts = RpgEnum[Attribute].values

  def culture: IO[GenPanel] = for {
    n  ← NodePanel(ProtoNodes.all(HD.base.culture.data), locs)
    gp ← attPnl(HD.base.culture, n, bLoc.culture)
  } yield gp

  def profession(): IO[GenPanel] = for {
    np  ← NodePanel(ProtoNodes all HD.base.profession, locs)
    gpp ← genPair(HD.base.profession, np)
    p   ← Panel(border := Border.title(bLoc.profession))
    _   ← gpp._1 ^^ np.fillH(2).fillV(1) addTo p
  } yield new GenPanel(np, p, gpp._2)

  def race: IO[GenPanel] = for {
    n  ← NodePanel(ProtoNodes.all(HD.base.race.data), locs)
    gp ← attPnl(HD.base.race, n, bLoc.race)
  } yield gp

  private def genPair(l: HD @> GenData, np: NP[Hero,HD]): IO[GPPair] = for {
    name ← TextField text ""
    le   ← numeric
    au   ← numeric
    ae   ← numeric
    mr   ← numeric

    elem = (cLoc.name ^^ bLoc.le ^^ bLoc.au ^^ bLoc.ae ^^ bLoc.mr) <>
           (name ^^ le ^^ au ^^ ae ^^ mr)

    sf   = np.sf ⊹ 
           (stringSf(name.sfE)(l >=> GenData.name) ∙ Hero.data.get) ⊹
           (intSf(le.sfE, Le.validate)(l.le) ∙ Hero.data.get) ⊹
           (intSf(au.sfE, Au.validate)(l.au) ∙ Hero.data.get) ⊹
           (intSf(ae.sfE, Ae.validate)(l.ae) ∙ Hero.data.get) ⊹
           (intSf(mr.sfE, Mr.validate)(l.mr) ∙ Hero.data.get)
  } yield (elem, sf)

  private def attPnl(l: HD @> GenDataAttributes,
                    np: NP[Hero,HD], title: String): IO[GenPanel] = for {
    gpp   ← genPair(l.data, np)
    attPs ← atts traverse attPanel
    p     ← Panel(border := Border.title(title))
    _     ← gpp._1 ^^ attPs.foldMap(attElem) ^^ np.fillH(2).fillV(1) addTo p

    sf    = gpp._2 ⊹ (attPs foldMap attSf(l) contramap Hero.data.get)
  } yield new GenPanel(np, p, sf)


  private def attPanel(a: Attribute) = numeric map ((a, _))

  private def attElem(p: (Attribute, TextField)) = p._1.loc.locName <> p._2

  private def attSf(l: HD @> GenDataAttributes)(p: (Attribute, TextField)) = 
    longSf(p._2.sfE, Attributes.validator)(l.attributes at p._1)
}

// vim: set ts=2 sw=2 et:
