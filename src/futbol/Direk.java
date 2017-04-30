package futbol;

import java.awt.*;
import java.util.ArrayList;

class Direk extends Circly{
	public static ArrayList<Direk> direkler=new ArrayList<>();
	static Color defColor=Color.white;
	Color teamColor;
	boolean left;
	boolean top;

	Direk(int xPos,int yPos){
		radius=6;
		size=11;
		color=defColor;
		if(xPos>600)teamColor=Main.players[1].color;
		else teamColor=Main.players[0].color;
		this.xPos=xPos;
		this.yPos=yPos;
		direkler.add(this);
	}
	Direk(boolean left,boolean top){
		this.left=left;
		this.top=top;
		radius=6;
		size=11;
		color=defColor;
		if(left){
			teamColor=Main.player1.color;
			Main.player1.direkler.add(this);
			xPos=55;
		}
		else {
			teamColor=Main.player2.color;
			Main.player2.direkler.add(this);
			xPos=1145;
		}
		if(top)yPos=400-(left?Main.player1.goalsize/2:Main.player2.goalsize/2);
		else yPos=400+(left?Main.player1.goalsize/2:Main.player2.goalsize/2);
		direkler.add(this);
	}
	void updateGoalSize(){
		if(top)yPos=400-(left?Main.player1.goalsize/2:Main.player2.goalsize/2);
		else yPos=400+(left?Main.player1.goalsize/2:Main.player2.goalsize/2);
	}
}
