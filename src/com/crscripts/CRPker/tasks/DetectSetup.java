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
			equipIds.add(new PkItem(id, 1));
		for (Item item : Inventory.getItems()) {
			PkItem pkItem = get(invItems, item.getId());
			if (pkItem == null)
				invItems.add(new PkItem(item.getId(), item.getStackSize()));
			else
				pkItem.amt += item.getStackSize();
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

		public PkItem(int id, int amt) {
			this.id = id;
			this.amt = amt;
		}
	}
}