package futbol;

class Ball extends Circly{
	double slow=0.080;
	double slowX=0;
	double slowY=0;
	double moveAngle;
	static int rtg;
	static int rbg;
	static int ltg;
	static int lbg;

	Ball(){
		xPos=Main.width/2;
		yPos=Main.height/2;
		radius=8;
		size=16;
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
	void move(){
		//Horizontal bounds
        if(yPos+radius+ySpeed>=780 && ySpeed>0){
			yPos+=780-(yPos+ySpeed);
            ySpeed=-ySpeed;
        } else if(yPos-radius+ySpeed<=30 && ySpeed<0){
			yPos+=30-(yPos+ySpeed);
            ySpeed=-ySpeed;
        }else yPos+=ySpeed;
		//Vertical bounds
        if(xPos+radius+xSpeed>=1145 && !(yPos>=rtg && yPos<=rbg) && xSpeed>0) {
			xPos+=1145-(xPos+xSpeed);
            xSpeed=-xSpeed;
        } else if(xPos+radius+xSpeed <= 70 && !(yPos>=ltg && yPos<=lbg) && xSpeed<0) {
			xPos+=70-(xPos+xSpeed);
	        xSpeed=-xSpeed;
        }else xPos+=xSpeed;
		
		//Ball slows over time
		moveAngle=Math.atan2(ySpeed, xSpeed);
		slowX=slow*Math.abs(Math.cos(moveAngle));
		slowY=slow*Math.abs(Math.sin(moveAngle));

		if(xSpeed>=slowX) xSpeed-=slowX;
		else if(xSpeed<=-slowX) xSpeed+=slowX;
		else xSpeed=0;
		if(ySpeed>=slowY) ySpeed-=slowY;
		else if(ySpeed<=-slowY) ySpeed+=slowY;
		else ySpeed=0;
	}
}