package futbol;

import java.awt.*;
import java.util.ArrayList;

class Player extends Circly{
	static final double decrement=2/0.008;
	private String name;
	static final double increment=3/0.008;
	double SPEED=6/0.008;
	int up;
	int down;
	int left;
	int right;
	Player enem;
	double i=0;
	double j=0;
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
		//keylisten
		if(Main.pressed.contains(up)&&!Main.pressed.contains(down))
			if(j>-(125-Player.increment*Main.delta)) j-=Player.increment*Main.delta;
			else j=-125;
		else if(Main.pressed.contains(down)&&!Main.pressed.contains(up))
			if(j<(125-Player.increment*Main.delta)) j+=Player.increment*Main.delta;
			else j=125;

		if(Main.pressed.contains(left)&&!Main.pressed.contains(right))
			if(i>-(125-Player.increment*Main.delta)) i-=Player.increment*Main.delta;
			else i=-125;
		else if(Main.pressed.contains(right)&&!Main.pressed.contains(left))
			if(i<(125-Player.increment*Main.delta)) i+=Player.increment*Main.delta;
			else i=125;


		//set player speed
		xSpeed=SPEED*Math.sin(i*Math.PI/250)*Math.cos(j*Math.PI/500)*Main.delta;
		ySpeed=SPEED*Math.sin(j*Math.PI/250)*Math.cos(i*Math.PI/500)*Main.delta;

        if(ySpeed<0&&yPos+ySpeed<radius) yPos=radius;
		else if(ySpeed>0&&yPos+ySpeed>800-radius) yPos=800-radius;
		else {
			if(Main.distance(xPos+xSpeed,yPos+ySpeed,enem.xPos,enem.yPos)<=radius+enem.radius){
				angel =Math.atan2(yPos-enem.yPos,xPos-enem.xPos);
				if((yPos+ySpeed*Math.abs(Math.cos(angel))<800-radius)&&(yPos+ySpeed*Math.abs(Math.cos(angel))>radius))yPos+=ySpeed*Math.abs(Math.cos(angel));
				else {
					i=0;
					if(xPos>enem.xPos)enem.j-=4;
					else enem.j+=4;
					return;
				}
				if((yPos-enem.yPos)/(xPos-enem.xPos)<0){
					yPos+=xSpeed*Math.abs(Math.cos(angel));
					if(yPos<enem.yPos)enem.i-=2;
					else enem.i+=2;
				}else{
					yPos-=xSpeed*Math.abs(Math.cos(angel));
					if(yPos<enem.yPos)enem.i+=2;
					else enem.i-=2;
				}
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
					if(xPos<enem.xPos)enem.j-=2;
					else enem.j+=2;
				}else{
					xPos-=ySpeed*Math.abs(Math.sin(angel));
					if(xPos<enem.xPos)enem.j+=2;
					else enem.j-=2;
				}
			}else{
				if(Main.distance(this,Main.ball)>radius+Main.ball.radius)xPos+=xSpeed;
			}
		}
		//slow player
		if(i>decrement*Main.delta) i-=decrement*Main.delta;
		else if(i<-decrement*Main.delta) i+=decrement*Main.delta;
		else i=0;
		if(j>Player.decrement*Main.delta) j-=decrement*Main.delta;
		else if(j<-decrement*Main.delta) j+=decrement*Main.delta;
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
