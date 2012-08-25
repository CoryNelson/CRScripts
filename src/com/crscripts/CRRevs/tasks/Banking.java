package com.crscripts.CRRevs.tasks;

import com.crscripts.CRRevs.CRRevs;
import com.crscripts.CRRevs.Util;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;

import static com.crscripts.CRRevs.paint.Paint.setStatus;

/**
 * User: Cory
 * Date: 24/08/12
 * Time: 15:29
 */
public class Banking extends Strategy implements Runnable {

	private CRRevs crRevs;

	public Banking(CRRevs crRevs) {
		this.crRevs = crRevs;
	}

	public boolean validate() {
		return crRevs.isDead || !Util.isWieldingEquipment();
	}

	public void run() {
		setStatus("Banking");
		if (!Util.inventoryHasMissing() && Bank.open()) {
			for (int id : Util.wieldIds) {
				if (Bank.getItemCount(id) == 0 && Inventory.getCount(id) == 0)
					crRevs.stop();
				else {
					if (Inventory.getCount(id) == 0 && Bank.getItemCount(id) > 0)
						Bank.withdraw(id, 1);
					Time.sleep(Random.nextInt(50, 150));
				}
			}
		}
		if (Util.inventoryHasMissing() && Bank.close()) {
			for (int id : Util.wieldIds) {
				if (Inventory.getCount(id) != 0) {
					Inventory.getItem(id).getWidgetChild().click(true);
				}
			}
		}
	}
}