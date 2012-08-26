package com.crscripts.CRPker.tasks;

import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.Player;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 19:34
 */
public class FindOpponent extends Strategy implements Runnable {

	public static Player opponent;

	public boolean validate() {
		if ((Calculations.distanceTo(2815, 5511) < 200)) {
			if (opponent == null || !Players.getLocal().isInCombat() || !opponent.isInCombat())
				return true;
		}
		return false;
	}

	public void run() {
		if (opponent == null) {
			opponent = Players.getNearest(new Filter<Player>() {
				@Override
				public boolean accept(Player player) {
					if (player.getId() == Players.getLocal().getId())
						return false;
					return player.getInteracting() == null || player.getInteracting() == Players.getLocal();
				}
			});
		}
		if (opponent != null)
			opponent.interact("Attack");
	}
}