package futbol;

import java.awt.*;
import java.util.ArrayList;

class Player extends Circly{
	byte SPEED=6;
	private String name;
	byte i=0;
	byte j=0;
	short up;
	short down;
	short left;
	short right;
	Player enem;
    static final byte decrement=2;
	static final byte increment=3;
	boolean botted;
	Ai bot;
    private final boolean leftT;
	int goalsize=160;
	ArrayList<Direk> direkler=new ArrayList<>();
    double angel;

	Player(String name,int xPos,int yPos, boolean left){
		super();
		radius=30;
		size=60;
		this.name=name;
		this.xPos=xPos;
		this.yPos=yPos;
        this.leftT=left;
	}

	void move(){
		//set player speed
		xSpeed=SPEED*Math.sin(i*Math.PI/250)*Math.cos(j*Math.PI/500);
		ySpeed=SPEED*Math.sin(j*Math.PI/250)*Math.cos(i*Math.PI/500);

        if(ySpeed<0&&yPos+ySpeed<radius) yPos=radius;
		else if(ySpeed>0&&yPos+ySpeed>800-radius) yPos=800-radius;
		else {
			if(Main.distance(xPos+xSpeed,yPos+ySpeed,enem.xPos,enem.yPos)<=radius+enem.radius){
				angel =Math.atan2(yPos-enem.yPos,xPos-enem.xPos);
				yPos+=ySpeed*Math.abs(Math.cos(angel));
				if((yPos-enem.yPos)/(xPos-enem.xPos)<0){
					yPos+=xSpeed*Math.abs(Math.cos(angel));
				}else{
					yPos-=xSpeed*Math.abs(Math.cos(angel));
				}
				enem.yPos+=ySpeed/5;
			}else{
				yPos+=ySpeed;
			}
		}
		if(xSpeed<0&&xPos+xSpeed<radius) xPos=radius;
		else if(xSpeed>0&&xPos+xSpeed>1200-radius) xPos=1200-radius;
		else {
			if(Main.distance(xPos+xSpeed,yPos+ySpeed,enem.xPos,enem.yPos)<=radius+enem.radius){
				angel =Math.atan2(yPos-enem.yPos,xPos-enem.xPos);
				xPos+=xSpeed*Math.abs(Math.sin(angel));
				if((yPos-enem.yPos)/(xPos-enem.xPos)<0){
					xPos+=ySpeed*Math.abs(Math.sin(angel));
				}else{
					xPos-=ySpeed*Math.abs(Math.sin(angel));
				}
                enem.xPos+=xSpeed/5;
			}else{
				xPos+=xSpeed;
			}
		}
		//slow player
		if(i>decrement) i-=decrement;
		else if(i<-decrement) i+=decrement;
		else i=0;
		if(j>Player.decrement) j-=decrement;
		else if(j<-decrement) j+=decrement;
		else j=0;
	}

	public Player enemy(){
		for(Player p : Main.players) if(p!=this) return p;
		return null;
	}
	public void setColor(Color c){
        color=c;
        if(leftT){
            Main.blDirek.teamColor=c;
            Main.tlDirek.teamColor=c;
        }else{
            Main.brDirek.teamColor=c;
            Main.trDirek.teamColor=c;}
    }
	void setGoalSize(int goalsize){
		this.goalsize=goalsize;
		for(Direk d:direkler)d.updateGoalSize();
		if(this==Main.player1){
			Main.leftBound1.height=400-goalsize/2-20;
			Main.leftBound2.height=400-goalsize/2-20;
			Main.leftBound2.y=400+goalsize/2;
			Main.ball.ltg=400-goalsize/2;
			Main.ball.lbg=400+goalsize/2;
		}else {
			Main.rightBound1.height=400-Main.player2.goalsize/2-20;
			Main.rightBound2.height=400-Main.player2.goalsize/2-20;
			Main.rightBound2.y=400+Main.player2.goalsize/2;
			Main.ball.rtg=400-goalsize/2;
			Main.ball.rbg=400+goalsize/2;
		}
	}
}
