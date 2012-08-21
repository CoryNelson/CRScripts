package com.crscripts.domtower.util.prayer;

/**
 * User: Cory
 * Date: 22/08/12
 * Time: 00:17
 */
public enum Protect {
	NONE(-1), MELEE(-1), RANGE(-1), MAGIC(-1);

	private int id;

	Protect(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}