package com.crscripts.domtower.mode.climber.handicap;

/**
 * User: Cory
 * Date: 21/08/12
 * Time: 09:30
 */
public enum Handicaps {
	DISEASE, REDUCED_MAGIC_ATTACK, SLIPPERY_FINGERS(true), NO_PRAYERS(true), MONSTER_STUN, NO_FAMILIAR(true),
	REDUCED_RANGED_ATTACK, NO_BODY_ARMOUR(true), HALVED_LIFE_POINTS(true), RANDOM_FREEZE(true),
	REDUCED_MELEE_DEFENCE, NO_FOOD(true), NO_SHIELD(true), REDUCED_MELEE_ATTACK(true), LIFE_SAVER,
	NO_POTIONS(true), NO_POWER_UPS, REDUCED_RANGED_DEFENCE, POISON, RANDOM_DAZE(true), REDUCED_MAGIC_DEFENCE,
	NO_SPECIAL_ATTACKS(true);

	private boolean active;
	private boolean trySkip;

	Handicaps(boolean trySkip) {
		this.trySkip = trySkip;
		active = false;
	}

	Handicaps() {
		this(false);
	}

	public Handicaps getNext() {
		return values()[(ordinal() + 1) % values().length];
	}

	public Handicaps getPrevious() {
		if (ordinal() - 1 >= 0)
			return values()[(ordinal() - 1)];
		return values()[(this.ordinal() + values().length - 1) % values().length];
	}

	public static void reset() {
		for (Handicaps h : values())
			h.setActive(false);
	}

	public boolean TrySkip() {
		return trySkip;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
