package futbol;

import futbol.Engine.TimedEvent;

import java.awt.*;

class Mutator extends Ball{
	MutatorT type;
	byte count=0;
	final Color secColor;
	final Color firColor;
	
	Mutator(MutatorT type){
		color=Color.red;
		switch(type){
			case EXTEND_GOAL:{
				color=Color.red;
				firColor=Color.red;
				secColor=Color.blue;
				break;
			}case SHRINK_GOAL:{
				color=Color.blue;
				firColor=Color.blue;
				secColor=Color.blue;
				break;
			}default:{
				color=Color.blue;
				firColor=Color.blue;
				secColor=Color.blue;
			}
		}
		size=16;
		radius=8;
		this.type=type;
		xPos=Main.rng.nextInt(600)+300;
		yPos=Main.rng.nextInt(600)+100;
	}

	
	void move(){
		super.move();
		/*if(count++==90){if(color==firColor)
			color=secColor;
		else color=firColor;
			count=0;
		}*/
	}
	void activate(boolean left){
		Player player;
		if(left){
			player=Main.player1;
		}else{
			player=Main.player2;
		}
		if(type==MutatorT.EXTEND_GOAL){
				player.setGoalSize(player.goalsize+60);
				new TimedEvent(24000){
					public void run(){
						super.run();
						if(Main.mutators.contains(this))player.setGoalSize(player.goalsize-60);}
				}.start();
			}else if(type==MutatorT.SHRINK_GOAL){
				player.setGoalSize(player.goalsize-40);
				new TimedEvent(20000){
					public void run(){
						super.run();
						if(Main.mutators.contains(this))player.setGoalSize(player.goalsize+40);}
				}.start();
			}
		Main.mutatorsToRemove.add(this);
	}
	static Mutator createMutator(int i){
		switch(i){
			case 0:{
				return new Mutator(MutatorT.EXTEND_GOAL);
			}case 1:{
				return new Mutator(MutatorT.SHRINK_GOAL);
			}default:{
				return null;
			}
		}
	}
}

enum MutatorT{
	EXTEND_GOAL,
	SHRINK_GOAL,
}