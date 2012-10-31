package efa.dsa.test

import efa.dsa.equipment._
import efa.dsa.being.equipment._
import efa.dsa.world.{DistanceClass, ShieldType, ShieldSize}
import efa.rpg.core.{ItemData, DB, RpgItem, DieRoller}
import org.scalacheck._, Arbitrary.arbitrary
import scalaz._, Scalaz._, scalacheck.ScalaCheckBinding._

object EquipmentGens {

  def equipmentDatasGen(es: EquipmentItems): Gen[EquipmentDatas] = {
    def ammunitionGen (i: AmmunitionItem) =
      countGen ∘ (AmmunitionData(i.eData, i.id, i.tp, _))

    def armorGen (i: ArmorItem) =
      arbitrary[Boolean] ∘ (ArmorData(i.eData, i.id, i.rs, i.be, _))

    def articleGen (i: ArticleItem) = countGen ∘ (ArticleData(i, i.id, _))

    def meleeGen (i: MeleeWeaponItem) = Gen value MeleeWeaponData(
      i.eData, i.id, i.tp, i.talent, i.bf, i.tpkk, i.ini, i.wm)

    def rangedGen (i: RangedWeaponItem) = Gen value RangedWeaponData(
      i.eData, i.id, i.tp, i.talent, i.tpkk, i.reach, i.tpPlus, i.timeToLoad)

    def shieldGen (i: ShieldItem) =
      Gen value ShieldData(i.eData, i.id, i.ini, i.bf, i.wm)

    def zoneArmorGen (i: ZoneArmorItem) =
      arbitrary[Boolean] ∘ (ZoneArmorData(i.eData, i.id, i.rs, i.be, _))

    def dbGen[A,B](db: DB[A], gen: A ⇒ Gen[B]) = {
      def pairGen (p: (Int,A)): Gen[(Int,B)] = gen(p._2) ∘ ((p._1, _))
      def fromSeq (as: Seq[(Int,A)]) =
        as.toList traverse pairGen map (_.toMap)

      Gen someOf db flatMap fromSeq
    }

    for {
      amm ← dbGen(es.ammunition, ammunitionGen)
      arm ← dbGen(es.armor, armorGen)
      art ← dbGen(es.articles, articleGen)
      mel ← dbGen(es.meleeWeapons, meleeGen)
      ran ← dbGen(es.rangedWeapons, rangedGen)
      shi ← dbGen(es.shields, shieldGen)
      zon ← dbGen(es.zoneArmor, zoneArmorGen)
    } yield EquipmentDatas(amm, arm, art, mel, ran, shi, zon)
  }

  def handsDataGen (es: EquipmentDatas): Gen[HandsData] = {
    import HandsData._

    def dbGen[A,B](db: DB[A], f: Int ⇒ B, e: Gen[B]): Gen[B] =
      db.isEmpty ? e | Gen.oneOf(db.toList).map(p ⇒ f(p._1))

    val empty: Gen[HandsData] = Gen value Empty

    val twoHanded: Gen[HandsData] =
      dbGen(es.meleeWeapons, TwoHanded.apply, empty)
    
    val melee: Gen[HandsData] = dbGen(es.meleeWeapons,
      i ⇒ OneHanded(HandData.Empty, HandData.Melee(i)), empty)
    
    val meleeWrong: Gen[HandsData] = dbGen(es.meleeWeapons,
      i ⇒ OneHanded(HandData.Melee(i), HandData.Empty), empty)
    
    val meleeShield: Gen[HandsData] =
      ^(dbGen(es.shields, HandData.Shield(_), HandData.Empty),
        dbGen(es.meleeWeapons, HandData.Melee(_), HandData.Empty))( 
        OneHanded.apply)
    
    val throwing: Gen[HandsData] = dbGen(es.rangedWeapons,
      i ⇒ OneHanded(HandData.Empty, HandData.Ranged(i)), empty)
    
    val throwingWrong: Gen[HandsData] = dbGen(es.rangedWeapons,
      i ⇒ OneHanded(HandData.Ranged(i), HandData.Empty), empty)
    
    val rangedAmmo: Gen[HandsData] =
      ^(dbGen(es.ammunition, HandData.Ammo(_), HandData.Empty),
        dbGen(es.rangedWeapons, HandData.Ranged(_), HandData.Empty))(
        OneHanded.apply)

    Gen oneOf (empty, twoHanded, melee, meleeWrong, meleeShield,
      throwing, throwingWrong, rangedAmmo)
  }

  lazy val equipmentGen = for {
    amm ← ammunitionGen
    arm ← armorGen
    art ← articleGen
    mel ← meleeWeaponGen
    ran ← rangedWeaponGen
    shi ← shieldGen
    zon ← zoneArmorGen
  } yield EquipmentItems(amm, arm, art, mel, ran, shi, zon)
  
  lazy val ammunitionGen = dbGen[AmmunitionItem](
    ammunitionNames, ed ⇒ tpGen ∘ (AmmunitionItem(ed, _)))
  
  lazy val armorGen = {
    def gen (ed: EquipmentItemData) = for {
      rs ← rsGen
      be ← beGen
      ia ← arbitrary[Boolean]
    } yield ArmorItem(ed, rs, be, ia)

    dbGen[ArmorItem](armorNames, gen)
  }

  lazy val articleGen = dbGen[ArticleItem](articleNames, Gen value _)
  
  lazy val meleeWeaponGen = {
    def gen (ed: EquipmentItemData) = for {
      tp ← tpGen
      tpkk ← tpkkGen
      imp ← arbitrary[Boolean]
      tal ← talentGen
      bf ← bfGen
      th ← arbitrary[Boolean]
      wm ← wmGen
      ini ← iniGen
      dk ← arbitrary[DistanceClass]
      len ← lengthGen
    } yield MeleeWeaponItem(ed, tp, tpkk, imp, tal, bf, th, wm, ini, dk, len)

    dbGen[MeleeWeaponItem](meleeWeaponNames, gen)
  }
  
  lazy val rangedWeaponGen = {
    def gen (ed: EquipmentItemData) = for {
      tp ← tpGen
      tpkk ← tpkkGen
      imp ← arbitrary[Boolean]
      tal ← talentGen
      rea ← reachGen
      tpp ← tpPlusGen
      mw ← arbitrary[Boolean]
      ttl ← timeToLoadGen
      use ← arbitrary[Boolean]
    } yield RangedWeaponItem(ed, tp, tpkk, imp, tal, rea, tpp, mw, ttl, use)

    dbGen[RangedWeaponItem](rangedWeaponNames, gen)
  }
  
  lazy val shieldGen = {
    def gen (ed: EquipmentItemData) = for {
      siz ← arbitrary[ShieldSize]
      typ ← arbitrary[ShieldType]
      ini ← iniGen
      bf ← bfGen
      wm ← wmGen
    } yield ShieldItem(ed, siz, typ, ini, bf, wm)

    dbGen[ShieldItem](shieldNames, gen)
  }
  
  lazy val zoneArmorGen = {
    def gen (ed: EquipmentItemData) = for {
      zon ← zoneRsGen
      be ← beGen
      ia ← arbitrary[Boolean]
    } yield ZoneArmorItem(ed, zon, be, ia)

    dbGen[ZoneArmorItem](zoneArmorNames, gen)
  }


  lazy val ammunitionNames = List(
    "Bolzen",
    "Pfeil",
    "Stein"
  )

  lazy val armorNames = List(
    "Lederhelm",
    "Lederrüstung",
    "Krötenhaut",
    "Gestechrüstung",
    "Plattenpanzer"
  )

  lazy val articleNames = List(
    "Schlafsack",
    "Seil",
    "Köcher",
    "Zelt"
  )

  lazy val meleeWeaponNames = List(
    "Andergaster",
    "Dolch",
    "Schwert",
    "Streitaxt",
    "Zweihänder"
  )

  lazy val rangedWeaponNames = List(
    "Armbrust",
    "Kurzbogen",
    "Langbogen",
    "Schleuder",
    "Wurfbeil"
  )

  lazy val shieldNames = List(
    "Buckler",
    "Holzschild",
    "Parierhand",
    "Stahlschild"
  )

  def zoneArmorNames = armorNames

  def dbGen[A] (names: List[String], gen: EquipmentItemData ⇒ Gen[A])
    (implicit E:EquipmentItem[A]): Gen[DB[A]] = {
    def idGen (id: ItemData): Gen[A] = for {
      price ← priceGen
      weight ← weightGen
      a ← gen(EquipmentItemData(id, price, weight))
    } yield a

    val seqGen = itemDatas(names) traverse idGen

    seqGen map (_ map (a ⇒ (E id a, a)) toMap)
  }
}

// vim: set ts=2 sw=2 et:
