package futbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Formatter;
import java.util.Iterator;
import java.io.*;

public class Main extends JPanel implements KeyListener, ActionListener, ItemListener {
	private static JMenuBar menuBar;
	static short GAME_HERTZ=125;
	static short width=1200;
	static short height=800;
	private static byte target_fps=60;
	private static short fps=60;
	private static short frameCount=60;
	private static Set<Short> pressed=new TreeSet<>();
	private static JFrame frame;
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
	private final byte PLAYER_COUNT=2;
	private JMenuItem m11;
	private JMenuItem m21;
	private JMenuItem m2r1;
	private JMenuItem m2r2;
	private JMenuItem m2s1;
	private JMenuItem m2s2;
	private boolean showStats=false;
    static boolean goal = true;
	private static byte t1Score = 0;
	private static byte t2Score = 0;
	static File sett=new File("settings.txt");
	static Scanner settings;
	static Formatter f;
	static String[] variables={"name1","name2","anan"};
	static String name1="Batu";
	static String name2="Aytac";

	Main(){
		try{
			settings=new Scanner(sett);
		}catch(FileNotFoundException e){
			try{
			f=new Formatter("settings.txt");
			settings=new Scanner(sett);
			}catch(FileNotFoundException f){
			}
		}
		//while(settings.hasNext()){
		while(settings.hasNext()){
			Scanner srr=new Scanner(settings.nextLine()).useDelimiter("=");
			String sasa=srr.next();
			String sasa2=srr.next();
			System.out.println(sasa+" to "+sasa2);
			try{
				this.getClass().getDeclaredField(sasa).set(this,sasa2);
			} catch (Throwable e) {System.out.println("haha"); }
		}
		frame=new JFrame("Football");
		frame.setSize(width+5, height+30);
		frame.setLocation(200, 20);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JPanel contPane=new JPanel(new BorderLayout());

		frame.setJMenuBar(menuBarimiz());
		frame.setContentPane(contPane);
		menuBar.setVisible(false);

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


		// Initialize players
		players=new Player[PLAYER_COUNT];
		players[0]=new Player(name1, width/3, height/2);
		players[0].color=Color.RED;
		players[1]=new Player(name2, width*2/3, height/2);
		players[1].color=Color.MAGENTA;
		
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

		setLayout(null);
		setSize(width, height);
		setLocation(0, 0);
		addKeyListener(this);
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocusInWindow();

		frame.add(this);
		frame.setVisible(true);
	}

	public static void main(String[] args){
		Main m=new Main();

		//interepolation things
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER=5;
		double lastUpdateTime=System.nanoTime();
		double lastRenderTime=System.nanoTime();
		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
		//Simple way of finding FPS.
		int lastSecondTime=(int) (lastUpdateTime/1000000000);

		// Game loop
		while(true){
			double now=System.nanoTime();
			int updateCount=0;
			while(now-lastUpdateTime>TIME_BETWEEN_UPDATES&&updateCount<MAX_UPDATES_BEFORE_RENDER){
				gameCodes();
				lastUpdateTime+=TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if(now-lastUpdateTime>TIME_BETWEEN_UPDATES){
				lastUpdateTime=now-TIME_BETWEEN_UPDATES;
			}
			frame.repaint();
			lastRenderTime=now;
			//Update the frames we got.
			int thisSecond=(int) (lastUpdateTime/1000000000);
			if(thisSecond>lastSecondTime){
				System.out.println("NEW SECOND "+thisSecond+" "+frameCount);
				fps=Main.frameCount;
				frameCount=0;
				lastSecondTime=thisSecond;
			}
			//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
			while(now-lastRenderTime<TARGET_TIME_BETWEEN_RENDERS&&now-lastUpdateTime<TIME_BETWEEN_UPDATES){
				Thread.yield();
				//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
				//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
				try{
					Thread.sleep(1);
				}catch(Exception e){ }
				//
				now=System.nanoTime();

			}
		}
	}


	private static void gameCodes(){
		// hit(PlayerXPos, PlayerYPos, PlayerXSpeed, PlayerYSpeed)
		for(Direk d : Direk.direkler){
			if(Math.abs((d.xPos-ball.xPos)*(d.xPos-ball.xPos)+(d.yPos-ball.yPos)*(d.yPos-ball.yPos))<=(d.SIZE/2+ball.SIZE)*(d.SIZE/2+ball.SIZE)){
				ball.hit(d.xPos, d.yPos, 0, 0);
			}
		}
		//Horizontal bounds
        if(ball.yPos + (ball.SIZE / 2) + ball.ySpeed >= 760 && ball.ySpeed > 0) {
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
			if(Math.abs((p.xPos-ball.xPos)*(p.xPos-ball.xPos)+(p.yPos-ball.yPos)*(p.yPos-ball.yPos))<=(p.SIZE/2+ball.SIZE/2)*(p.SIZE/2+ball.SIZE/2)){
				ball.hit(p.xPos, p.yPos, p.xSpeed, p.ySpeed);
			}
			p.xSpeed=p.SPEED*Math.sin(p.i*Math.PI/250)*Math.cos(p.j*Math.PI/500);
			p.ySpeed=p.SPEED*Math.sin(p.j*Math.PI/250)*Math.cos(p.i*Math.PI/500);

			//slow player
			if(p.i>2) p.i-=3;
			else if(p.i<-2) p.i+=3;
			else p.i=0;
			if(p.j>2) p.j-=3;
			else if(p.j<-2) p.j+=3;
			else p.j=0;

			//keylisten
			if(Main.pressed.contains(p.up)&&!Main.pressed.contains(p.down))
				if(p.j>-121) p.j-=4;
				else p.j=-125;
			else if(!Main.pressed.contains(p.up)&&Main.pressed.contains(p.down))
				if(p.j<121) p.j+=4;
				else p.j=125;

			if(Main.pressed.contains(p.left)&&!Main.pressed.contains(p.right))
				if(p.i>-121) p.i-=4;
				else p.i=-125;
			else if(!Main.pressed.contains(p.left)&&Main.pressed.contains(p.right))
				if(p.i<121) p.i+=4;
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
                if(ball.yPos >= 321 && ball.yPos <= 478 && goal) {
                    if(ball.xPos >= 1150 && goal) {
                        t1Score++;
	                    score();
                    } else if(ball.xPos <= 50 && goal) {
                        t2Score++;
	                    score();
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
		
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(showStats){
			g.drawString("FPS: "+fps+"\n", 5, 10);
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

		frameCount++;
	}

	@Override
	public void keyTyped(KeyEvent e){

	}

	@Override
	public void keyPressed(KeyEvent e){
		short a=(short)e.getKeyCode();
		pressed.add(a);
		if(a=='K'){
			if(!menuBar.isVisible()){
				menuBar.setVisible(true);
				frame.setSize(width+5, height+50);
			}
		}
		if(a=='L'){
			if(menuBar.isVisible()){
				menuBar.setVisible(false);
				frame.setSize(width+5, height+30);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e){

		pressed.remove((short)e.getKeyCode());
	}

	public JMenuBar menuBarimiz(){
		menuBar=new JMenuBar();
		JMenu menu1=new JMenu("Game");
		JMenu menu2=new JMenu("Engine");
		m11=new JMenuItem("Reset");
		m21=new JMenuItem("Show Stats");
		m2r1=new JMenuItem("1200*800");
		m2r2=new JMenuItem("800*600");
		m2s1=new JMenuItem("%100");
		m2s2=new JMenuItem("%50");
		m21.addActionListener(this);
		m11.addActionListener(this);
		m2r1.addActionListener(this);
		m2r2.addActionListener(this);
		m2s1.addActionListener(this);
		m2s2.addActionListener(this);
		menu1.add(m11);
		menu2.add(m21);
		menu2.addSeparator();
		menu2.add("Resolution:");
		menu2.add(m2r1);
		menu2.add(m2r2);
		menu2.addSeparator();
		menu2.add("Game speed:");
		menu2.add(m2s1);
		menu2.add(m2s2);
		menuBar.add(menu1);
		menuBar.add(menu2);
		return menuBar;
	}

	private static void reset() {
        int i=1;
        for(Player p : players){
            p.xSpeed=0;
            p.ySpeed=0;
            p.xPos=i*400;
            p.yPos=400;
            i++;
        }
        ball.xPos=600;
        ball.yPos=400;
        ball.xSpeed = 0;
        ball.ySpeed = 0;
    }

	public void actionPerformed(ActionEvent e){
		if((e.getSource())==m21){
			if(showStats) showStats=false;
			else showStats=true;
		} else if( (e.getSource())==m11){
			reset();
		} else if((e.getSource())==m2r1){
			width=1200;
			height=800;
			frame.setSize(width+5, height+50);
		} else if( (e.getSource())==m2r2){
			width=800;
			height=600;
			frame.setSize(width+5, height+50);
		} else if(e.getSource()==m2s1){
			GAME_HERTZ=125;
			System.out.println(GAME_HERTZ);
		}
		else if(e.getSource()==m2s2){
			GAME_HERTZ=60;
			System.out.println(GAME_HERTZ);
		}
	}

	public void itemStateChanged(ItemEvent e){
		JMenuItem source=(JMenuItem) (e.getSource());
	}

	public static void score(){
		goal = false;
		try {
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		reset();
		goal = true;
	}
	public static double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.abs((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)));
	}
}
