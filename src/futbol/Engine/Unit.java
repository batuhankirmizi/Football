package futbol.Engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Unit{
	double xPos=0;
	double yPos=0;
	long SPEED=0;
	double xSpeed=0;
	double ySpeed=0;
	BufferedImage image;

	public void render(Graphics g){
		g.drawImage(image, Engine.engine.scaleX(xPos), Engine.engine.scaleY(yPos), Engine.engine.scaleX(image.getWidth()), Engine.engine.scaleY(image.getHeight()), null);
	}
}
