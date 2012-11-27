package efa.dsa.being.ui.generation

import efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{Hero, HeroData ⇒ HD, loc ⇒ bLoc}
import efa.dsa.being.generation._
import efa.dsa.being.ui.NodePanel
import efa.dsa.world.Attribute
import efa.nb.VSET
import efa.rpg.core.RpgEnum
import efa.rpg.being.BeingPanel
import org.openide.util.Lookup
import scala.swing.TextField
import scalaz._, Scalaz._, effect.IO

abstract class GenPanel (
  l: HD @> GenData,
  val np: NodePanel[Hero,HD]
) extends BeingPanel[Hero,HD] with Lookup.Provider {
  val nameC = textField("")
  val leC = number
  val auC = number
  val aeC = number
  val mrC = number

  protected def genElems = (cLoc.name above bLoc.le above bLoc.au above
    bLoc.ae above bLoc.mr) beside
    (nameC above leC above auC above aeC above mrC)

  protected def genSet: VSET[Hero,HD] =
    (stringIn(nameC)(l.name) ∙ ((_: Hero).data)) ⊹
    (intIn(leC, Le.validate)(l.le) ∙ (_.data)) ⊹
    (intIn(auC, Au.validate)(l.au) ∙ (_.data)) ⊹
    (intIn(aeC, Ae.validate)(l.ae) ∙ (_.data)) ⊹
    (intIn(mrC, Mr.validate)(l.mr) ∙ (_.data)) ⊹
    np.set

  override def getLookup = np.getLookup
}

final class ProfessionPanel (np: NodePanel[Hero,HD])
   extends GenPanel(HD.base.profession, np) {

  genElems above (np fillH 2 fillV 1) add()

  def set = genSet
}

final class GenAttributesPanel (
  l: HD @> GenDataAttributes,
  np: NodePanel[Hero,HD]
) extends GenPanel(l.data, np) {
  type Panel = (Attribute, TextField)

  val ps = RpgEnum[Attribute].values map toPanel

  genElems above (ps foldMap elem) above (np fillH 2 fillV 1) add()

  def set = genSet ⊹ (ps foldMap aSet contramap (_.data))

  private def toPanel (a: Attribute) = (a, number)

  private def elem (p: Panel): Elem = p._1.loc.locName beside p._2

  private def aSet (p: Panel): VSET[HD,HD] =
    longIn(p._2, Attributes.validator)(l.attributes at p._1)
}

object GenPanel {
  val locs = List(cLoc.valueLoc)

  def race: IO[GenPanel] = for {
    n ← NodePanel[Hero,HD](ProtoNodes.all(HD.base.race.data),
          "DSA_raceGeneration_NodePanel", locs)
  } yield new GenAttributesPanel(HD.base.race, n)

  def culture: IO[GenPanel] = for {
    n ← NodePanel[Hero,HD](ProtoNodes.all(HD.base.culture.data),
          "DSA_cultureGeneration_NodePanel", locs)
  } yield new GenAttributesPanel(HD.base.culture, n)

  def profession: IO[GenPanel] = for {
    n ← NodePanel[Hero,HD](ProtoNodes.all(HD.base.profession),
          "DSA_professionGeneration_NodePanel", locs)
  } yield new ProfessionPanel(n)
}

// vim: set ts=2 sw=2 et:
