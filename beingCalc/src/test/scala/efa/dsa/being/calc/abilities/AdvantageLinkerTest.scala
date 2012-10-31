package efa.dsa.being.calc.advantages
//
//import org.efa.dsa.being.abilities.{AdvantagedData, Feat}
//import org.efa.dsa.being.calc.advantages.AdvantageLikeLinker.FeatLinker
//import org.junit._
//import Assert._
//
//class AdvantageLikeLinkerTest {
//  @Test def testRename {
//    val ad1 = AdvantagedData.Default feats_= List(Feat(1, "feat", true, ""),
//                                                 Feat(2, "feat2", false, ""))
//    val ad2 = AdvantagedData.Default feats_= List(Feat(2, "renamed", false, ""), 
//                                                  Feat(1, "feat", true, ""))
//    
//    assertEquals(Some(ad2), FeatLinker.renameA(ad1, Feat(2, "feat2", false, ""), "renamed"))
//    assertEquals(None, FeatLinker.renameA(ad1, Feat(2, "feat2", false, ""), "feat")) //must not rename since name already exists.
//  }
//
//}
