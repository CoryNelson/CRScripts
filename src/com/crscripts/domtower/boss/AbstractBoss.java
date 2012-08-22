package com.crscripts.domtower.boss;

import com.crscripts.domtower.mode.climber.handicap.Handicaps;
import com.crscripts.domtower.util.Consumables;
import com.crscripts.domtower.util.Methods;
import com.crscripts.domtower.util.prayer.Pray;
import com.crscripts.domtower.util.prayer.Protect;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;

/**
 * User: Cory
 * Date: 21/08/12
 * Time: 09:28
 */
public abstract class AbstractBoss extends Methods {

	protected final Protect protect;
	protected final int[] bossIds;
	protected boolean attack;
	protected NPC boss;

	public AbstractBoss(Protect protect, int... bossIds) {
		this.protect = protect;
		this.bossIds = bossIds;
	}

	protected void run() {
		if(validate()) {
			if (attack || Players.getLocal().getInteracting() == null) {
				attack = false;
				if (!boss.isOnScreen())
					Walking.walk(boss);
				if (boss != null && boss.interact("Attack")) {
					int time = 0;
					while ((boss != null && !boss.isInCombat()) && time <= 5000) {
						Time.sleep(50);
						time += 50;
					}
				}
			}
			if(protect != null && protect != Protect.NONE) {
				if (prayerIsLow() && consume(2, Consumables.PRAY_POT))
						attack = true;
				if (hpIsLow() && consume(2, Consumables.FOOD))
						attack = true;
				if (!Pray.prayerActive(protect.getId()))
					Pray.toggle(protect.getId());
			}
		} else
			boss = NPCs.getNearest(bossIds);

	}

	protected boolean validate() {
		return boss != null && boss.validate();
	}

	protected boolean resetProgress() {
		if (Handicaps.NO_FOOD.isActive())
			return true;
		if (Handicaps.NO_PRAYERS.isActive())
			return true;
		return false;
	}

	protected abstract int[] getNeededItems();
}
