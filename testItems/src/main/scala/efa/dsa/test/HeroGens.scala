package efa.dsa.test

import efa.dsa.abilities.AbilityItems
import efa.dsa.being._
import efa.dsa.being.skills.{SkillDatas}
import efa.dsa.being.equipment.{EquipmentDatas}
import efa.dsa.equipment.EquipmentItems
import efa.dsa.world.Attribute
import efa.rpg.core.{Gender, EnumMaps}
import org.scalacheck.{Arbitrary, Gen}, Arbitrary.arbitrary
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

object HeroGens {

  def heroDataGen (ais: AbilityItems, eis: EquipmentItems): Gen[HeroData] = for {
    humanoid ← humanoidDataGen(ais, eis)
    base ← baseDataGen(humanoid.skills)
    bought ← enumGenL[Attribute](0L, 4L)
    bae ← boughtAeGen
    bau ← boughtAuGen
    bke ← boughtKeGen
    ble ← boughtLeGen
    bmr ← boughtMrGen
  } yield HeroData(base, humanoid, bought, bae, bau, bke, ble, bmr)

  def baseDataGen(sd: SkillDatas): Gen[HeroBaseData] = 
    ^^^^(Gen.oneOf(heroNames),
      arbitrary[Gender],
      GenerationGens.raceGen(sd),
      GenerationGens.cultureGen(sd),
      GenerationGens.professionGen(sd))((n, g, r, c, p) ⇒ 
      HeroBaseData(n, g, r, c, p, "", "", 1, 0, 0L, 0L, 0L, "", "", "", 0))

  def humanoidDataGen(ais: AbilityItems, eis: EquipmentItems)
    : Gen[HumanoidData] = for {
    att ← enumGenL[Attribute](8L, 14L)
    lae ← lostAeGen
    lau ← lostAuGen
    lke ← lostKeGen
    lle ← lostLeGen
    wou ← woundsGen
    exh ← exhaustionGen
    zon ← zoneWoundsGen
    sks ← AbilityGens skillDatasGen ais
    ed  ← EquipmentGens equipmentDatasGen eis
    abs ← AbilityGens abilityDatasGen ais
    han ← EquipmentGens handsDataGen ed
  } yield HumanoidData(att, lae, lau, lke, lle,
    wou, exh, zon, han, abs, ed, sks)

  lazy val heroNames = List (
    "Alice",
    "Elodia",
    "Galeipa",
    "Gazor",
    "Goga",
    "Orshosh",
    "Thorolf",
    "Thynia",
    "Würgul"
  )
}

// vim: set ts=2 sw=2 et:
