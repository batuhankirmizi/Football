package futbol;

import java.awt.*;
import java.util.ArrayList;

class Direk extends Circly{
	public static ArrayList<Direk> direkler=new ArrayList<>();
	static Color defColor=Color.white;
	Color teamColor;

	Direk(int xPos,int yPos){
		SIZE=11;
		color=defColor;
		if(xPos>600)teamColor=Main.players[1].color;
		else teamColor=Main.players[0].color;
		this.xPos=xPos;
		this.yPos=yPos;
		direkler.add(this);
	}
}
