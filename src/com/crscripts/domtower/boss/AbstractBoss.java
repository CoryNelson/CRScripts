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

	protected final Protect prayer;
	protected final int[] bossIds;
	protected boolean attack;
	protected NPC boss;

	public AbstractBoss(Protect prayer, int... bossIds) {
		this.prayer = prayer;
		this.bossIds = bossIds;
	}

	protected void run() {
		if ((boss == null || !boss.validate()))
			boss = NPCs.getNearest(bossIds);
		else {
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
			if (Pray.getPoints() > 200) {
				if (!Pray.prayerActive(19))
					Pray.toggle(19);
				if (!Pray.prayerActive(prayer.getId()))
					Pray.toggle(prayer.getId());
			} else {
				for (int i = 0; i < 2; i++) {
					consume(Consumables.PRAY_POT);
					attack = true;
				}
			}
			if (needToEat()) {
				for (int i = 0; i < 2; i++) {
					consume(Consumables.FOOD);
					attack = true;
				}
			}
		}
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
