package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;

class Player extends Circle {
    String name;
    final int SIZE = 30;
    final int SPEED = 3;
	double movspeed=3;
    int xSpeed=0;
    int ySpeed=0;

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
