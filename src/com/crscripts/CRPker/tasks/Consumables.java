package com.crscripts.CRPker.tasks;

import com.crscripts.CRPker.util.prayer.Pray;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 19:35
 */
public class Consumables extends Strategy implements Runnable {
	public boolean validate() {
		return hpIsLow() || prayerIsLow() || !Pray.prayerActive(19);
	}

	public void run() {
		if (!Pray.prayerActive(19))
			Pray.toggle(19);
		if (prayerIsLow() && consume(2, PRAY_POT)) {
			FindOpponent.opponent.interact("Attack");
		}
		if (hpIsLow() && consume(FOOD)) {
			FindOpponent.opponent.interact("Attack");
		}
	}

	private boolean hpIsLow() {
		int ch = Integer.parseInt(Widgets.get(748, 8).getText());
		double percent = getPercent(ch, Skills.CONSTITUTION, 10);
		double eatAt = (Skills.getLevel(Skills.CONSTITUTION) * 0.6);
		return (percent < eatAt);
	}

	private boolean prayerIsLow() {
		int cp = Integer.parseInt(Widgets.get(749, 6).getText());
		double percent = getPercent(cp, Skills.PRAYER, 10);
		double eatAt = (Skills.getLevel(Skills.PRAYER) * 0.4);
		return (percent < eatAt);
	}

	private double getPercent(int lvl, int skill, int boost) {
		double realLvl = Skills.getRealLevel(skill) * boost;
		return ((double) lvl / realLvl) * 100;
	}

	protected boolean consume(int amt, int... id) {
		boolean consumed = true;
		for (int i = 0; i < amt; i++)
			if (!consume(id))
				consumed = false;
		return false;
	}

	protected boolean consume(int... id) {
		for (int i : id) {
			for (Item s : Inventory.getItems()) {
				if (s.getId() == i) {
					if (s.getWidgetChild().click(true)) {
						int time = 0;
						while (Players.getLocal().getAnimation() != 829 && time <= 2000) {
							time += 50;
							Time.sleep(50);
						}
						return (Players.getLocal().getAnimation() == 829);
					}
					return false;
				}
			}
		}
		return false;
	}

	public final static int[] FOOD = {
			379,   //Lobster
			373,   //Swordfish
			7946,  //Monkfish
			22342, //Scorpion Meat
			385,   //Sharks
			15272  //Rocktails
	};

	public final static int[] PRAY_POT = {
			//flask, potion
			23253, 143,  //1dose
			23251, 141,  //2dose
			23249, 139,  //3dose
			23247, 2434, //4dose
			23245,        //5dose
			23243,        //6dose
	};
}