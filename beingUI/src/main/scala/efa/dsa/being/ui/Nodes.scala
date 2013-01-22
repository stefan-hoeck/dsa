package efa.dsa.being.ui

import efa.core.{ValSt, Efa}, Efa._
import efa.data.{UniqueId}
import efa.nb.node.{NodeOut, NbNode ⇒ N, NbChildren ⇒ NC,
                    Paster, PasteType}, NC._
import efa.rpg.core.{Described, HtmlDesc}
import scalaz._, Scalaz._, effect.IO

object Nodes {
  def described[A:Described]: NodeOut[A,Nothing] =
    N.name(Described[A].name) ⊹
    N.desc(Described[A].shortDesc) ⊹
    (N.cookie[HtmlDesc] ∙ Described[A].htmlDesc)

  def childActions (path: String): NodeOut[Any,Nothing] =
    N.contextRootsA(List("ContextActions/DsaChildNode", path))

  /**
   * @tparam A: The parent type for instance Skills
   * @tparam B: Type of fully calculated items for instance Skill
   * @tparam C: Type of data items for instance SkillData
   * @tparam D: Type of RpgItems for instance SkillItem
   * @tparam E: Data parent type for instance SkillDatas
   *
   * @TODO change from List (get) to Map
   */
  def parentNode[A,B,C,D,E](name: String, out: NodeOut[B,ValSt[C]])
    (get: A ⇒ List[B])(add: D ⇒ State[C,Unit])(
    implicit UI: UniqueId[B,E], M: Manifest[D]
  ): NodeOut[A,ValSt[C]] =
    NC.children(NC.uniqueIdF[B,ValSt[C],E,List](out) ∙ get) ⊹
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
