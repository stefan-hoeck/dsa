package efa.dsa.being.ui.abilities

import efa.dsa.being.abilities._
import efa.dsa.being.ui.DescribedPanel
import efa.nb.VSIn
import scalaz._, Scalaz._, effect.IO

class AbilityPanel[A,B:AbilityData](a: Ability[A,B])
   extends DescribedPanel[Ability[A,B]](a) {

   override def elems =
     (efa.core.loc.name beside nameC) above
     (descLbl beside descElem)

   lazy val in: VSIn[B] = 
     ^(stringIn(nameC), stringIn(descC))((n,d) ⇒ (
       (AbilityData[B].nameL := n) >> (AbilityData[B].descL := d)
     ) exec a.data
   )
}

object AbilityPanel {
  def create[A,B:AbilityData](a: Ability[A,B]): IO[AbilityPanel[A,B]] = for {
    p ← IO(new AbilityPanel[A,B](a))
    _ ← p.adjust
  } yield p
}

// vim: set ts=2 sw=2 et:
