package com.crscripts.CRPker.tasks;

import com.crscripts.CRPker.CRPker;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 19:45
 */
public class UseBank extends Strategy implements Runnable {

	public ConcurrentLinkedQueue<Integer> wieldIds;
	private CRPker crPker;

	public UseBank(CRPker crPker) {
		wieldIds = new ConcurrentLinkedQueue<Integer>();
		this.crPker = crPker;
	}

	public boolean validate() {
		return !isWieldingEquipment() || !inventoryHasItems();
	}

	public void run() {
		if (!isWieldingEquipment()) {
			if (!inventoryHasMissing() && Bank.open()) {
				if (Inventory.isFull())
					Bank.depositInventory();
				for (int id : wieldIds) {
					if (Bank.getItemCount(id) == 0 && Inventory.getCount(id) == 0)
						crPker.stop();
					else {
						if (Inventory.getCount(id) == 0 && Bank.getItemCount(id) > 0)
							Bank.withdraw(id, 1);
						Time.sleep(Random.nextInt(50, 150));
					}
				}
			}
			if (inventoryHasMissing() && Bank.close()) {
				for (int id : wieldIds) {
					if (Inventory.getCount(id) != 0) {
						Inventory.getItem(id).getWidgetChild().click(true);
					}
				}
			}
		} else if (!inventoryHasItems() && Bank.open()) {
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

	private boolean inventoryHasItems() {
		for (DetectSetup.PkItem item : DetectSetup.invItems) {
			Item i = Inventory.getItem(item.id);
			if (i == null)
				return false;
			if (Inventory.getCount(item.id) == item.amt)
				continue;
			if (i.getStackSize() == item.amt)
				continue;
			return false;
		}
		return true;
	}

	private boolean inventoryHasMissing() {
		boolean inventoryHasMissing = true;
		for (int id : wieldIds)
			if (Inventory.getCount(id) == 0)
				inventoryHasMissing = false;
		return inventoryHasMissing;
	}

	private boolean isWieldingEquipment() {
		boolean wieldingAll = true;
		wieldIds.clear();
		for (DetectSetup.PkItem item : DetectSetup.equipIds) {
			boolean wieldingId = false;
			for (int eId : Equipment.getAppearanceIds()) {
				if (eId == item.id)
					wieldingId = true;
			}
			if (!wieldingId) {
				wieldingAll = false;
				wieldIds.add(item.id);
			}
		}
		return wieldingAll;
	}
}