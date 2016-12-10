package futbol;

import javafx.scene.shape.Circle;
import java.awt.*;

class Ball extends Circle{
    double xPos;
    double yPos;
    double xSpeed = 0;
    double ySpeed = 0;
    final int SIZE=15;
    double slow=0.05
    Thread ballMover=new Thread(new Runnable(){
        @Override
        public void run(){
            while(true){
                setCenterX(getCenterX() + xSpeed);
                setCenterY(getCenterY() + ySpeed);
                
                //Ball bounce
                if(getCenterX<=SIZE||getCenterX>=Main.WIDTH-SIZE)
                    xSpeed=-xSpeed;
                if(getCenterY<=SIZE||getCenterY>=Main.HEIGHT-SIZE)
                    YSpeed=-ySpeed;
                
                //Ball slows over time
                if(xSpeed>slow)
                    xSpeed -=slow;
                else if(xSpeed<-slow)
                    xSpeed +=slow;
                else xSpeed=0;
                if(ySpeed>slow)
                    xSpeed -= slow;
                else if(ySpeed<-slow)
                    xSpeed += slow;
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
        this.xSpeed += xSpeed;
        this.ySpeed += ySpeed;
        double angle = Math.atan2(ydist,xdist);
        double speed = Math.sqrt(ydist*ydist+xdist*xdist);
        this.xSpeed = speed*Math.cos(angle);
        this.ySpeed = speed*Math.sin(angle);
        this.xSpeed += xSpeed;	//Bu iki satırın tekrar eklenmesi top ve oyuncu arasındaki açının yanı sıra
        this.ySpeed += ySpeed;	//topun oyuncunun hareket yönüne doğru da hareket etmesini sağlar
    }
}
