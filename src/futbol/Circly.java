package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;

class Circly extends Circle{
	double xPos=0;
	double yPos=0;
	double xSpeed=0;
	double ySpeed=0;
	byte radius;
	byte size;
	Color color=Color.cyan;

	void draw(Graphics g){
		g.setColor(color);
		g.fillOval(Main.engine.scaleX(xPos-radius), Main.engine.scaleY(yPos-radius), Main.engine.scaleSizeX(size), Main.engine.scaleSizeY(size));
	}
}