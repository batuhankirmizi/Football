import javafx.scene.shape.Circle;

import java.awt.*;


public class Ball extends Circle{

	double xPos;

	double yPos;

	double xSpeed=1;

	double ySpeed=1;

	final int SIZE=15;

	public Thread ballMover=new Thread(new Runnable(){

		@Override

		public void run(){

			while(true){

				try{

					Thread.sleep(500);

				}catch(InterruptedException e){

					e.printStackTrace();

				}

				xPos+=xSpeed;

				yPos+=ySpeed;

				if(xSpeed>0)xSpeed-=0.5;

				else if(xSpeed<0)xSpeed+=0.5;

				if(ySpeed>0)xSpeed-=0.5;

				else if(ySpeed<0)xSpeed+=0.5;

			}

		}

	});

	public void draw(Graphics g){

		g.setColor(Color.green);

		g.fillOval((int)xPos,(int)yPos,SIZE,SIZE);

	}

	Ball(){

		xPos=Main.WIDTH/2;

		yPos=Main.HEIGHT/2;

	}

	public void hit(double xPos,double yPos,double xSpeed,double ySpeed){

		double ydist=yPos-this.yPos;

		double xdist=xPos-this.xPos;

		double angle=Math.atan2(ydist/xdist);


		double speed=Math.sqrt(ydist*ydist+xdist*xdist);

          this.xSpeed+=xSpeed;

		this.ySpeed+=ySpeed;

		this.xSpeed=speed*Math.cos(angle);

		this.ySpeed=speed*Math.sin(angle);

		

	}


}


