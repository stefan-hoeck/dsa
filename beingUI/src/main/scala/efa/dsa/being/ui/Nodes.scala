package efa.dsa.being.ui

import efa.core.{ValSt, Efa, UniqueId, Unerased}, Efa._
import efa.core.syntax.lookup
import efa.nb.node.{NodeOut, NbNode ⇒ N, NbChildren ⇒ NC,
                    Paster, PasteType}, NC._
import efa.rpg.core.{Described, HtmlDesc}
import scalaz._, Scalaz._, effect.IO

object Nodes {
  def described[A:Described,B]: NodeOut[A,B] =
    N.name[A,B](Described[A].name) ⊹
    N.desc[A,B](Described[A].shortDesc) ⊹
    (N.cookie[HtmlDesc,B] ∙ Described[A].htmlDesc)

  def childActions[A,B](path: String): NodeOut[A,B] =
    N.contextRootsA(List("ContextActions/DsaChildNode", path))

  /**
   * @tparam A: The parent type for instance Skills
   * @tparam B: Type of fully calculated items for instance Skill
   * @tparam C: Type of data items for instance SkillData
   * @tparam D: Type of RpgItems for instance SkillItem
   * @tparam E: Data parent type for instance SkillDatas
   */
  def parentNode[A,B,C,D:Unerased](name: String, out: NodeOut[B,ValSt[C]])
    (get: A ⇒ List[B])(add: D ⇒ State[C,Unit]): NodeOut[A,ValSt[C]] =
    NC.children(NC.leavesF(out)(get)) ⊹
    N.nameA(name) ⊹
    NodeOut[A,ValSt[C]](
      (o,n) ⇒ _ ⇒ {
        val paster: Paster = (p, no) ⇒ for {
          optD ← no.getLookup.head[D]
          _    ← optD map (d ⇒ o(add(d).success)) orZero
        } yield ()

        n setPasters List(paster)
      }
    )
}

// vim: set ts=2 sw=2 et:
