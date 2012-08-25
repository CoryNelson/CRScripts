package com.crscripts.CRPker.tasks;

import com.crscripts.CRPker.CRPker;
import com.crscripts.CRPker.util.Util;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 19:45
 */
public class UseBank extends Strategy implements Runnable {
	private CRPker crPker;

	public UseBank(CRPker crPker) {
		this.crPker = crPker;
	}

	public boolean validate() {
		return !Util.isWieldingEquipment() || !Util.inventoryHasItems();
	}

	public void run() {
		if (!Util.isWieldingEquipment()) {
			if (!Util.inventoryHasMissing() && Bank.open()) {
				for (int id : Util.wieldIds) {
					if (Inventory.isFull()) {
						Bank.depositInventory();
						return;
					}
					if (Bank.getItemCount(id) == 0 && Inventory.getCount(id) == 0)
						crPker.stop();
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
		} else if (!Util.inventoryHasItems() && Bank.open()) {
			for (DetectSetup.PkItem item : DetectSetup.invItems) {
				if (Bank.getItemCount(item.id) == 0 && Inventory.getCount(item.id) == 0)
					crPker.stop();
				else {
					System.out.println(item.id + "." + item.stacks);
					if (item.stacks) {
						Item i = Inventory.getItem(item.id);
						if (i == null)
							Bank.withdraw(item.id, item.amt);
						else if (item.amt - i.getStackSize() > 0)
							Bank.withdraw(item.id, item.amt - i.getStackSize());
					} else {
						if (Inventory.getCount(item.id) != item.amt)
							Bank.withdraw(item.id, item.amt - Inventory.getCount(item.id));
					}
					Time.sleep(Random.nextInt(50, 150));
				}
			}
		}
	}
}