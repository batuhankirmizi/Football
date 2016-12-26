package futbol;

import javafx.scene.shape.Circle;
import java.awt.*;

class Ball extends Circle{
    double xPos;
    double yPos;
    double xSpeed = 0;
    double ySpeed = 0;
    final byte SIZE=15;
    double slow=0.175;
	double slowX=0;
	double slowY=0;
	static final int limit = 10;

    Ball(){
        xPos = Main.width/2;
        yPos = Main.height/2;

        setCenterX(xPos);
        setCenterY(yPos);
    }

    void draw(Graphics g){
        g.setColor(Color.green);

        xPos = getCenterX();
        yPos = getCenterY();

        g.fillOval((int)(xPos-SIZE/2)*Main.width/1200,(int)(yPos-SIZE/2)*(Main.height)/800,SIZE*Main.width/1200,SIZE*Main.width/1200);
    }

    void hit(double xPos,double yPos,double xSpeed,double ySpeed){
        double ydist = this.yPos-yPos;
        double xdist = this.xPos-xPos;
        this.xSpeed += xSpeed/3;
        this.ySpeed += ySpeed/3;
        double speed = Math.sqrt(ySpeed*ySpeed+xSpeed*xSpeed) + 10;
		double angle = Math.atan2(ydist,xdist);
        this.xSpeed = speed*Math.cos(angle);
		this.ySpeed = speed*Math.sin(angle);
        this.xSpeed += xSpeed/3;	//Bu iki satırın tekrar eklenmesi top ve oyuncu arasındaki açının yanı sıra
        this.ySpeed += ySpeed/3;	//topun oyuncunun hareket yönüne doğru da hareket etmesini sağlar
    }
}
