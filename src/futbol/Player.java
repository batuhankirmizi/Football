package futbol;

class Player extends Circly{
	final byte SPEED=4;
	private String name;
	byte i=0;
	byte j=0;
	short up;
	short down;
	short left;
	short right;
	Player enem;
	double angel;
	static final byte decrement=2;
	static final byte increment=3;
	boolean botted;
	Ai bot;

	Player(String name,int xPos,int yPos){
		super();
		SIZE=60;
		this.name=name;
		this.xPos=xPos;
		this.yPos=yPos;
	}

	void move(){
		//set player speed
			xSpeed=SPEED*Math.sin(i*Math.PI/250)*Math.cos(j*Math.PI/500);
			ySpeed=SPEED*Math.sin(j*Math.PI/250)*Math.cos(i*Math.PI/500);

		if(ySpeed<0&&yPos+ySpeed<SIZE/2) yPos=SIZE/2;
		else if(ySpeed>0&&yPos+ySpeed>800-SIZE/2) yPos=800-SIZE/2;
		else move2();
		if(xSpeed<0&&xPos+xSpeed<SIZE/2) xPos=SIZE/2;
		else if(xSpeed>0&&xPos+xSpeed>1200-SIZE/2) xPos=1200-SIZE/2;
		else move2();
		//slow player
		if(i>decrement) i-=decrement;
		else if(i<-decrement) i+=decrement;
		else i=0;
		if(j>Player.decrement) j-=decrement;
		else if(j<-decrement) j+=decrement;
		else j=0;
	}
	void move2(){
		if(Main.distance(xPos+xSpeed,yPos+ySpeed,enem.xPos,enem.yPos)<=SIZE/2+enem.SIZE/2){
			angel=Math.atan2(yPos-enem.yPos,xPos-enem.xPos);
			yPos+=ySpeed*Math.abs(Math.cos(angel));
			xPos+=xSpeed*Math.abs(Math.sin(angel));
			if((yPos-enem.yPos)/(xPos-enem.xPos)<0){
				yPos+=xSpeed*Math.abs(Math.cos(angel));
				xPos+=ySpeed*Math.abs(Math.sin(angel));
			}else{
				yPos-=xSpeed*Math.abs(Math.cos(angel));
				xPos-=ySpeed*Math.abs(Math.sin(angel));
			}
		}else{
			yPos+=ySpeed;
			xPos+=xSpeed;
		}
	}

	public Player enemy(){
		for(Player p : Main.players) if(p!=this) return p;
		return null;
	}
}
