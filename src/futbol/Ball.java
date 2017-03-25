package futbol;

class Ball extends Circly{
	static final int limit=25;
	double slow=0.1;
	double slowX=0;
	double slowY=0;

	Ball(){
		xPos=Main.width/2;
		yPos=Main.height/2;
		SIZE=15;
	}

	void hit(double xPos,double yPos,double xSpeed,double ySpeed){
		double ydist=this.yPos-yPos;
		double xdist=this.xPos-xPos;
		this.xSpeed+=xSpeed/2;
		this.ySpeed+=ySpeed/2;
		double speed=Math.sqrt(ySpeed*ySpeed+xSpeed*xSpeed)+10;
		double angle=Math.atan2(ydist,xdist);
		this.xSpeed=speed*Math.cos(angle);
		this.ySpeed=speed*Math.sin(angle);
		this.xSpeed+=xSpeed/2;    //Bu iki satırın tekrar eklenmesi top ve oyuncu arasındaki açının yanı sıra
		this.ySpeed+=ySpeed/2;    //topun oyuncunun hareket yönüne doğru da hareket etmesini sağlar
	}
	void hit(Player p){
		System.out.println("hit");
		double ydist=this.yPos-p.yPos;
		double xdist=this.xPos-p.xPos;
		this.xSpeed+=p.xSpeed/2;
		this.ySpeed+=p.ySpeed/2;
		double speed=Math.sqrt(p.ySpeed*p.ySpeed+p.xSpeed*p.xSpeed)+10;
		double angle=Math.atan2(ydist,xdist);
		this.xSpeed=speed*Math.cos(angle);
		this.ySpeed=speed*Math.sin(angle);
		this.xSpeed+=p.xSpeed/2;    //Bu iki satırın tekrar eklenmesi top ve oyuncu arasındaki açının yanı sıra
		this.ySpeed+=p.ySpeed/2;    //topun oyuncunun hareket yönüne doğru da hareket etmesini sağlar
	}
}