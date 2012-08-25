package com.crscripts.CRRevs;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: Cory
 * Date: 24/08/12
 * Time: 17:04
 */
public class Util {

	public static ConcurrentLinkedQueue<Integer> wieldIds = new ConcurrentLinkedQueue<Integer>();
	public static ConcurrentLinkedQueue<Integer> equipIds = new ConcurrentLinkedQueue<Integer>();

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
		for (int id : equipIds) {
			boolean wieldingId = false;
			for (int eId : Equipment.getAppearanceIds()) {
				if (eId == id)
					wieldingId = true;
			}
			if (!wieldingId) {
				wieldingAll = false;
				wieldIds.add(id);
			}
		}
		return wieldingAll;
	}

	public static boolean inCave() {
		Tile loc = Players.getLocal().getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int z = loc.getPlane();
		return (x >= 3050 && x <= 3150) && (y >= 10020 && y <= 100190) && z == 0;
	}

	public static boolean interactWithObject(String action, int... id) {
		SceneObject o = SceneEntities.getNearest(id);
		if (canInteract(o, 5))
			return (o.interact(action));
		return false;
	}

	public static boolean interactWithNPC(String action, int... id) {
		NPC n = NPCs.getNearest(id);
		if (canInteract(n, 5))
			return (n.interact(action));
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
