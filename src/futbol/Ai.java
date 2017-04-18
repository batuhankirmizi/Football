package futbol;

public class Ai extends Thread{
	byte hertz=10;
	Player player;
	int xDes;
	int yDes;
	boolean moving=false;
	boolean movingY=false;
	boolean movingX=false;

	Ai(){
		player=getPlayer();
		System.out.println("AI created for player"+Main.bot);
	}
	public void run(){
		super.run();
		while(Main.run){
			//donothing();
			System.out.print("");
			if(moving){
				if(movingY){
					if((int)(player.yPos-surplusY())<yDes) pressDown();
					else if((int)(player.yPos-surplusY())>yDes) pressUp();
					else{
						releaseDown();
						releaseUp();
						movingY=false;
					}
				}if(movingX){
					if((int)(player.xPos-surplusX())<xDes) pressRight();
					else if((int)(player.xPos-surplusX())>xDes) pressLeft();
					else{
						releaseLeft();
						releaseRight();
						movingX=false;
					}
				}if(!movingY&&!movingX) moving=false;
				try{
					Thread.sleep(hertz);
				}catch(InterruptedException ignored){
				}
			}
		}
	}
	void goTo(int x,int y){
		xDes=x;
		yDes=y;
		movingX=true;
		movingY=true;
		moving=true;
		System.out.println("Going to "+x+","+y);
	}
	Player getPlayer(){
		return Main.players[Main.bot-1];
	}
	void pressUp(){
		releaseDown();
		Main.pressed.add(player.up);
	}
	void releaseUp(){
		Main.pressed.remove(player.up);
	}
	void pressDown(){
		releaseUp();
		Main.pressed.add(player.down);
	}
	void releaseDown(){
		Main.pressed.remove(player.down);
	}
	void pressLeft(){
		releaseRight();
		Main.pressed.add(player.left);
	}
	void releaseLeft(){
		Main.pressed.remove(player.left);
	}
	void pressRight(){
		releaseLeft();
		Main.pressed.add(player.right);
	}
	void releaseRight(){
		Main.pressed.remove(player.right);
	}
	double surplusY(){
		double distance=Math.PI*getYSpeed()*getYSpeed()*5/2;
		if(player.yPos<yDes) return -distance;
		else return distance;
	}
	double surplusX(){
		double distance=Math.PI*getXSpeed()*getXSpeed()*5/2;
		if(player.xPos<xDes) return -distance;
		else return distance;
	}
	double getYSpeed(){
		return player.ySpeed;
	}
	double getXSpeed(){
		return player.xSpeed;
	}
}
