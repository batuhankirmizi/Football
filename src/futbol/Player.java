package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;

class Player extends Circle {
	String name;
	final int SIZE = 40;
	final int SPEED = 8;
	public double movspeed=8;
	double xSpeed=0;
	int i=0;
	double ySpeed=0;
	int j=0;
	int up;
	int down;
	int left;
	int right;

	Player(String name, double xPos, double yPos) {
		this.name = name;

		setCenterX(xPos);
		setCenterY(yPos);
		setRadius(SIZE);
	}

	void draw(Graphics g) {
		g.setColor(Color.ORANGE);

		g.fillOval((int)getCenterX(), (int)getCenterY(), (int)getRadius(), (int)getRadius());
	}
}
