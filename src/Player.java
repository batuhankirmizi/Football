package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;

class Player extends Circle {
    String name;
    final int SIZE = 30;
    final int SPEED = 6;
	public double movspeed=6;
	double speedM=movspeed/Math.log(125);
    double xSpeed=0;
	int i=0;
    double ySpeed=0;
	int j=0;

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
