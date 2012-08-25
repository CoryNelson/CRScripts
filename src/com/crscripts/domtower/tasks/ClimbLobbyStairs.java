package com.crscripts.domtower.tasks;

import com.crscripts.domtower.DominionTower;
import com.crscripts.domtower.util.Util;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class ClimbLobbyStairs extends Strategy implements Runnable {

	private WidgetChild climberMode = Widgets.get(1164, 0);
	private DominionTower dominionTower;

	public ClimbLobbyStairs(DominionTower dominionTower) {
		this.dominionTower = dominionTower;
	}

	public boolean validate() {
		if (!Util.inLobby())
			return false;
		if (dominionTower.getBoss() == null)
			return true;
		if (dominionTower.getBoss().resetProgress())
			return false;
		return true;
	}

	public void run() {
		if (Util.interactWithObject("Climb-up", 62678)) {
			int time = 0;
			while (!climberMode.validate() && time <= 4000) {
				time += 50;
				Time.sleep(50);
			}
		}
	}
}
