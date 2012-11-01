package efa.dsa.being.ui.hero

import efa.core.{loc ⇒ cLoc}
import efa.dsa.being.{HeroBaseData ⇒ HBD, loc ⇒ bLoc}
import efa.dsa.world.mittelreich._
import efa.rpg.being.BeingPanel
import efa.rpg.core.Gender
import scalaz._, Scalaz._

class HeroBasePanel extends BeingPanel[HBD,HBD] {
  val nameC = text
  val raceC = text //TODO
  val cultureC = text //TODO
  val professionC = text //TODO
  val positionC = text
  val titleC = text
  val soC = num
  val birthdayC = num
  val genderC = enumBox[Gender]
  val heightC = num //TODO
  val weightC = num //TODO
  val eyesC = text
  val hairC = text

  def set = stringIn(nameC)(HBD.name) ⊹ 
    stringIn(positionC)(HBD.position) ⊹
    stringIn(titleC)(HBD.title) ⊹
    intIn(soC)(HBD.so) ⊹
    longIn(birthdayC)(HBD.birthday) ⊹ 
    comboBox(genderC)(HBD.gender) ⊹ 
    stringIn(eyesC)(HBD.eyeColor) ⊹ 
    stringIn(hairC)(HBD.hairColor)

  {
    val heightL = "%s [%s]" format (bLoc.height, Distance.HF.shortName)
    val weightL = "%s [%s]" format (bLoc.weight, Weight.ST.shortName)

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
