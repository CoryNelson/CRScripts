package com.crscripts.domtower.boss.bosses;

import com.crscripts.domtower.boss.AbstractBoss;
import com.crscripts.domtower.util.prayer.Pray;
import com.crscripts.domtower.util.prayer.Protect;

/**
 * User: Cory
 * Date: 22/08/12
 * Time: 00:58
 */
public class TarnRazorlor extends AbstractBoss {

	public TarnRazorlor() {
		super(Protect.MELEE, new int[]{-1});
	}

	protected void run() {
		super.run();
		if(validate()) {
			if (!Pray.prayerActive(19))
				Pray.toggle(19);
		}
	}

	protected int[] getNeededItems() {
		return null;
	}
}
