package com.crscripts.CRRevs.tasks;

import com.crscripts.CRRevs.Util;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.wrappers.Tile;

/**
 * User: Cory
 * Date: 24/08/12
 * Time: 19:21
 */
public class WalkToRevs extends Strategy implements Runnable {
	public boolean validate() {
		return Util.inCave() && Util.isWieldingEquipment();
	}

	public void run() {
		Walking.findPath(new Tile(3116, 10103, 0)).traverse();
	}
}