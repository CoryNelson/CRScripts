package com.crscripts.CRRevs;

import com.crscripts.CRRevs.tasks.Banking;
import com.crscripts.CRRevs.tasks.WalkToCave;
import com.crscripts.CRRevs.tasks.WalkToRevs;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.bot.event.MessageEvent;
import org.powerbot.game.bot.event.listener.MessageListener;
import org.powerbot.game.bot.event.listener.PaintListener;

import java.awt.*;

import static com.crscripts.CRRevs.paint.Paint.draw;

/**
 * User: Cory
 * Date: 24/08/12
 * Time: 15:28
 */
@Manifest(name = "CRRevs", authors = {"Cory"})
public class CRRevs extends ActiveScript implements PaintListener, MessageListener {

	private Banking banking;
	public boolean isDead;

	protected void setup() {
		for (int id : Equipment.getAppearanceIds())
			Util.equipIds.add(id);
		provide(new Banking(this));
		provide(new WalkToCave());
		provide(new WalkToRevs());
	}

	public void messageReceived(MessageEvent messageEvent) {
		String message = messageEvent.getMessage();
		int id = messageEvent.getId();
		if (id == 0 && message.contains("Oh dear"))
			isDead = true;
	}

	public void onRepaint(Graphics graphics) {
		draw(graphics);
	}
}
