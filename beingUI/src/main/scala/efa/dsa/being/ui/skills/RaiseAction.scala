package efa.dsa.being.ui.skills

import efa.dsa.being.loc
import efa.nb.actions._
import scalaz._, Scalaz._

class RaiseAction extends CookieAllAction[RaiseCookie] (
  loc.raise, CookieActionMode.ExactlyOne
){
  def run (s: Seq[RaiseCookie]) = s.toList foldMap (_.raise)
}

// vim: set ts=2 sw=2 et:
