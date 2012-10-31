package efa.dsa.being.calc.skills
//
//import org.efa.dsa.being.HeroData
//import org.efa.dsa.being.calc.Hero
//import org.efa.dsa.being.generation._
//import org.efa.dsa.being.skills.{SkilledData, Talent, ProtoTalent}
//import org.efa.dsa.world.RaisingCost
//import org.junit._
//import Assert._
//
//class SkillLinkerTest {
//  val sd = SkilledData.Default
//  val talents = List(Talent(1, RaisingCost.C, 3, false))
//  val linker = SkillLinker.TalentLinker
//  
//  @Test def testSetS {
//    assertEquals(sd talents_= talents, linker.setS(sd, talents))
//  }
//  
//  @Test def testDeleteS {
//    assertEquals(sd, linker.deleteS(linker.setS(sd, talents), talents.head))
//  }
//  
//  @Test def testAddS {
//    assertEquals(sd talents_= talents, linker.addS(sd, talents.head))
//  }
//  
//  @Test def testUpdateS {
//    assertEquals(sd talents_= List(Talent(1, RaisingCost.C, 4, false)), 
//                 linker.updateS(sd talents_= talents, talents.head tap_= 4))
//  }
//
//  val psd = new ProtoSkillData() talents_= List(ProtoTalent(1, 2), ProtoTalent(3, 5))
//  private def race = new Race() skills_= psd
//  private def culture = new Culture() skills_= psd
//  private def profession = new Profession() skills_= psd
//  
//  val heroData = new HeroData("Würgul") race_= race culture_= 
//    culture profession_= profession skilledData_= (sd talents_= talents) ap_= 1000
//  
//  @Test def testModsFromHero {
//    val mods = linker modsFromHero heroData
//    val mod = mods.head
//    assertEquals(talents.head, mod.parent)
//    assertEquals(2 + 2 + 2 + 3, mod.permanentTaw)
//    assertEquals(2 + 2 + 2 + 3, mod.taw)
//    assertEquals(4, mod.modifiers.size)
//  }
//  
//  @Test def testRaiseAndLowerSkill { //Category C at 9 TaW => 38
//    val hero = Hero(heroData)
//    assertEquals(9, hero.talents.find(_.parent.parentId == 1).get.taw)
//    def raised = heroData apUsed_= 38 updateTalent Talent(1, RaisingCost.C, 4, false)
//    val rd = linker.raiseSkill(hero, 1)
//    assertEquals(raised, linker.raiseSkill(hero, 1))
//    assertEquals(heroData, linker.lowerSkill(Hero(rd), 1))
//  }
//  
//  @Test def testRaiseNotPossible {
//    val np1 = heroData ap_= 20 //too few ap to raise
//    val np2 = heroData apUsed_= 999 //too unused ap to raise
//    assertEquals(np1, linker.raiseSkill(Hero(np1), 1))
//    assertEquals(np2, linker.raiseSkill(Hero(np2), 1))
//  }
//  
//  @Test def testLowerNotPossible {
//    val np1 = heroData updateTalent Talent(1, RaisingCost.C, 0, false)
//    assertEquals(np1, linker.lowerSkill(Hero(np1), 1))
//  }
//}
