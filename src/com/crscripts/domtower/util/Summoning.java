package com.crscripts.domtower.util;

import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Widgets;

/**
 * User: Spudda
 * Date: 13/08/12
 * Time: 22:11
 */
public class Summoning {
	public static int getPoints() {
		return Integer.parseInt(Widgets.get(747, 7).getText());
	}

	public static int getSpecial() {
		return Settings.get(1177);
	}

	public static double getTimeLeft() {
		return Double.parseDouble(Widgets.get(662, 43).getText());
	}

	public static boolean isFamiliarSummoned() {
		return Settings.get(448) != -1;
	}
}
