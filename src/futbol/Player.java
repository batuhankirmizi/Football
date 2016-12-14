package futbol;

import javafx.scene.shape.Circle;

import java.awt.*;

class Player extends Circle implements Runnable {
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
	public void run(){
		while(true) {
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			xSpeed=SPEED*Math.sin(i*Math.PI/250)*Math.cos(j*Math.PI/500);
			ySpeed=SPEED*Math.sin(j*Math.PI/250)*Math.cos(i*Math.PI/500);
			
			
			if(getCenterY()<=0)										//stop player from moving out of frame
			setCenterY(getCenterY() + movspeed);
			if(getCenterY()>=Main.HEIGHT - 70)
			setCenterY(getCenterY() - movspeed);
			if(getCenterX()<=0)
			setCenterX(getCenterX() + movspeed);
			if(getCenterX() >= Main.WIDTH - 40)
			setCenterX(getCenterX() - movspeed);
					
			
			setCenterY(getCenterY() + ySpeed);						//move player
			setCenterX(getCenterX() + xSpeed);
			
			if(i>2)i-=2;			//slower
			else if(i<-2)i+=2;
			else i=0;
			if(j>2)j-=2;
			else if(j<-2)j+=2;
			else j=0;
			
			if(Main.pressed.contains(up) && !Main.pressed.contains(down))	//keylisten
				if(j>-122)j-=3;
				else j=-125;
			else if(!Main.pressed.contains(up) && Main.pressed.contains(down))
				if(j<122)j+=3;
				else j=125;
			
			if(Main.pressed.contains(left) && !Main.pressed.contains(right))
				if(i>-122)i-=3;
				else i=-125;
			else if(!Main.pressed.contains(left) && Main.pressed.contains(right))
				if(i<122)i+=3;
				else i=125;
		}
	}
}
