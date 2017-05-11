package futbol;

import java.awt.*;

class Mutator extends Ball{
	int type;
	byte count=0;
	
	Mutator(int type){
		switch(type){
			default:{
				size=16;
				radius=8;
				color=Color.red;
				this.type=type;
				xPos=Main.rng.nextInt(600)+300;
				yPos=Main.rng.nextInt(600)+100;
			}
		}
	}
	
	void move(){
		super.move();
		if(count++==90){if(color==Color.red)
			color=Color.blue;
		else color=Color.red;
			count=0;
		}
	}
	
}