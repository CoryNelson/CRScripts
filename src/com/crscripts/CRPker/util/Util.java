package com.crscripts.CRPker.util;

import com.crscripts.CRPker.tasks.DetectSetup;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 21:41
 */
public class Util {

	public static ConcurrentLinkedQueue<Integer> wieldIds = new ConcurrentLinkedQueue<Integer>();

	public static boolean inventoryHasItems() {
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

	public static boolean inventoryHasMissing() {
		boolean inventoryHasMissing = true;
		for (int id : wieldIds)
			if (Inventory.getCount(id) == 0)
				inventoryHasMissing = false;
		return inventoryHasMissing;
	}

	public static boolean isWieldingEquipment() {
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

	public static boolean interactWithObject(String action, int... id) {
		SceneObject o = SceneEntities.getNearest(id);
		if (canInteract(o, 5))
			return (o.interact(action));
		return false;
	}

	public static boolean canInteract(Locatable loc, int dist) {
		if (Calculations.distanceTo(loc) > dist && !Players.getLocal().isMoving()) {
			Walking.walk(loc);
			return false;
		}
		if (!loc.getLocation().isOnScreen()) {
			Camera.turnTo(loc);
			return false;
		}
		return true;
	}
}
