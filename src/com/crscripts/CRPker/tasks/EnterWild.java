package com.crscripts.CRPker.tasks;

import com.crscripts.CRPker.util.Util;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Time;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 19:45
 */
public class EnterWild extends Strategy implements Runnable {
	public boolean validate() {
		if (Util.isWieldingEquipment() && Util.inventoryHasItems())
			return (SceneEntities.getNearest(38698) != null);
		return false;
	}

	public void run() {
		if (Util.interactWithObject("Enter", 38698)) {
			int time = 0;
			while ((SceneEntities.getNearest(38700) == null) && time <= 4000) {
				time += 50;
				Time.sleep(50);
			}
		}
	}
}