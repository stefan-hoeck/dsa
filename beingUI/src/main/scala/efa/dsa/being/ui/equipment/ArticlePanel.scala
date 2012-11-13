package efa.dsa.being.ui.equipment

import efa.dsa.being.equipment._
import efa.nb.VSIn
import scalaz._, Scalaz._, effect.IO

class ArticlePanel (a: Article) extends EquipmentPanel(a) {
  val countC = numField(a.data.count)

  def lbls = List(efa.dsa.being.loc.count)
  def fields = List(countC)

  def in: VSIn[ArticleData] =
    ^^(eqIn, parentIn, intIn(countC, Count.validate))(ArticleData.apply)
}

object ArticlePanel {
  def create (a: Article): IO[ArticlePanel] = for {
    p ← IO(new ArticlePanel(a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
