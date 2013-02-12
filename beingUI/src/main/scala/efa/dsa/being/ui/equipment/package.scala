package efa.dsa.being.ui

import efa.dsa.being.equipment._
import efa.nb.dialog.{DialogEditable ⇒ DE}

package object equipment {
  implicit lazy val ArmorEditable = DE.io1(ArmorPanel.create)(_.in)
  implicit lazy val AmmunitionEditable = DE.io1(AmmunitionPanel.create)(_.in)
  implicit lazy val ArticleEditable = DE.io1(ArticlePanel.create)(_.in)
  implicit lazy val MeleeWeaponEditable = DE.io1(MeleeWeaponPanel.create)(_.in)
  implicit lazy val RangedWeaponEditable = DE.io1(RangedWeaponPanel.create)(_.in)
  implicit lazy val ShieldEditable = DE.io1(ShieldPanel.create)(_.in)
  implicit lazy val ZoneArmorEditable = DE.io1(ZoneArmorPanel.create)(_.in)
}

// vim: set ts=2 sw=2 et:
