package futbol;

import java.util.ArrayList;

class Direk extends Circly {
	public static ArrayList<Direk> direkler=new ArrayList<>();

	Direk(int xPos, int yPos) {
		SIZE=11;
		this.xPos=xPos;
		this.yPos=yPos;
		direkler.add(this);
	}
}
