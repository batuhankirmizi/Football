package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;

class Player extends Circle {
	String name;
	final int SIZE = 40;
	final int SPEED = 6;
	public double movspeed=8;
	public Color color=Color.cyan;
	double xPos;
	double xSpeed=0;
	int i=0;
	double yPos;
	double ySpeed=0;
	int j=0;
	int up;
	int down;
	int left;
	int right;

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
