package efa.dsa.equipment.services

import efa.dsa.equipment._
import efa.rpg.items.specs.EditableProps
import implicits._

object AmmunitionEditableTest extends EditableProps[AmmunitionItem]("AmmunitionEditable")

object ArmorEditableTest extends EditableProps[ArmorItem]("ArmorEditable")

object ArticleEditableTest extends EditableProps[ArticleItem]("ArticleEditable")

object MeleeWeaponEditableTest extends EditableProps[MeleeWeaponItem]("MeleeWeaponEditable")

object RangedWeaponEditableTest extends EditableProps[RangedWeaponItem]("RangedWeaponEditable")

object ShieldEditableTest extends EditableProps[ShieldItem]("ShieldEditable")

object ZoneArmorEditableTest extends EditableProps[ZoneArmorItem]("ZoneArmorEditable")

// vim: set ts=2 sw=2 et nowrap:
