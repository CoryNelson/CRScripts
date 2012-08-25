package com.crscripts.CRPker;

import com.crscripts.CRPker.tasks.*;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 17:36
 */
@Manifest(name = "CRPker", authors = {"Cory"})
public class CRPker extends ActiveScript {

	private DetectSetup detectSetup;

	protected void setup() {
		submit(detectSetup = new DetectSetup());
		provide(new UseBank(this));
		provide(new EnterWild());
		provide(new FindOpponent());
		provide(new Consumables());
		provide(new Vengeance());
		provide(new UseSpec());
		provide(new Loot());
	}

	public DetectSetup getSetup() {
		return detectSetup;
	}
}
