package efa.dsa.being

import efa.core.Default
import efa.dsa.being.abilities.Abilities
import efa.dsa.being.equipment.{Equipments, AttackMode}
import efa.dsa.being.skills.Skills
import efa.rpg.core.{Modifiers, Util, Described}
import scalaz._, Scalaz._

case class Hero (
  abilities: Abilities,
  attackModes: List[AttackMode],
  attributes: HeroAttributes,
  data: HeroData,
  derived: HeroDerived,
  equipment: Equipments,
  modifiers: Modifiers,
  skills: Skills
)

object Hero extends Util {
  lazy val default = Hero(!!, Nil, !!, !!, !!, !!, Modifiers.empty, !!)

  implicit lazy val HeroDefault = Default default default

  implicit lazy val HeroEqual = Equal.equalA[Hero]

  implicit lazy val HeroDescribed = new Described[Hero] {
    def fullDesc (h: Hero) = h.data.base.desc
    def shortDesc (h: Hero) = h.data.base.desc
    def name (h: Hero) = h.data.base.name
    def desc (h: Hero) = h.data.base.desc
  }
}

////			//creates an optional modifier depending whether a given value is 0 or not
////			def nonZeroMod(name: String, x: Long): Option[Modifier] = x match {
////				case 0 => None
////				case x => Some(Modifier(name, x))
////			}
////    
////			//applies modifiers to attributes (start, bought, race & culture)
////			def attributesApplied = {
////				def attr2mods(a: Attribute): List[Modifier] = List(
////					nonZeroMod(Start, h base a), 
////					nonZeroMod(Bought, h bought a), 
////					nonZeroMod(h.race.name, h.race attribute a), 
////					nonZeroMod(h.culture.name, h.culture attribute a)
////				).flatten
////    
////				(start /: Attribute.values)((hr, a) => 
////					hr.addModifiers(CalcAttributed keyFromAttribute a, attr2mods(a)))
////			}
////    
////			//applies modifiers to derived values (bought, race, culture & profession)
////			def allDerived: Hero = {
////				def boughtGiMod(key: ModifierKey, bought: HeroData => Int, 
////												fromGi: GenerationInfo => Int)(hr: Hero): Hero = {
////					def allGi: List[Modifier] = List(h.race, h.culture, h.profession) flatMap (
////						gi => nonZeroMod(gi.name, fromGi(gi)))
////					hr addModifiers (key, nonZeroMod(Bought, bought(h)).toList ::: allGi)
////				}
////				boughtGiMod(Humanoid.keyLe, _.boughtLe, _.le)(
////					boughtGiMod(Humanoid.keyAu, _.boughtAu, _.au)(
////						boughtGiMod(Humanoid.keyAe, _.boughtAe, _.ae)(
////							boughtGiMod(Humanoid.keyKe, _.boughtKe, _ => 0)(
////								boughtGiMod(Humanoid.keyMr, _.boughtMr, _.mr)(attributesApplied)
////							)
////						)
////					)
////				)
////			}
////    
////			allDerived
//		}

// vim: set ts=2 sw=2 et:
