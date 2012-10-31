package efa.dsa.being.equipment

sealed trait Hands {
  def left: Hand
  def right: Hand
}

object Hands {
  
  case object Empty extends Hands {
    def left = Hand.Empty
    def right = Hand.Empty
  }
  
  case class OneHanded(left: Hand, right: Hand) extends Hands

  case class TwoHanded(w: MeleeWeapon) extends Hands {
    def left = Hand.Melee(w)
    def right = Hand.Melee(w)
  }

  implicit val HandsEqual = scalaz.Equal.equalA[Hands]
}

// vim: set ts=2 sw=2 et:
