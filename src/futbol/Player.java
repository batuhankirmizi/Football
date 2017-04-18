package futbol;

class Player extends Circly{
	final byte SPEED=5;
	private String name;
	byte i=0;
	byte j=0;
	short up;
	short down;
	short left;
	short right;
	Player enem;
	double angel;

	Player(String name,int xPos,int yPos){
		super();
		SIZE=60;
		this.name=name;
		this.xPos=xPos;
		this.yPos=yPos;
	}

	void move(){
		if(Main.distance(xPos+xSpeed,yPos+ySpeed,enem.xPos,enem.yPos)<=SIZE/2+enem.SIZE/2){
			angel=Math.atan2(yPos-enem.yPos,xPos-enem.xPos);
			yPos=yPos+ySpeed*Math.abs(Math.cos(angel));
			xPos=xPos+xSpeed*Math.abs(Math.sin(angel));
			if((yPos-enem.yPos)/(xPos-enem.xPos)<0){
				yPos+=xSpeed*Math.abs(Math.cos(angel));
				xPos+=ySpeed*Math.abs(Math.sin(angel));
			}else{
				yPos-=xSpeed*Math.abs(Math.cos(angel));
				xPos-=ySpeed*Math.abs(Math.sin(angel));
			}
		}else{
			yPos=yPos+ySpeed;
			xPos=xPos+xSpeed;
		}
	}

	public Player enemy(){
		for(Player p : Main.players) if(p!=this) return p;
		return null;
	}
}
