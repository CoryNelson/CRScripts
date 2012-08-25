package com.crscripts.CRRevs.tasks;

import com.crscripts.CRRevs.Util;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.LinkedList;

import static com.crscripts.CRRevs.paint.Paint.setStatus;

/**
 * User: Cory
 * Date: 24/08/12
 * Time: 17:04
 */
public class WalkToCave extends Strategy implements Runnable {

	private LinkedList<Locatable> path = new LinkedList<Locatable>();
	private int[] wallIDs = {65080, 65084};
	private Locatable currentPath;
	private LocalPath walkPath;

	public boolean validate() {
		return !Util.inCave() && Util.isWieldingEquipment();
	}

	public void run() {
		setStatus("Traversing To Cave: " + path.size());
		Player me = Players.getLocal();
		int y = me.getLocation().getY();
		if (y >= 3521) {
			SceneObject cave = SceneEntities.getNearest(20600);
			if (cave == null || !cave.isOnScreen()) {
				if (walkPath == null || !walkPath.validate() || Calculations.distanceTo(currentPath) < 8) {
					if (path.size() > 0)
						walkPath = Walking.findPath(currentPath = path.removeFirst());
					else if (path.size() == 0 && cave != null) {
						Walking.walk(cave);
					} else {
						path.clear();
						path.add(new Tile(3071, 3631, 0).randomize(3, 2));
					}
				} else
					walkPath.traverse();
			} else {
				if (Util.interactWithObject("Enter", cave.getId())) {

				}
			}
		} else {
			if (Calculations.distanceTo(new Tile(3069, 3520, 0)) > 4)
				Walking.walk(new Tile(3069, 3520, 0));
			else if (Util.interactWithObject("Cross", wallIDs)) {
				int time = 0;
				while ((y <= 3521) && time <= 4000) {
					time += 50;
					Time.sleep(50);
				}
			}
		}
	}
}