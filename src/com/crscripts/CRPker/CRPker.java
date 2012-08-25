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

	protected void setup() {
		submit(new DetectSetup());
		provide(new UseBank());
		provide(new EnterWild());
		provide(new FindOpponent());
		provide(new Consumables());
		provide(new Vengeance());
		provide(new UseSpec());
		provide(new Loot());
	}

}
