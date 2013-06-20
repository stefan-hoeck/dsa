package efa.dsa.being.ui.skills

import efa.dsa.being.loc
import efa.nb.actions._
import scalaz._, Scalaz._

class LowerAction extends CookieAllAction[LowerCookie] (
  loc.lower, CookieActionMode.ExactlyOne
){
  def run(s: Seq[LowerCookie]) = s.toList foldMap (_.lower)
}

// vim: set ts=2 sw=2 et:
