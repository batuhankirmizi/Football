package futbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Main extends JPanel implements KeyListener, ActionListener, ItemListener{
	public static JMenuBar menuBar;
	static int width=1200;
	static int height=800;
	static double target_fps=60;
	static int fps=60;
	static int frameCount=60;
	static Set<Integer> pressed=new HashSet<>();
	static short tekcift=0;
	private static JFrame frame;
	private static Player[] players;
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
	private final int PLAYER_COUNT=2;
	JMenuItem m11;
	JMenuItem m21;
	JMenuItem m2r1;
	JMenuItem m2r2;
	boolean showStats=false;

	Main(){
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
		tlDirek=new Direk(55, 321);
		blDirek=new Direk(55, 478);
		trDirek=new Direk(1145, 321);
		brDirek=new Direk(1145, 478);
		leftBound2=new Shapes(50, 480, 10, 300);
		rightBound1=new Shapes(1140, 20, 10, 300);
		rightBound2=new Shapes(1140, 480, 10, 300);


		// Initialize players
		players=new Player[PLAYER_COUNT];
		players[0]=new Player("Batu", width/3, height/2);
		players[1]=new Player("Aytaç", width*2/3, height/2);


		// Set control keys
		players[0].left=65;
		players[0].right='D';
		players[0].up=87;
		players[0].down='S';
		players[1].left=KeyEvent.VK_LEFT;
		players[1].right=KeyEvent.VK_RIGHT;
		players[1].up=KeyEvent.VK_UP;
		players[1].down=KeyEvent.VK_DOWN;

		// Initialize ball
		ball=new Ball();

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
		final double GAME_HERTZ=125.0;
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER=5;
		double lastUpdateTime=System.nanoTime();
		double lastRenderTime=System.nanoTime();
		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
		//Simple way of finding FPS.
		int lastSecondTime=(int) (lastUpdateTime/1000000000);

		// TODO Game loop
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
			float interpolation=Math.min(1.0f, (float) ((now-lastUpdateTime)/TIME_BETWEEN_UPDATES));
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
				}catch(Exception e){
				}
				now=System.nanoTime();

			}
		}
	}

	public static void gameCodes(){
		// hit(PlayerXPos, PlayerYPos, PlayerXSpeed, PlayerYSpeed)
		for(Direk d : Direk.direkler){
			if(Math.abs((d.xPos-ball.xPos)*(d.xPos-ball.xPos)+(d.yPos-ball.yPos)*(d.yPos-ball.yPos))<=(d.SIZE/2+ball.SIZE)*(d.SIZE/2+ball.SIZE)){
				ball.hit(d.xPos, d.yPos, 0, 0);
			}
		}
		if(ball.xSpeed<0&&(ball.intersects(50, 20, 10, 300)||ball.intersects(50, 480, 10, 300)))
			ball.xSpeed=-ball.xSpeed;
		if(ball.xSpeed>0&&(ball.intersects(1140, 20, 10, 300)||ball.intersects(1140, 480, 10, 300)))
			ball.xSpeed=-ball.xSpeed;
		if(ball.ySpeed<0&&ball.intersects(50, 20, 1100, 10)) ball.ySpeed=-ball.ySpeed;
		if(ball.ySpeed>0&&ball.intersects(50, 770, 1100, 10)) ball.ySpeed=-ball.ySpeed;
		for(Player p : players){
			if(Math.abs((p.xPos-ball.xPos)*(p.xPos-ball.xPos)+(p.yPos-ball.yPos)*(p.yPos-ball.yPos))<=(p.SIZE/2+ball.SIZE)*(p.SIZE/2+ball.SIZE)){
				ball.hit(p.xPos, p.yPos, p.xSpeed, p.ySpeed);
			}
			p.xSpeed=p.SPEED*Math.sin(p.i*Math.PI/250)*Math.cos(p.j*Math.PI/500);
			p.ySpeed=p.SPEED*Math.sin(p.j*Math.PI/250)*Math.cos(p.i*Math.PI/500);

			//move player
			if(p.ySpeed<0&&p.yPos+p.ySpeed<p.SIZE/2) p.yPos=p.SIZE/2;
			else if(p.ySpeed>0&&p.yPos+p.ySpeed>800-p.SIZE/2) p.yPos=800-p.SIZE/2;
			else p.yPos=p.yPos+p.ySpeed;
			if(p.xSpeed<0&&p.xPos+p.xSpeed<p.SIZE/2) p.xPos=p.SIZE/2;
			else if(p.xSpeed>0&&p.xPos+p.xSpeed>1200-p.SIZE/2) p.xPos=1200-p.SIZE/2;
			else p.xPos=p.xPos+p.xSpeed;

			if(p.i>2) p.i-=2;            //slower
			else if(p.i<-2) p.i+=2;
			else p.i=0;
			if(p.j>2) p.j-=2;
			else if(p.j<-2) p.j+=2;
			else p.j=0;

			if(Main.pressed.contains(p.up)&&!Main.pressed.contains(p.down))    //keylisten
				if(p.j>-122) p.j-=3;
				else p.j=-125;
			else if(!Main.pressed.contains(p.up)&&Main.pressed.contains(p.down))
				if(p.j<122) p.j+=3;
				else p.j=125;

			if(Main.pressed.contains(p.left)&&!Main.pressed.contains(p.right))
				if(p.i>-122) p.i-=3;
				else p.i=-125;
			else if(!Main.pressed.contains(p.left)&&Main.pressed.contains(p.right))
				if(p.i<122) p.i+=3;
				else p.i=125;

			if(tekcift==0){
				//Ball bounce
				if((ball.xPos<=ball.SIZE)&&ball.xSpeed<0){                            //hit left
					ball.xSpeed=-ball.xSpeed+0.2;
					System.out.println("bounce");
				} else if(ball.xSpeed>0&&(ball.xPos>=1200-(Main.menuBar.isVisible() ? 20 : 0)-ball.SIZE*2)){    //hit right
					ball.xSpeed=-ball.xSpeed-0.2;
					System.out.println("bounce");
				}
				if((ball.yPos<=ball.SIZE)&&ball.ySpeed<0){                            //hit top
					ball.ySpeed=-ball.ySpeed+0.2;
					System.out.println("bounce");
				} else if(ball.ySpeed>0&&(ball.yPos>=800-ball.SIZE*2-20)){    //hit bot
					ball.ySpeed=-ball.ySpeed-0.2;
					System.out.println("bounce");
				}

				//Ball move
				ball.setCenterX(ball.getCenterX()+ball.xSpeed);
				ball.setCenterY(ball.getCenterY()+ball.ySpeed);

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
				tekcift=1;
			} else tekcift=0;
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
		frameCount++;
	}

	@Override
	public void keyTyped(KeyEvent e){

	}

	@Override
	public void keyPressed(KeyEvent e){
		int a=e.getKeyCode();
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

		pressed.remove(e.getKeyCode());
	}

	public JMenuBar menuBarimiz(){
		menuBar=new JMenuBar();
		JMenu menu1=new JMenu("Game");
		JMenu menu2=new JMenu("Engine");
		m11=new JMenuItem("Reset");
		m21=new JMenuItem("Show Stats");
		m2r1=new JMenuItem("1200*800");
		m2r2=new JMenuItem("800*600");
		m21.addActionListener(this);
		m11.addActionListener(this);
		m2r1.addActionListener(this);
		m2r2.addActionListener(this);
		menu1.add(m11);
		menu2.add(m21);
		menu2.addSeparator();
		menu2.add("Resolution:");
		menu2.add(m2r1);
		menu2.add(m2r2);
		menuBar.add(menu1);
		menuBar.add(menu2);
		return menuBar;
	}

	public void actionPerformed(ActionEvent e){
		if((JMenuItem) (e.getSource())==m21){
			if(showStats) showStats=false;
			else showStats=true;
		} else if((JMenuItem) (e.getSource())==m11){
			int i=1;
			for(Player p : players){
				p.xSpeed=0;
				p.ySpeed=0;
				p.xPos=i*width/3;
				p.yPos=height/2;
				i++;
			}
			i=1;
			ball.setCenterX(width/2);
			ball.setCenterY(height/2);
		} else if((JMenuItem) (e.getSource())==m2r1){
			width=1200;
			height=800;
			frame.setSize(width+5, height+50);
		} else if((JMenuItem) (e.getSource())==m2r2){
			width=800;
			height=600;
			frame.setSize(width+5, height+50);
		}
	}

	public void itemStateChanged(ItemEvent e){
		JMenuItem source=(JMenuItem) (e.getSource());
	}
}
