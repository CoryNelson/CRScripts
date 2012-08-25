package com.crscripts.CRPker.tasks;

import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 19:37
 */
public class DetectSetup extends Strategy implements Runnable {

	public static ConcurrentLinkedQueue<PkItem> equipIds = new ConcurrentLinkedQueue<PkItem>();
	public static ConcurrentLinkedQueue<PkItem> invItems = new ConcurrentLinkedQueue<PkItem>();

	public boolean validate() {
		return true;
	}

	public void run() {
		for (int id : Equipment.getAppearanceIds())
			if (id != -1)
				equipIds.add(new PkItem(id, 1, false));
		for (Item item : Inventory.getItems()) {
			if (item == null || item.getId() == -1)
				continue;
			PkItem pkItem = get(invItems, item.getId());
			if (pkItem == null)
				invItems.add(new PkItem(item.getId(), item.getStackSize(), true));
			else {
				pkItem.stacks = false;
				pkItem.amt += item.getStackSize();
			}
		}
	}

	private PkItem get(ConcurrentLinkedQueue<PkItem> list, int id) {
		for (PkItem item : list)
			if (item.id == id)
				return item;
		return null;
	}

	public class PkItem {
		public int id;
		public int amt;
		public boolean stacks;

		public PkItem(int id, int amt, boolean stacks) {
			this.id = id;
			this.amt = amt;
			this.stacks = stacks;
		}
	}
}