package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

class Direk extends Circle {
	public static ArrayList<Direk> direkler=new ArrayList<>();
	final int SIZE=10;
	double xPos;
	double yPos;

	Direk(int xPos, int yPos) {
		this.xPos=xPos;
		this.yPos=yPos;
		direkler.add(this);
	}

	void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)(xPos-SIZE/2)*(Main.width)/1200, (int)((yPos-SIZE/2)*(Main.height))/800, (int)SIZE*Main.width/1200, (int)SIZE*Main.width/1200);
	}
}
