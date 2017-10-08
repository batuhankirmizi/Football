package futbol;

import java.awt.*;

class Shapes extends Rectangle{
	Shapes(int x,int y,int width,int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}

	void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(Main.engine.scaleX(x), Main.engine.scaleY(y), Main.engine.scaleSizeX(width), Main.engine.scaleSizeY(height));
	}
}