package futbol;

import javafx.scene.shape.Circle;
import java.awt.*;

class Circly extends Circle{
	double xPos;
	double yPos;
	double xSpeed=0;
	double ySpeed=0;
	byte SIZE;
	Color color=Color.cyan;

	void draw(Graphics g){
		g.setColor(color);
		g.fillOval((int)(xPos-SIZE/2)*Main.width/1200,(int)(yPos-SIZE/2)*(Main.height)/800,SIZE*Main.width/1200,SIZE*Main.width/1200);
	}
}