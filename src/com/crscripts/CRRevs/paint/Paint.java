package com.crscripts.CRRevs.paint;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;

import java.awt.*;

/**
 * User: Cory
 * Date: 24/08/12
 * Time: 16:08
 */
public class Paint {

	private static String status = "Initialising";

	public static void draw(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setStroke(new BasicStroke(2F));
		g.setColor(Color.BLACK);
		g.drawLine(Mouse.getX(), Mouse.getY(), Game.getDimensions().width, Game.getDimensions().height);
		g.drawLine(Mouse.getX(), Mouse.getY(), 0, Game.getDimensions().height);
		g.drawLine(0, 50, Mouse.getX(), Mouse.getY());
		g.drawLine(Game.getDimensions().width, 50, Mouse.getX(), Mouse.getY());

		g.setColor(Color.WHITE);
		g.drawOval(Mouse.getX() - 10, Mouse.getY() - 10, 20, 20);

		g.setStroke(new BasicStroke(1F));
		g.drawString(status, 400, 70);
	}

	public static void setStatus(String status) {
		Paint.status = status;
	}
}
