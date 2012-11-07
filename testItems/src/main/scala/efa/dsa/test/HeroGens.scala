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
    skills ← AbilityGens skillDatasGen ais
    equipment ← EquipmentGens equipmentDatasGen eis
    feats ← AbilityGens abilityDatasGen ais
    attributes ← attributesGen
    humanoid ← humanoidBaseDataGen(equipment)
    base ← baseDataGen(skills)
  } yield HeroData(base, attributes, humanoid, feats, skills, equipment)

  def baseDataGen(sd: SkillDatas): Gen[HeroBaseData] = 
    ^^^^(Gen.oneOf(heroNames),
      arbitrary[Gender],
      GenerationGens.raceGen(sd),
      GenerationGens.cultureGen(sd),
      GenerationGens.professionGen(sd))((n, g, r, c, p) ⇒ 
      HeroBaseData(n, g, r, c, p, "", "", 1, 0, 0L, 0L, 0L, "", "", "", 0))

  def humanoidBaseDataGen(ed: EquipmentDatas): Gen[HumanoidBaseData] = for {
    lae ← lostAeGen
    bae ← boughtAeGen
    lau ← lostAuGen
    bau ← boughtAuGen
    lke ← lostKeGen
    bke ← boughtKeGen
    lle ← lostLeGen
    ble ← boughtLeGen
    bmr ← boughtMrGen
    wou ← woundsGen
    exh ← exhaustionGen
    zon ← zoneWoundsGen
    han ← EquipmentGens handsDataGen ed
  } yield HumanoidBaseData(
    lae, bae, lau, bau, lke, bke, lle, ble, bmr, wou, exh, zon, han)

  lazy val attributesGen =
    ^(enumGen[Attribute](0, 4), enumGen[Attribute](8, 14))(
      AttributesData.apply)

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
