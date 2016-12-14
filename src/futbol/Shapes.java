package futbol;

import java.awt.*;

public class Shapes extends Rectangle{
	void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(x*Main.width/1200,y*Main.height/800,(width)*Main.width/1200,height*Main.height/800);
	}
	Shapes(int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
}
