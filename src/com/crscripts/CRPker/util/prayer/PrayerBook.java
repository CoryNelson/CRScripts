package com.crscripts.CRPker.util.prayer;

/**
 * User: Spudda
 * Date: 13/08/12
 * Time: 23:02
 */
public interface PrayerBook {
	public int getId();

	public int getBook();

	public int getRequiredLevel();

	public boolean isActive();

	public boolean isSetQuick();
}