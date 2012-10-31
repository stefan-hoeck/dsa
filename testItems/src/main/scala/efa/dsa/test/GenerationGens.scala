package efa.dsa.test

import efa.dsa.being.generation._
import efa.dsa.being.skills.{SkillDatas, SkillData}
import efa.dsa.generation.{SkillPrototype, SkillPrototypes}
import efa.dsa.world.{Attribute}
import efa.rpg.core.{DB, EnumMaps}
import org.scalacheck.{Arbitrary, Gen}, Arbitrary.arbitrary
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

object GenerationGens {

  def raceGen (sd: SkillDatas) = genDataAttributesGen(sd, raceNames)

  def cultureGen (sd: SkillDatas) = genDataAttributesGen(sd, cultureNames)

  def professionGen (sd: SkillDatas) = genDataGen(sd, professionNames)

  lazy val attributesGen = EnumMaps.int[Attribute](-2, 2, 0, "").gen

  def genDataGen (sd: SkillDatas, names: List[String]): Gen[GenData] =
    ^(Gen.oneOf(names),
      genAeGen,
      genAuGen,
      genLeGen,
      genMrGen,
      skillsGen(sd))(GenData.apply)

  def genDataAttributesGen (sd: SkillDatas, names: List[String]) =
    ^(genDataGen(sd, names), attributesGen)(GenDataAttributes.apply)
  

  def skillsGen (sd: SkillDatas): Gen[SkillPrototypes] = {
    def dbg[A](db: DB[A]): Gen[DB[SkillPrototype]] = {
      def pairToProto(i: Int) = skillProtoGen ∘ (v ⇒ (i, SkillPrototype(i, v)))
      def seqToMap (as: Seq[(Int,A)]) =
        as.toList traverse (p ⇒ pairToProto(p._1)) map (_.toMap)

      Gen someOf db flatMap seqToMap
    }

    for {
      lan ← dbg(sd.languages)
      mel ← dbg(sd.meleeTalents)
      ran ← dbg(sd.rangedTalents)
      rit ← dbg(sd.rituals)
      scr ← dbg(sd.scriptures)
      spe ← dbg(sd.spells)
      tal ← dbg(sd.talents)
    } yield SkillPrototypes(lan, mel, ran, rit, scr, spe, tal)
  }

  lazy val raceNames = List(
    "Auelfe",
    "Mittelländer",
    "Nivesin",
    "Norbardin",
    "Thorwaler",
    "Zwerg"
  )

  lazy val cultureNames = List(
    "Andergast",
    "Garethien",
    "Maraskan",
    "Orkland",
    "Svellttal",
    "Weiden",
    "Zyklopeninseln"
  )

  lazy val professionNames = List(
    "Einbrecher",
    "Entdecker",
    "Jäger",
    "Krieger",
    "Kurtisane",
    "Magierin",
    "Piratin",
    "Wundärztin"
  )
}

// vim: set ts=2 sw=2 et:
