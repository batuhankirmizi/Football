package futbol;

import javafx.scene.shape.Circle;
import java.awt.*;

class Ball extends Circle{
    double xPos;
    double yPos;
    double xSpeed = 0;
    double ySpeed = 0;
    final int SIZE=15;

    Thread ballMover=new Thread(new Runnable(){
        @Override
        public void run(){
            while(true){
                setCenterX(getCenterX() + xSpeed);
                setCenterY(getCenterY() + ySpeed);

                if(xSpeed>0.05)
                    xSpeed -= 0.05;
                else if(xSpeed<-0.05)
                    xSpeed += 0.05;
                else xSpeed=0;
                
                if(ySpeed>0.05)
                    xSpeed -= 0.05;
                else if(ySpeed<-0.05)
                    xSpeed += 0.05;
                else xSpeed=0
                try{
                    Thread.sleep(32);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    });

    Ball(){
        xPos = Main.WIDTH/2;
        yPos = Main.HEIGHT/2;

        setCenterX(xPos);
        setCenterY(yPos);
    }

    void draw(Graphics g){
        g.setColor(Color.green);

        xPos = getCenterX();
        yPos = getCenterY();

        g.fillOval((int)xPos,(int)yPos,SIZE,SIZE);
    }

    void hit(double xPos,double yPos,double xSpeed,double ySpeed){
        double ydist = yPos-this.yPos;
        double xdist = xPos-this.xPos;
        double angle = Math.atan2(ydist,xdist);
        double speed = Math.sqrt(ydist*ydist+xdist*xdist);
        this.xSpeed += xSpeed;
        this.ySpeed += ySpeed;
        this.xSpeed = speed*Math.cos(angle);
        this.ySpeed = speed*Math.sin(angle);
        this.xSpeed += xSpeed;	//Bu iki satırın tekrar eklenmesi top ve oyuncu arasındaki açının yanı sıra
        this.ySpeed += ySpeed;	//topun oyuncunun hareket yönüne doğru da hareket etmesini sağlar
    }
}
