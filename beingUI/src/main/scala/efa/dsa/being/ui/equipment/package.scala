package efa.dsa.being.ui

import efa.dsa.being.equipment._
import efa.nb.dialog.{DialogEditable ⇒ DE}

package object equipment {

  implicit lazy val ArmorEditable =
    DE.io((a: Armor) ⇒ ArmorPanel.create(a))(_.in)

  implicit lazy val AmmunitionEditable =
    DE.io((a: Ammunition) ⇒ AmmunitionPanel.create(a))(_.in)

  implicit lazy val ArticleEditable =
    DE.io((a: Article) ⇒ ArticlePanel.create(a))(_.in)

  implicit lazy val MeleeWeaponEditable =
    DE.io((a: MeleeWeapon) ⇒ MeleeWeaponPanel.create(a))(_.in)

  implicit lazy val RangedWeaponEditable =
    DE.io((a: RangedWeapon) ⇒ RangedWeaponPanel.create(a))(_.in)

  implicit lazy val ShieldEditable =
    DE.io((a: Shield) ⇒ ShieldPanel.create(a))(_.in)

  implicit lazy val ZoneArmorEditable =
    DE.io((a: ZoneArmor) ⇒ ZoneArmorPanel.create(a))(_.in)
}

// vim: set ts=2 sw=2 et:
