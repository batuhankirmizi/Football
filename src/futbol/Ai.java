package futbol;

public class Ai extends Thread{
	byte period=5;
	Player player;
	int xDes;
	int yDes;
	boolean moving=false;
	boolean movingY=false;
	boolean movingX=false;
	byte team;
	boolean rand;

	Ai(byte team){
		this.team=team;
		player=getPlayer();
		System.out.println("AI created for player"+team);
		player.botted=true;
		player.bot=this;
	}
	public void run(){
		super.run();
		//donothing();
		//System.out.print("");
		if(moving){
			if(movingY){
				if((player.yPos+surplusY())<yDes) pressDown();
				else if((int)(player.yPos-surplusY())>yDes) pressUp();
				else{
					releaseDown();
					releaseUp();
					movingY=false;
				}
			}if(movingX){
				if((int)(player.xPos+surplusX())<xDes) pressRight();
				else if((int)(player.xPos-surplusX())>xDes) pressLeft();
				else{
					releaseLeft();
					releaseRight();
					movingX=false;
				}
			}if(!movingY&&!movingX) moving=false;
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
		return Main.players[team-1];
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
		/*
		double distance=Math.PI*getYSpeed()*getYSpeed()*5/4;
		if(player.yPos<yDes) return -distance;
		else return distance;
		*/
		double dis=0;
		if(Math.abs(yDes-player.yPos)>Math.abs(xDes-player.xPos)){
			int g=Math.abs(player.i);
			for(int f=Math.abs(player.j);f>0;f-=Player.decrement){
			    if(g>Player.decrement)g-=Player.decrement;
			    else g=0;
			    dis+=player.SPEED*Math.sin(f*Math.PI/250)*Math.cos(g*Math.PI/500);
			}
		}
		else for(int f=Math.abs(player.j);f>0;f-=Player.decrement)dis+=player.SPEED*Math.sin(f*Math.PI/250)*Math.cos(player.i*Math.PI/500);
		return dis;
	}
	double surplusX(){
		/*double distance=Math.PI*getXSpeed()*getXSpeed()*5/4;
		if(player.xPos<xDes) return -distance;
		else return distance;
		*/
		double dis=0;
		if(Math.abs(xDes-player.xPos)>Math.abs(yDes-player.yPos)){
			int g=Math.abs(player.j);
			for(int f=Math.abs(player.i);f>0;f-=Player.decrement){
			if(g>Player.decrement)g-=Player.decrement;
			else g=0;
			dis+=player.SPEED*Math.sin(f*Math.PI/250)*Math.cos(g*Math.PI/500);
			}
		}
		else for(int f=Math.abs(player.i);f>0;f-=Player.decrement)dis+=player.SPEED*Math.sin(f*Math.PI/250)*Math.cos(player.j*Math.PI/500);
		return dis;
	}
	double getYSpeed(){
		return player.ySpeed;
	}
	double getXSpeed(){
		return player.xSpeed;
	}
}
