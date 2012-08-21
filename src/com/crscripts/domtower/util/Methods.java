package com.crscripts.domtower.util;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * User: Cory
 * Date: 16/08/12
 * Time: 23:56
 */
public class Methods {

	public static boolean consumed = false;

	protected boolean needToEat() {
		int hp = Integer.parseInt(Widgets.get(748, 8).getText());
		double percent = ((double) hp / (double) (Skills.getRealLevel(Skills.CONSTITUTION) * 10)) * 100;
		double eatAt = (Skills.getLevel(Skills.CONSTITUTION) * 0.6);
		return (percent < eatAt);
	}

	protected boolean walkToAndClick(String action, int... id) {
		SceneObject o = SceneEntities.getNearest(id);
		if (Calculations.distanceTo(o) > 5 && !Players.getLocal().isMoving())
			Walking.walk(o);
		else {
			if (o != null && o.isOnScreen()) {
				Mouse.move(o.getCentralPoint(), 30, 50);
				return (o.interact(action));
			} else {
				Camera.turnTo(o);
			}
		}
		return false;
	}

	protected boolean consume(int... id) {
		for (int i : id) {
			for (Item s : Inventory.getItems()) {
				if (s.getId() == i) {
					if (s.getWidgetChild().click(true)) {
						int time = 0;
						while (!consumed && time <= 2000) {
							time += 50;
							Time.sleep(50);
						}
						if (consumed) {
							consumed = false;
							return true;
						}
					}
					return false;
				}
			}
		}
		return false;
	}
}
