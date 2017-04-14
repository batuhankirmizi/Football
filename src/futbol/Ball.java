package futbol;

class Ball extends Circly{
	static final int limit=35;
	double slow=0.080;
	double slowX=0;
	double slowY=0;

	Ball(){
		xPos=Main.width/2;
		yPos=Main.height/2;
		SIZE=15;
	}

	void hit(Circly p){
		double ydist=this.yPos-p.yPos;
		double xdist=this.xPos-p.xPos;
		this.xSpeed+=p.xSpeed*4/5;
		this.ySpeed+=p.ySpeed*4/5;
		double speed=Math.sqrt(p.ySpeed*p.ySpeed+p.xSpeed*p.xSpeed)+10;
		double angle=Math.atan2(ydist,xdist);
		this.xSpeed=speed*Math.cos(angle);
		this.ySpeed=speed*Math.sin(angle);
		this.xSpeed+=p.xSpeed*4/5;    //Bu iki satırın tekrar eklenmesi top ve oyuncu arasındaki açının yanı sıra
		this.ySpeed+=p.ySpeed*4/5;    //topun oyuncunun hareket yönüne doğru da hareket etmesini sağlar
	}
}