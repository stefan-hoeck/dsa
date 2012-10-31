package efa.dsa.being.calc.combat
////
////import org.efa.data.spi.SignalEditor
////import org.efa.dsa.being.equipment._
////import org.efa.dsa.equipment._
////import org.efa.dsa.world.equipment.{TpKk}
////import org.efa.rpg.core.die.DieRoller
////import org.junit._
////import Assert._
////
////class AttackModeTest extends SignalEditor[RangedWeaponItem] {
//// 	val dataSignal = Equipment.rangedWeapons 
////	add(RangedWeaponItem(2, "Kurzbogen", DieRoller(1, 6, 1), TpKk(12, 3), false,
////											 "Bögen", DefaultReach, DefaultTpPlus, true, 3, true,
////											 0L, 0L, ""))
////	add(RangedWeaponItem(3, "Wurfaxt", DieRoller(1, 6, 3), TpKk(12, 3), false,
////											 "Wurfbeile", DefaultReach, DefaultTpPlus, false, 1, false,
////											 0L, 0L, ""))
////	val melee = new MeleeWeapon() tp_= DieRoller(2, 6, 2) name_= "Schwert"
////	val ranged = new RangedWeapon() tp_= DieRoller(1, 6, 1) copy (parentId = 2) name_= "Kurzbogen"
////	val ammo = new Ammunition() tp_= DieRoller(1, 6, 2) name_= "Pfeile"
////	val throwing = new RangedWeapon() tp_= DieRoller(1, 6, 3) copy (parentId = 3) name_= "Wurfaxt"
////	val shield = new Shield()
////
////	import Hand._, AttackMode._
////	@Test def testUnarmed {
////    val (a :: b :: as) = AttackMode fromHands Hands.Empty
////		assertTrue(a.subdual)
////		assertEquals("Raufen", a.name)
////		assertTrue(b.subdual)
////		assertEquals("Ringen", b.name)
////		assertEquals(Nil, as)
////	}
////
////	@Test def testSingleMelee {
////		val (a :: u :: as) = AttackMode fromHands Hands.OneHanded(Empty, Melee(melee))
////		val (am, um) = (a.asInstanceOf[MeleeSingle], u.asInstanceOf[MeleeSingle])
////		assertEquals(melee.name, a.name)
////		assertFalse(a.subdual)
////		assertTrue(u.subdual)
////		assertFalse(am.wrongHand)
////		assertFalse(um.wrongHand)
////		assertEquals(melee, am.weapon)
////		assertEquals(melee, um.weapon)
////		assertEquals(melee.tp, um.tp)
////		assertEquals(melee.tp, am.tp)
////		assertEquals(Nil, as)
////	}
////	
////	@Test def testSingleMeleeWrongHand {
////		val (a :: u :: as) = AttackMode fromHands Hands.OneHanded(Melee(melee), Empty)
////		val (am, um) = (a.asInstanceOf[MeleeSingle], u.asInstanceOf[MeleeSingle])
////		assertEquals(melee.name, a.name)
////		assertFalse(a.subdual)
////		assertTrue(u.subdual)
////		assertTrue(am.wrongHand)
////		assertTrue(um.wrongHand)
////		assertEquals(melee.tp, um.tp)
////		assertEquals(melee.tp, am.tp)
////		assertEquals(Nil, as)
////	}
////
////	@Test def testMeleeShield {
////		val (a :: u :: as) = AttackMode fromHands Hands.OneHanded(Shield(shield), Melee(melee))
////		val (am, um) = (a.asInstanceOf[MeleeShield], u.asInstanceOf[MeleeShield])
////		assertEquals(melee.name, a.name)
////		assertFalse(a.subdual)
////		assertTrue(u.subdual)
////		assertEquals(melee, am.weapon)
////		assertEquals(melee, um.weapon)
////		assertEquals(shield, am.shield)
////		assertEquals(shield, um.shield)
////		assertEquals(melee.tp, um.tp)
////		assertEquals(melee.tp, am.tp)
////		assertEquals(Nil, as)
////	}
////
////	@Test def testThrowing {
////		val (a :: as) = AttackMode fromHands Hands.OneHanded(Empty, Ranged(throwing))
////		val (am) = (a.asInstanceOf[Throwing])
////		assertEquals(throwing.name, a.name)
////		assertFalse(a.subdual)
////		assertFalse(am.wrongHand)
////		assertEquals(throwing, am.weapon)
////		assertEquals(throwing.tp, am.tp)
////		assertEquals(Nil, as)
////	}
////	
////	@Test def testThrowingWrongHand {
////		val (a :: as) = AttackMode fromHands Hands.OneHanded(Ranged(throwing), Empty)
////		val (am) = (a.asInstanceOf[Throwing])
////		assertEquals(throwing.name, a.name)
////		assertFalse(a.subdual)
////		assertTrue(am.wrongHand)
////		assertEquals(throwing, am.weapon)
////		assertEquals(throwing.tp, am.tp)
////		assertEquals(Nil, as)
////	}
////
////	@Test def testRanged {
////		val (a :: as) = AttackMode fromHands Hands.OneHanded(Ammo(ammo), Ranged(ranged))
////		val (am) = (a.asInstanceOf[Shooting])
////		assertEquals(ranged.name, a.name)
////		assertFalse(a.subdual)
////		assertEquals(ranged, am.weapon)
////		assertEquals(ammo, am.ammo)
////		assertEquals(ammo.tp, am.tp)
////		assertEquals(Nil, as)
////	}
////
////	@Test def testMeleeTwoHanded {
////		val (a :: u :: as) = AttackMode fromHands Hands.TwoHanded(melee)
////		val (am, um) = (a.asInstanceOf[MeleeTwoHanded], u.asInstanceOf[MeleeTwoHanded])
////		assertEquals(melee.name, a.name)
////		assertFalse(a.subdual)
////		assertTrue(u.subdual)
////		assertEquals(melee, am.weapon)
////		assertEquals(melee, um.weapon)
////		assertEquals(melee.tp, um.tp)
////		assertEquals(melee.tp, am.tp)
////		assertEquals(Nil, as)
////	}
////
////	@Test def testLocalization {
////		assertTrue(keyAtFk.locName.nonEmpty)
////		assertTrue(keyAtFk.shortName.nonEmpty)
////		assertTrue(keyAtFk.locName != keyAtFk.shortName)
////		assertTrue(keyPa.locName.nonEmpty)
////		assertTrue(keyTp.locName.nonEmpty)
////		assertTrue(keyTp.shortName.nonEmpty)
////		assertTrue(keyTp.shortName != keyTp.locName)
////		assertTrue(baseLoc.nonEmpty)
////		assertTrue(unarmed.nonEmpty)
////		assertTrue(subdualLoc.nonEmpty)
////		assertTrue(raufen.nonEmpty)
////		assertTrue(ringen.nonEmpty)
////	}
////}
