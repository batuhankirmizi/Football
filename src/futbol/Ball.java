package futbol;

import javafx.scene.shape.Circle;
import java.awt.*;

class Ball extends Circle{
    double xPos;
    double yPos;
    double xSpeed = 0;
    double ySpeed = 0;
    final int SIZE=15;
    double slow=0.5;
	double slowX=0;
	double slowY=0;

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

        g.fillOval((int)xPos*Main.width/1200,(int)yPos*Main.height/800,SIZE*Main.width/1200,SIZE*Main.height/800);
    }

    void hit(double xPos,double yPos,double xSpeed,double ySpeed){
        double ydist = this.yPos-yPos;
        double xdist = this.xPos-xPos;
        this.xSpeed += xSpeed/8;
        this.ySpeed += ySpeed/8;
        double speed = Math.sqrt(ySpeed*ySpeed+xSpeed*xSpeed)+20;
		double angle = Math.atan2(ydist,xdist);
        this.xSpeed = speed*Math.cos(angle);
		this.ySpeed = speed*Math.sin(angle);
        this.xSpeed += xSpeed/8;	//Bu iki satırın tekrar eklenmesi top ve oyuncu arasındaki açının yanı sıra
        this.ySpeed += ySpeed/8;	//topun oyuncunun hareket yönüne doğru da hareket etmesini sağlar
    }
}
