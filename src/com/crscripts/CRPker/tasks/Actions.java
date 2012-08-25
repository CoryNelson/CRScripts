package com.crscripts.CRPker.tasks;

import org.powerbot.concurrent.strategy.Strategy;

import java.util.LinkedList;

/**
 * User: Cory
 * Date: 25/08/12
 * Time: 18:40
 */
public class Actions extends Strategy implements Runnable {

	private final LinkedList queue = new LinkedList();

	public boolean validate() {
		return !queue.isEmpty();
	}

	public void run() {
		Runnable r;
		synchronized (queue) {
			r = (Runnable) queue.removeFirst();
		}
		try {
			r.run();
		} catch (RuntimeException e) {
		}
	}

	public void add(Runnable r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}
}