package futbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Engine{
	public static Player[] players;
	private static Ball ball;
	private static Shapes topBound;
	private static Shapes botBound;
	private static Shapes leftBound1;
	private static Direk tlDirek;
	private static Direk blDirek;
	private static Shapes leftBound2;
	private static Shapes rightBound1;
	private static Direk trDirek;
	private static Direk brDirek;
	private static Shapes rightBound2;
	private static final byte PLAYER_COUNT=2;
    static boolean goal = true;
	private static byte t1Score = 0;
	private static byte t2Score = 0;
	static String name1="Batu";
	static String name2="Aytac";
	JMenuItem m12;
	JMenuItem m13;
	JMenuItem m14;
	JMenuItem m15;

	public static void main(String[] args){
		System.out.println("Main main() started");
		engine=new Main();
		engine.run();
	}
	Main(){
		super();
		variables.addAll(new ArrayList<String>(Arrays.asList("name1","name2")));
		System.out.println("sub created");

		// Initialize players
		players=new Player[PLAYER_COUNT];
		players[0]=new Player(name1, width/3, height/2);
		players[0].color=Color.RED;
		players[1]=new Player(name2, width*2/3, height/2);
		players[1].color=Color.MAGENTA;

		// Draw bounds
		topBound=new Shapes(50, 20, 1100, 10);
		botBound=new Shapes(50, 770, 1100, 10);
		leftBound1=new Shapes(50, 20, 10, 300);
		tlDirek=new Direk(54, 321);
		blDirek=new Direk(54, 478);
		trDirek=new Direk(1144, 321);
		brDirek=new Direk(1144, 478);
		leftBound2=new Shapes(50, 480, 10, 300);
		rightBound1=new Shapes(1140, 20, 10, 300);
		rightBound2=new Shapes(1140, 480, 10, 300);

		for(Player p:players) p.enem=p.enemy();

		// Set control keys
		players[0].left='A';
		players[0].right='D';
		players[0].up='W';
		players[0].down='S';
		players[1].left=KeyEvent.VK_LEFT;
		players[1].right=KeyEvent.VK_RIGHT;
		players[1].up=KeyEvent.VK_UP;
		players[1].down=KeyEvent.VK_DOWN;

		// Initialize ball
		ball=new Ball();
		ball.color=Color.cyan;

		System.out.println("objects initialized");
	}

	public  void initialize(){
		super.initialize();
	}
	public  void gameCodes(){
		for(Direk d : Direk.direkler){
			if(distance(d,ball)<=(d.SIZE/2+ball.SIZE)){
				d.color=d.teamColor;
				new Timer(200){public void run(){super.run();if(goal)d.color=Direk.defColor;}}.start();
				ball.hit(d);
			}
		}
		//Horizontal bounds
        if(ball.yPos + (ball.SIZE / 2) + ball.ySpeed >= 770 && ball.ySpeed > 0) {
            ball.ySpeed = -ball.ySpeed;
        } else if(ball.yPos - (ball.SIZE / 2) + ball.ySpeed <= 25 && ball.ySpeed < 0) {
            ball.ySpeed = -ball.ySpeed;
        }
		//Vertical bounds
        if(!(ball.yPos >= 321 && ball.yPos <= 478) && ball.xSpeed > 0 && ball.xPos + (ball.SIZE / 2) + ball.xSpeed >= 1135) {
            ball.xSpeed = -ball.xSpeed;
        } else if(!(ball.yPos >= 321 && ball.yPos <= 478) && ball.xSpeed < 0 && ball.xPos + (ball.SIZE / 2) + ball.xSpeed <= 70) {
	        ball.xSpeed = -ball.xSpeed;
        }

		for(Player p : players){
			if(distance(p,ball)<=(p.SIZE/2+ball.SIZE/2)){
				ball.hit(p);
			}
			//set player speed
			p.xSpeed=p.SPEED*Math.sin(p.i*Math.PI/250)*Math.cos(p.j*Math.PI/500);
			p.ySpeed=p.SPEED*Math.sin(p.j*Math.PI/250)*Math.cos(p.i*Math.PI/500);

			//slow player
			if(p.i>2) p.i-=2;
			else if(p.i<-2) p.i+=2;
			else p.i=0;
			if(p.j>2) p.j-=2;
			else if(p.j<-2) p.j+=2;
			else p.j=0;

			//keylisten
			if(pressed.contains(p.up)&&!pressed.contains(p.down))
				if(p.j>-122) p.j-=3;
				else p.j=-125;
			else if(!pressed.contains(p.up)&&pressed.contains(p.down))
				if(p.j<122) p.j+=3;
				else p.j=125;

			if(pressed.contains(p.left)&&!pressed.contains(p.right))
				if(p.i>-122) p.i-=3;
				else p.i=-125;
			else if(!pressed.contains(p.left)&&pressed.contains(p.right))
				if(p.i<122) p.i+=3;
				else p.i=125;
				
			//move player
			if(p.ySpeed<0&&p.yPos+p.ySpeed<p.SIZE/2) p.yPos=p.SIZE/2;
			else if(p.ySpeed>0&&p.yPos+p.ySpeed>800-p.SIZE/2) p.yPos=800-p.SIZE/2;
			else p.move();
			if(p.xSpeed<0&&p.xPos+p.xSpeed<p.SIZE/2) p.xPos=p.SIZE/2;
			else if(p.xSpeed>0&&p.xPos+p.xSpeed>1200-p.SIZE/2) p.xPos=1200-p.SIZE/2;
			else p.move();
			}


			if(goal){
				// Goal condition
				if(ball.yPos>=321&&ball.yPos<=478&&goal){
					if(ball.xPos>=1150&&goal){
						score(true);
					}else if(ball.xPos<=50&&goal){
						score(false);
					}
				}
			}

				//Ball move
				ball.xPos=ball.xPos + (ball.xSpeed > 0 ? Math.min(Ball.limit, ball.xSpeed) :
                                                                      Math.max(-Ball.limit, ball.xSpeed));
				ball.yPos=ball.yPos + (ball.ySpeed > 0 ? Math.min(Ball.limit, ball.ySpeed) :
                        Math.max(-Ball.limit, ball.ySpeed));

				//Ball slows over time
				double moveAngle;
				moveAngle=Math.atan2(ball.ySpeed, ball.xSpeed);
				ball.slowX=ball.slow*Math.abs(Math.cos(moveAngle));
				ball.slowY=ball.slow*Math.abs(Math.sin(moveAngle));

				if(ball.xSpeed>=ball.slowX) ball.xSpeed-=ball.slowX;
				else if(ball.xSpeed<=-ball.slowX) ball.xSpeed+=ball.slowX;
				else ball.xSpeed=0;
				if(ball.ySpeed>=ball.slowY) ball.ySpeed-=ball.slowY;
				else if(ball.ySpeed<=-ball.slowY) ball.ySpeed+=ball.slowY;
				else ball.ySpeed=0;
	}
	public void menuBar(){
		m12=new JMenuItem("Player 1 name");
		m12.addActionListener(this);
		menu1.add(m12);
		m13=new JMenuItem("Player 2 name");
		m13.addActionListener(this);
		menu1.add(m13);
		m14=new JMenuItem("Player 1 color");
		m14.addActionListener(this);
		menu1.add(m14);
		m15=new JMenuItem("Player 2 color");
		m15.addActionListener(this);
		menu1.add(m15);
	}
	protected void actions(ActionEvent e){
		if((e.getSource())==m12){
			String s = (String)JOptionPane.showInputDialog(
					frame,
					"Enter name",
					"Player 1",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					name1);
			if(s.length()>0&&s.length()<8){
				name1=s;
				players[0].name=s;
			}
		}else if((e.getSource())==m13){
			String s = (String)JOptionPane.showInputDialog(
					frame,
					"Enter name",
					"Player 2",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					name2);
			if(s.length()>0&&s.length()<8){
				name2=s;
				players[1].name=s;
			}
		}else if((e.getSource())==m14){
			String s = (String)JOptionPane.showInputDialog(
					frame,
					"Select color",
					"Player 1",
					JOptionPane.PLAIN_MESSAGE,
					null,
					new String[]{"red","blue"},
					name1);
			if(s.equals("red")){
				players[0].color=Color.red;
				blDirek.teamColor=Color.red;
				tlDirek.teamColor=Color.red;
			}else if(s.equals("blue")){
				players[0].color=Color.blue;
				blDirek.teamColor=Color.blue;
				tlDirek.teamColor=Color.blue;
			}
		}else if((e.getSource())==m15){
			String s = (String)JOptionPane.showInputDialog(
					frame,
					"Select color",
					"Player 2",
					JOptionPane.PLAIN_MESSAGE,
					null,
					new String[]{"purple","green"},
					name1);
			if(s.equals("purple")){
				players[1].color=Color.magenta;
				brDirek.teamColor=Color.magenta;
				trDirek.teamColor=Color.magenta;
			}else if(s.equals("green")){
				players[1].color=Color.green;
				brDirek.teamColor=Color.green;
				trDirek.teamColor=Color.green;
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(showStats){
			g.drawString("p1 speed: "+Math.sqrt(players[0].xSpeed*players[0].xSpeed+players[0].ySpeed*players[0].ySpeed), 5, 20);
			g.drawString("p1 x: "+players[0].xSpeed+" y: "+players[0].ySpeed, 5, 30);
			g.drawString("p2 speed: "+Math.sqrt(players[1].xSpeed*players[1].xSpeed+players[1].ySpeed*players[1].ySpeed), 5, 40);
			g.drawString("ball speed: "+Math.sqrt(ball.xSpeed*ball.xSpeed+ball.ySpeed*ball.ySpeed), 5, 50);
		}
		topBound.draw(g);
		botBound.draw(g);
		leftBound1.draw(g);
		rightBound1.draw(g);
		leftBound2.draw(g);
		rightBound2.draw(g);
		tlDirek.draw(g);
		blDirek.draw(g);
		trDirek.draw(g);
		brDirek.draw(g);
		players[0].draw(g);
		players[1].draw(g);
		ball.draw(g);

		// Draw scores
        g.drawString(players[0].name+" "+String.valueOf(t1Score), getWidth() / 3, 12);
        g.drawString(players[1].name+" "+String.valueOf(t2Score), getWidth() * 2 / 3, 12);
	}
	public  void reset() {
        int i=1;
        for(Player p : players){
            p.xSpeed=0;
            p.i=0;
            p.ySpeed=0;
            p.j=0;
            p.xPos=i*400;
            p.yPos=400;
            i++;
        }
        ball.xPos=600;
        ball.yPos=400;
        ball.xSpeed = 0;
        ball.ySpeed = 0;
		tlDirek.color=Color.white;
		blDirek.color=Color.white;
		trDirek.color=Color.white;
		brDirek.color=Color.white;
        goal=true;
    }
	public  void score(boolean team1){
		goal = false;
		if(team1){
			trDirek.color=players[1].color;
			brDirek.color=players[1].color;
			t1Score++;
		}else{
			tlDirek.color=players[0].color;
			blDirek.color=players[0].color;
			t2Score++;
		}
		new Timer(1500){public void run(){super.run();reset();}}.start();
	}
	public static double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.abs((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)));
	}
	public static double distance(Circly p1, Circly p2){
		return Math.sqrt(Math.abs((p1.xPos-p2.xPos)*(p1.xPos-p2.xPos)+(p1.yPos-p2.yPos)*(p1.yPos-p2.yPos)));
	}
}