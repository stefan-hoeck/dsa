package efa.dsa.being.ui.abilities

import efa.core._
import efa.dsa.being.abilities._
import efa.dsa.being.calc.AbilityLinker
import efa.dsa.being.ui.{Nodes, loc ⇒ bLoc}
import efa.nb.dialog.DialogEditable
import efa.nb.node.{NodeOut, NbNode ⇒ N}
import efa.rpg.core.{Described}
import scalaz._, Scalaz._

object AbilitiesNodes {
  def abilityOut[A,B](
    implicit L: AbilityLinker[A,B],
    D: DialogEditable[Ability[A,B],(Ability[A,B],B)]
  ): NodeOut[Ability[A,B],ValSt[AbilityDatas]] = 
    N.destroyEs(L.delete) ⊹
    (N.editDialog(D) map (p ⇒ L update (p._1, p._2) success)) ⊹
    (N renameEs L.rename contramap (d ⇒ (d, d.nameVal))) ⊹ 
    Nodes.described[Ability[A,B]] ⊹
    Nodes.childActions("ContextActions/DsaAdvantageNode")

}

//  protected def setData(ad: AdvantagedData) {
//    provider.source.fire(s.now advantagedData_= ad)
//  }
//  override protected def rootNode = new AbstractNode(
//    new NodeSeqChildren(List[Node](advantagesNode, disadvantagesNode, featsNode))
//  )
//  override protected def localizations = 
//    List(Advantage.valueMut.loc, Advantage.activeMut.loc)
//  
//  class ParentNode[A <: AdvantageLikeBuilder[A], I <: ItemBuilder[I]] (
//    linker: AdvantageLikeLinker{type Item = I; type Adv = A},
//    toNode: Signal[A] => SignalNode[A]
//  )(implicit m: Manifest[I])
//  extends ListNode[A, I](
//    sd map linker.mutator, //signal
//    NodeFactory[A, List[A], String](toNode, _ sortWith (_.name < _.name), _.name)//children
//  ) 
//  { 
//    setDisplayName(linker.mutator.locName)
//    
//    override protected def addItem(i: I) {
//      linker.addA(sd.now, linker item2Adv i) foreach {setData}
//    }
//  }
//  
//  protected def advantagesNode = new ParentNode(AdvantageLinker, advantageNode)
//  protected def disadvantagesNode = new ParentNode(DisadvantageLinker, disadvantageNode)
//  protected def featsNode = new ParentNode(FeatLinker, featNode)
//
//     with PropertyNode {
//    
//    instanceContent add new EditDescriptionCookie{
//      def get = a.now.userDesc
//      def set(s: String) {setData(linker.updateA(sd.now, a.now userDesc_= s))}
//    }
//    
//    //property controllers
//    override lazy val controllers = List[PropertyController[_]](dataController)
//    private[abilities] lazy val dataController: PropertyController[_] = new AdvController {
//      override protected def factories = List(booleanRw[A](activeMut))
//    }
//  
//  class AdvantageNode(a: Signal[Advantage]) 
//  extends AdvantageLikeNode(a, AdvantageLinker) {
//    override lazy val controllers = List(valController, dataController)
//    private lazy val valController = new AdvController {
//        protected def factories = List(textRw(Advantage.valueMut, Alignment.Right))
//      }
//  }
//  
//  class DisadvantageNode(a: Signal[Disadvantage]) 
//  extends AdvantageLikeNode(a, DisadvantageLinker) {
//    override lazy val controllers = List(valController, dataController)
//    private lazy val valController = new AdvController {
//        override protected def factories =
//          List(textRw(Disadvantage.valueMut, Alignment.Right))
//      }
//  }
//  
//  class FeatNode(a: Signal[Feat]) extends AdvantageLikeNode(a, FeatLinker)
//  
//}

// vim: set ts=2 sw=2 et:
