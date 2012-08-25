package com.crscripts.domtower.boss;

import com.crscripts.domtower.boss.bosses.TarnRazorlor;

import java.util.HashMap;

/**
 * User: Cory
 * Date: 21/08/12
 * Time: 09:31
 */
public class BossHandler {
	private static HashMap<String, AbstractBoss> bosses = new HashMap<String, AbstractBoss>();

	public static void loadBosses() {
		bosses.put("Tarn Razorlor", new TarnRazorlor());
	}

	public static AbstractBoss getBoss(String boss) {
		return bosses.get(boss);
	}
}
