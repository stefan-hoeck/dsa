package efa.dsa.being.equipment

import efa.rpg.core.specs.ToXmlProps
import org.scalacheck.Prop

object ArticleDataTest extends ToXmlProps[ArticleData]("ArticleData"){
  property("validateCount") =
    Prop forAll validated(ArticleData.count.set)(Count.validate)
}

// vim: set ts=2 sw=2 et:

