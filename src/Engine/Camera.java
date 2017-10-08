package Engine;

public class Camera{
	long xPos, yPos;
	float viewScale=1f;

	Camera(long xPos, long yPos){
		this.xPos=xPos;
		this.yPos=yPos;
	}

	public void moveTo(long x, long y){
		this.xPos=x;
		this.yPos=y;
		Engine.engine.updateScales();
	}

	public void move(long x, long y){
		xPos+=x;
		yPos+=y;
		Engine.engine.updateScales();
	}

	public void chanceScale(float delta){
		viewScale+=delta;
		Engine.engine.updateScales();
	}
}
