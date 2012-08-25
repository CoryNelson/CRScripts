package com.crscripts.domtower;

import com.crscripts.domtower.boss.AbstractBoss;
import com.crscripts.domtower.boss.BossHandler;
import com.crscripts.domtower.tasks.ClimbLobbyStairs;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;

/**
 * User: Cory
 * Date: 21/08/12
 * Time: 09:24
 */
@Manifest(name = "DominionTower", authors = {"Cory"})
public class DominionTower extends ActiveScript {

	private AbstractBoss boss;

	protected void setup() {
		BossHandler.loadBosses();
		provide(new ClimbLobbyStairs(this));
	}

	public AbstractBoss getBoss() {
		return boss;
	}

	public void setBoss(AbstractBoss boss) {
		this.boss = boss;
	}
}
