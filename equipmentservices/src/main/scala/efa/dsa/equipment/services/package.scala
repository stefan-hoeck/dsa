package efa.dsa.equipment

import efa.dsa.equipment.services.ui._
import efa.rpg.items.controller.editable

package object services {
  implicit val ArmorE = editable(ArmorPanel.apply)
  implicit val ArticleE = editable(ArticlePanel.apply)
  implicit val AmmunitionE = editable(AmmunitionPanel.apply)
  implicit val MeleeWeaponE = editable(MeleeWeaponPanel.apply)
  implicit val RangedWeaponE = editable(RangedWeaponPanel.apply)
  implicit val ShieldE = editable(ShieldPanel.apply)
  implicit val ZoneArmorE = editable(ZoneArmorPanel.apply)
}

// vim: set ts=2 sw=2 et:
