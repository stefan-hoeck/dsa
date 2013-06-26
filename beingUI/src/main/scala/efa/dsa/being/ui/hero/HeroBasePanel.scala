package efa.dsa.being.ui.hero

import dire._, dire.swing._
import efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{HeroBaseData ⇒ HBD, loc ⇒ bLoc, Height, Weight, So}
import efa.dsa.being.ui.loc
import efa.dsa.world.mittelreich.{Distance ⇒ D, Weight ⇒ W}
import efa.rpg.being.BeingPanel, BeingPanel._
import efa.rpg.core.Gender
import scalaz._, Scalaz._, effect.IO

object HeroBasePanel {
  private val profName = HBD.profession.name
  private val cultName = HBD.culture.data.name
  private val raceName = HBD.race.data.name

  import Swing._
  def apply(): IO[BeingPanel[HBD,HBD,Panel]] = for {
    name       ← TextField()
    race       ← TextField()
    culture    ← TextField()
    profession ← TextField()
    position   ← TextField()
    title      ← TextField()
    so         ← numeric
    birthday   ← numeric
    gender     ← enumBox[Gender]
    height     ← numeric
    weight     ← numeric
    eyes       ← TextField()
    hair       ← TextField()
    p          ← Panel(border := Border.title(loc.basePanel))

    _ ← {
          val heightL = "%s [%s]" format (bLoc.height, D.HF.shortName)
          val weightL = "%s [%s]" format (bLoc.weight, W.ST.shortName)
      
          //horizontal: 1
          val left = cLoc.name above bLoc.race above bLoc.culture above
                     bLoc.profession above bLoc.gender above bLoc.birthday
      
          //horizontal: 2
          val right = (bLoc.position above bLoc.title above bLoc.so above
                      bLoc.hair above bLoc.weight) beside
                      (position above title above so above hair above weight)
          //horizontal: 3
          val middleLower = (gender beside bLoc.eyes beside eyes) above
                            (birthday beside heightL beside height)
      
          //horizontal: 3
          val middle = race.fillH(3) above culture.fillH(3) above
                       profession.fillH(3) above middleLower
      
          //horizontal 5
          val totalRight = name.fillH(5) above (middle beside right)
      
          left beside totalRight addTo p
        }

    sf = stringSf(name.sf)(HBD.name) ⊹ 
         stringSf(position.sf)(HBD.position) ⊹
         stringSf(title.sf)(HBD.title) ⊹
         intSf(so.sf, So.validate)(HBD.so) ⊹
         longSf(birthday.sf)(HBD.birthday) ⊹ 
         lensed(gender.sf)(HBD.gender) ⊹ 
         stringSf(eyes.sf)(HBD.eyeColor) ⊹ 
         stringSf(hair.sf)(HBD.hairColor) ⊹
         unitSf[D,HBD](height.sf, D.HF, 0, Height.validate, HBD.height) ⊹ 
         unitSf[W,HBD](weight.sf, W.ST, 0, Weight.validate, HBD.weight) ⊹ 
         stringSf(race.sf)(raceName) ⊹ 
         stringSf(culture.sf)(cultName) ⊹ 
         stringSf(profession.sf)(profName)
  } yield BeingPanel(p, sf)
}

// vim: set ts=2 sw=2 et:
