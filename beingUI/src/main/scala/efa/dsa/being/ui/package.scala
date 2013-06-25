package efa.dsa.being

import dire.swing.Panel
import efa.core.Service.unique
import efa.dsa.being.ui.spi.UILocal
import efa.io.EfaIO._
import efa.nb.tc.{OutlineNb, AsTc, WithPrefs}
import efa.rpg.being.BeingPanel
import scalaz._, Scalaz._, effect.IO

package object ui {
  lazy val loc = unique[UILocal](UILocal)
  val version = "1.0.0-SNAPSHOT"

  type NP[A,B] = BeingPanel[A,B,OutlineNb]

  type BP[A,B] = BeingPanel[A,B,Panel]

  class BeingTc[A](n: String, id: String, panel: A ⇒ Panel) extends AsTc[A] {
    def create = ???
    def name = n
    def initialize(a: A) = _ ⇒ IO.ioUnit
    def preferredId = id
    def version = efa.dsa.being.ui.version
    def peer(a: A) = panel(a).peer

    final protected def readNp[A,B](np: NP[A,B], id: String)
      : WithPrefs[Unit] = np.p readProps (id, version)

    final protected def writeNp[A,B](np: NP[A,B], id: String)
      : WithPrefs[Unit] = np.p writeProps (id, version)
  }
}

// vim: set ts=2 sw=2 et:
