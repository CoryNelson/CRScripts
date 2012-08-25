package com.crscripts.domtower.util;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * User: Cory
 * Date: 16/08/12
 * Time: 23:56
 */
public class Methods {

	protected boolean hpIsLow() {
		int ch = Integer.parseInt(Widgets.get(748, 8).getText());
		double percent = getPercent(ch, Skills.CONSTITUTION, 10);
		double eatAt = (Skills.getLevel(Skills.CONSTITUTION) * 0.6);
		return (percent < eatAt);
	}

	protected boolean prayerIsLow() {
		int cp = Integer.parseInt(Widgets.get(749, 6).getText());
		double percent = getPercent(cp, Skills.PRAYER, 10);
		double eatAt = (Skills.getLevel(Skills.PRAYER) * 0.4);
		return (percent < eatAt);
	}

	protected double getPercent(int lvl, int skill, int boost) {
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
}
