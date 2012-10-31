package efa.dsa.being.calc.skills
//
//import org.efa.dsa.being.HeroData
//import org.efa.dsa.being.skills._
//import org.efa.dsa.world.RaisingCost
//import org.efa.util.prop.Mutator
//import org.junit._
//import Assert._
//
//class ModSkilledDataTest {
//
//  import ModSkilledData._
//  
//  @Test
//  def testLocalization = {
//    val ms = List[Mutator[_, _]](talentsMut, languagesMut, scripturesMut, 
//                                 meleeTalentsMut, rangedTalentsMut, spellsMut,
//                                 ritualsMut)
//    ms foreach {m => assertTrue(m.locName.nonEmpty)}
//  }
//
//  @Test def testFromHero {
//    val rc = RaisingCost.E
//    val sd = SkilledData(List(Talent(1, rc, 1, false)), 
//                         List(MeleeTalent(1, rc, 2, false, 3)),
//                         List(RangedTalent(1, rc, 3, false)),
//                         List(Language(1, rc, 4, false)),
//                         List(Scripture(1, rc, 5, false)),
//                         List(Spell(1, rc, 6, false, false, "")),
//                         List(Ritual(1, rc, 7, false)))
//    val hd = new HeroData("Würgul") skilledData_= sd
//    val msd = ModSkilledData fromHeroData hd
//    
//    assertEquals(1, msd.talents.size)
//    assertEquals(1, msd.meleeTalents.size)
//    assertEquals(1, msd.rangedTalents.size)
//    assertEquals(1, msd.languages.size)
//    assertEquals(1, msd.scriptures.size)
//    assertEquals(1, msd.spells.size)
//    assertEquals(1, msd.rituals.size)
//    
//    assertEquals(1, msd.talents.head.taw)
//    assertEquals(2, msd.meleeTalents.head.taw)
//    assertEquals(3, msd.rangedTalents.head.taw)
//    assertEquals(4, msd.languages.head.taw)
//    assertEquals(5, msd.scriptures.head.taw)
//    assertEquals(6, msd.spells.head.taw)
//    assertEquals(7, msd.rituals.head.taw)
//  }
//}
