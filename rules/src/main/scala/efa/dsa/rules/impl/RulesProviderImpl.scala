package efa.dsa.rules.impl

import efa.rpg.rules.LocFolder
import efa.core.{Folder, Localization}
import efa.dsa.rules.loc._
import scalaz.DList

class RulesProviderImpl extends efa.rpg.rules.spi.RulesProvider {
  private def parent (name: String, fs: LocFolder*): LocFolder =
    Folder(Stream.empty, fs.toStream, name)

  private def child (name: String, ls: Localization*): LocFolder =
    Folder(ls.toStream, Stream.empty, name)

  def get = DList(
    parent("Grundregeln",
      Folder(Stream(
        carriedWeightL,
        carryingCapacityL), Stream(), "Ausrüstung"),
      Folder(Stream(
        herausragendeEigenschaftL,
        miserableEigenschaftL,
        maxBoughtAttributeL
      ), Stream(), "Eigenschaften"),
      parent("Grundwerte",
        child("Astralenergie",
          calcAeL,
          vollzaubererAeL,
          halbzaubererAeL,
          viertelzaubererAeL,
          vollzaubererAeL,
          magierAeL,
          maxBoughtAeL
        ),
        child("Ausdauer",
          ausdauerndL,
          kurzatmigL,
          calcAuL,
          maxBoughtAuL,
          overstrainAuL
        ),
        child("Lebensenergie",
          hoheLebenskraftL,
          niedrigeLebenskraftL,
          calcLeL,
          maxBoughtLeL
        )
      ),
      parent("Kampfwerte",
        child("Attacke",
          shieldAtL,
          calcAtL
        ),
        child("ATPA",
          linkhandWeaponL,
          beidhandigerKampfIL,
          beidhandigerKampfIIL,
          weaponWrongHandL,
          meleeTalentAtPaL,
          beToAtPaL,
          baseAttackModeL
        ),
        child("Ausweichen",
          flinkDodgeL,
          ausweichenIL,
          ausweichenIIL,
          ausweichenIIIL,
          calcDodgeL,
          dodgeBeL,
          akrobatikDodgeL,
          behabigDodgeL
        ),
        child("Behinderung",
          rustungsgewohnungIIL,
          rustungsgewohnungIIIL,
          armorBeL,
          beFromOverloadL
        ),
        child("Fernkampf",
          calcFkL,
          rangedTalentFkL,
          beToFkL
        ),
        child("Geschwindigkeit",
          flinkGsL,
          behabigGsL,
          zwergenwuchsGsL,
          kleinwuchsigGsL,
          calcGsL,
          gsBeL
        ),
        child("Initiative",
          kampfgespurIniL,
          kampfreflexeIniL,
          shieldIniL,
          calcIniL,
          iniBeL,
          rustungsgewohnungIIIIniL
        ),
        child("Magieresistenz",
          niedrigeMagieresistenzL,
          hoheMagieresistenzL,
          halbzaubererMrL,
          vollzaubererMrL,
          calcMrL,
          maxBoughtMrL
        ),
        child("Parade",
          linkhandShieldPaL,
          schildkampfIL,
          schildkampfIIL,
          shieldPaL,
          calcPaL
        ),
        child("Rüstungsschutz", armorRsL),
        child("Wunden", woundsL),
        child("Wundschwelle",
          glasknochenWoundThresholdL,
          eisernWoundThresholdL,
          calcWoundThresholdL
        ),
        child("Talente", beTalentL)
      )
    ),
    parent("Optionale Regeln", 
      child("Eigenschaften", overstrainKoL),
      child("Grundwerte", lowAuL, lowLeL),
      parent("Kampfwerte",
        child("Attacke", meleeWeaponWmL),
        child("ATPA", lowKkAtPaL),
        child("Behinderung", zoneArmorBeL, overstrainBeL),
        child("Initiative", meleeWeaponIniL),
        child("Rüstungsschutz", zoneArmorRsL),
        child("Trefferpunkte", kkToTpL),
        child("Überanstrengung", overstrainFromExhaustionL)
      ),
      child("Talente", lowLeTalentL)
    )
  )
}

// vim: set ts=2 sw=2 et:
