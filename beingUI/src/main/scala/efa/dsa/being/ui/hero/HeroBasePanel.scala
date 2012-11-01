package efa.dsa.being.ui.hero

import efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{HeroBaseData ⇒ HBD, loc ⇒ bLoc, Height, Weight, So}
import efa.dsa.world.mittelreich.{Distance ⇒ D, Weight ⇒ W}
import efa.rpg.being.BeingPanel
import efa.rpg.core.Gender
import scalaz._, Scalaz._

class HeroBasePanel extends BeingPanel[HBD,HBD] {
  val nameC = text
  val raceC = text
  val cultureC = text
  val professionC = text
  val positionC = text
  val titleC = text
  val soC = num
  val birthdayC = num
  val genderC = enumBox[Gender]
  val heightC = num
  val weightC = num
  val eyesC = text
  val hairC = text

  def set = stringIn(nameC)(HBD.name) ⊹ 
    stringIn(positionC)(HBD.position) ⊹
    stringIn(titleC)(HBD.title) ⊹
    intIn(soC, So.validate)(HBD.so) ⊹
    longIn(birthdayC)(HBD.birthday) ⊹ 
    comboBox(genderC)(HBD.gender) ⊹ 
    stringIn(eyesC)(HBD.eyeColor) ⊹ 
    stringIn(hairC)(HBD.hairColor) ⊹
    unitSET[D](heightC, D.HF, 0, Height.validate, HBD.height) ⊹ 
    unitSET[W](weightC, W.ST, 0, Weight.validate, HBD.weight) ⊹ 
    stringIn(raceC)(HBD.race.data.name) ⊹ 
    stringIn(cultureC)(HBD.culture.data.name) ⊹ 
    stringIn(professionC)(HBD.profession.name)

  {
    val heightL = "%s [%s]" format (bLoc.height, D.HF.shortName)
    val weightL = "%s [%s]" format (bLoc.weight, W.ST.shortName)

    //horizontal: 1
    val left = cLoc.name above bLoc.race above bLoc.culture above
               bLoc.profession above bLoc.gender above bLoc.birthday

    //horizontal: 2
    val right = (bLoc.position above bLoc.title above bLoc.so above
                bLoc.hair above bLoc.weight) beside
                (positionC above titleC above soC above hairC above weightC)
    //horizontal: 3
    val middleLower = (genderC beside bLoc.eyes beside eyesC) above
                      (birthdayC beside heightL beside heightC)

    //horizontal: 3
    val middle = raceC.fillH(3) above cultureC.fillH(3) above
                 professionC.fillH(3) above middleLower

    //horizontal 5
    val totalRight = nameC.fillH(5) above (middle beside right)

    left beside totalRight add()
  }
}

// vim: set ts=2 sw=2 et:
