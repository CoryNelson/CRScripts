package com.crscripts.domtower.boss;

import java.util.HashMap;

/**
 * User: Cory
 * Date: 21/08/12
 * Time: 09:31
 */
public class BossHandler {
	private static HashMap<String, AbstractBoss> bosses = new HashMap<String, AbstractBoss>();

	public static void loadBosses() {

	}

	public static AbstractBoss getBoss(String boss) {
		return bosses.get(boss);
	}
}
