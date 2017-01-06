package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;

class Player extends Circle {
	String name;
	final byte SIZE = 60;
	final byte SPEED = 6;
	public Color color=Color.cyan;
	double xPos;
	double xSpeed=0;
	byte i=0;
	double yPos;
	double ySpeed=0;
	byte j=0;
	short up;
	short down;
	short left;
	short right;

	Player(String name, double xPos, double yPos) {
		this.name = name;

		this.xPos=xPos;
		this.yPos=yPos;
		setRadius(SIZE);
	}

	void draw(Graphics g) {
		/*
		g.setColor(Color.ORANGE);
		g.fillOval((int)xPos*(Main.width)/1200, (int)(yPos*(Main.height-(Main.menuBar.isVisible()?+20:0)))/800, (int)SIZE*Main.width/1200, (int)SIZE*Main.width/1200);
		*/

		g.setColor(color);
		g.fillOval((int)(xPos-SIZE/2)*(Main.width)/1200, (int)((yPos-SIZE/2)*(Main.height))/800, (int)SIZE*Main.width/1200, (int)SIZE*Main.width/1200);

	}
}
