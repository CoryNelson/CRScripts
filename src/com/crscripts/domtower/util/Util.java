package com.crscripts.domtower.util;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * User: Cory
 * Date: 21/08/12
 * Time: 21:20
 */
public class Util {
	public static boolean inLobby() {
		Tile loc = Players.getLocal().getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int z = loc.getPlane();
		return (x >= 3725 && x <= 3760) && (y >= 6400 && y <= 6445) && z == 0;
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
