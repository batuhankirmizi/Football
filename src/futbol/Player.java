package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;

class Player extends Circle {
	String name;
	final byte SIZE = 60;
	final byte SPEED = 6;
	public Color color=Color.cyan;
	double xPos;
	double xSpeed=0;
	byte i=0;
	double yPos;
	double ySpeed=0;
	byte j=0;
	short up;
	short down;
	short left;
	short right;

	Player(String name, int xPos, int yPos) {
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

	void move(){
		Player enem=enemy();
		if((Main.distance(xPos+xSpeed,yPos+ySpeed,enem.xPos,enem.yPos)<=SIZE)){
			double angel=Math.atan2(yPos-enem.yPos,xPos-enem.xPos);
			yPos=yPos+ySpeed*Math.cos(angel);
			xPos=xPos+xSpeed*Math.sin(angel);
		}else{
			yPos=yPos+ySpeed;
			xPos=xPos+xSpeed;
		}
	}
	public Player enemy(){
		for(Player p:Main.players)if(p!=this)return p;
		return null;
	}
}
