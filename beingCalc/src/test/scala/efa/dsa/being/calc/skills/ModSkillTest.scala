package efa.dsa.being.calc.skills
//
//import org.efa.dsa.being.skills.Talent
//import org.efa.dsa.world.RaisingCost
//import org.junit._
//import Assert._
//
//class ModSkillTest {
//  @Test def testRaiseAp { //E 4 to 5: 28; D 4 to 5: 22
//    val talent = Talent(1, RaisingCost.E, 4, false)
//    assertEquals(28, new ModTalent(talent).raiseAp)
//    assertEquals(22, new ModTalent(talent specialExp_= true).raiseAp)
//  }
//  
//  @Test def testActivateTalent {
//    val talent = Talent(1, RaisingCost.H, -1, false)
//    assertEquals(100, new ModTalent(talent).raiseAp)
//  }
//  
//  @Test def testUpperRaisingCosts {
//    val talent = Talent(1, RaisingCost.A, 32, false)
//    assertEquals(50, new ModTalent(talent).raiseAp)
//  }
//  
//  @Test def testLowerAp { //E 3 to 4: 21
//    val talent = Talent(1, RaisingCost.E, 4, false)
//    assertEquals(21, new ModTalent(talent).lowerAp)
//  }
//  
//  @Test def testLowerToZero { //E 3 to 4: 21
//    val talent = Talent(1, RaisingCost.E, 1, false)
//    assertEquals(4, new ModTalent(talent).lowerAp)
//  }
//  
//  @Test def testLowerZero {
//    val talent = Talent(1, RaisingCost.H, 0, false)
//    assertEquals(0, new ModTalent(talent).lowerAp)
//  }
//  
//  @Test def testUpperLoweringCosts {
//    val talent = Talent(1, RaisingCost.A, 32, false)
//    assertEquals(50, new ModTalent(talent).lowerAp)
//  }
//}
