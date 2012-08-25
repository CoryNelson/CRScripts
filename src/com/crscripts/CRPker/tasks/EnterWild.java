package com.crscripts.CRPker.tasks;

import com.crscripts.CRPker.util.Util;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 19:45
 */
public class EnterWild extends Strategy implements Runnable {
	public boolean validate() {
		if ((Calculations.distanceTo(2815, 5511) < 2) && !Players.getLocal().isMoving())
			return true;
		if (Util.isWieldingEquipment() && Util.inventoryHasItems())
			return (SceneEntities.getNearest(38698) != null);
		return false;
	}

	public void run() {
		System.out.println("o: " + (Calculations.distanceTo(2815, 5511)));
		if ((Calculations.distanceTo(2815, 5511) < 2)) {
			Time.sleep(400, 500);
			Tile tile = Players.getLocal().getLocation();
			Walking.walk(new Tile(tile.getX(), tile.getY() + 4, 0).randomize(2, 3));
		} else if (Util.interactWithObject("Enter", 38698)) {
			int time = 0;
			while ((Calculations.distanceTo(2815, 5511) > 250) && time <= 4000) {
				time += 50;
				Time.sleep(50);
			}
		}
	}
}