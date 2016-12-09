import javafx.scene.shape.Circle;
import java.awt.*;

public class Ball extends Circle{
	double xPos;
	double yPos;
	double xSpeed=1;
	double ySpeed=1;
	final int SIZE=15;
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
		double tangent=Math.tan(ydist/xdist);
		double cotangent=Math.tan(ydist/xdist);
		double speed=Math.sqrt(ydist*ydist+xdist*xdist);
		this.xSpeed=speed*cotangent;
		this.ySpeed=speed*tangent;
		this.xSpeed+=xSpeed/2;
		this.ySpeed+=ySpeed/2;
	}

}
