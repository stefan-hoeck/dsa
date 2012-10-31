package efa.dsa.test

import efa.rpg.core.{RpgItem, DB}
import scalaz._, Scalaz._

trait ItemGensTest {
  def testDB[A:RpgItem](db: DB[A], names: List[String]) = {
    val exp = db map {case (i,a) ⇒ (i, RpgItem[A].dataL get a)}
    val fnd = itemDatas(names) map (a ⇒ (a.id, a)) toMap

    exp == fnd
  }

}

// vim: set ts=2 sw=2 et:
