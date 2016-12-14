package futbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Main extends JPanel implements KeyListener, ActionListener, ItemListener {
    private static JFrame frame;
    private final int PLAYER_COUNT = 2;


    private static Player[] players;
    private static Ball ball;

    static final int WIDTH = 1200;
    static final int HEIGHT = 800;
	
	static double target_fps = 60;
	static int fps = 60;
	static int frameCount = 60;
	float interpolation;

    static Set<Integer> pressed = new HashSet<>();
	public JMenuBar menuBar;
	JMenuItem m11;
	JMenuItem m21;
	boolean showStats=false;

    public static void main(String[] args) {
        Main m = new Main();
		
		//interepolation things
		final double GAME_HERTZ = 30.0;
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / target_fps;
		//Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        // TODO Game loop
        Thread t1 = new Thread(players[0]);
		Thread t2 = new Thread(players[1]);
		t1.start();
		t2.start();
        ball.ballMover.start();
        while(true) {
			double now = System.nanoTime();
			int updateCount = 0;
			while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
			{
				gameCodes();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES){
			   lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}
			float interpolation = Math.min(1.0f,(float)((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
			frame.repaint();
			lastRenderTime = now;
			//Update the frames we got.
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime){
			   System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
			   fps = Main.frameCount;
			   frameCount = 0;
			   lastSecondTime = thisSecond;
			}
			//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
			while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES){
			   Thread.yield();
			   //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
			   //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
			   try {Thread.sleep(1);} catch(Exception e) {}
			   now = System.nanoTime();
			}
        }
    }
	public static void gameCodes(){
		// hit(PlayerXPos, PlayerYPos, PlayerXSpeed, PlayerYSpeed)
		for(Player p:players){
			if(p.intersects(ball.getCenterX(), ball.getCenterY(), ball.SIZE, ball.SIZE)){
				ball.hit(p.getCenterX(), p.getCenterY(), p.xSpeed, p.ySpeed);
			}
		}
	}
	Main() {
        frame = new JFrame("Football");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(200, 20);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
		JPanel contPane=new JPanel(new BorderLayout());
		
		frame.setJMenuBar(menuBarimiz());
		frame.setContentPane(contPane);
		menuBar.setVisible(false);
		
        // Initialize players
        players = new Player[PLAYER_COUNT];
        players[0] = new Player("Batu", WIDTH / 3, HEIGHT / 2);
        players[1] = new Player("Aytaç", WIDTH * 2 / 3, HEIGHT / 2);
		
		
		// Set control keys
		players[0].left=65;players[0].right='D';players[0].up=87;players[0].down='S';
		players[1].left=KeyEvent.VK_LEFT;players[1].right=KeyEvent.VK_RIGHT;players[1].up=KeyEvent.VK_UP;players[1].down=KeyEvent.VK_DOWN;

        // Initialize ball
        ball = new Ball();

        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setLocation(0, 0);
        addKeyListener(this);
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        frame.add(this);
        frame.setVisible(true);
    }
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		if(showStats){
			g.drawString("FPS: " + fps+"\n", 5, 10);
			g.drawString("p1 speed: "+Math.sqrt(players[0].xSpeed*players[0].xSpeed+players[0].ySpeed*players[0].ySpeed), 5, 20);
			g.drawString("p1 x: "+players[0].xSpeed+" y: "+players[0].ySpeed, 5, 30);
			g.drawString("p2 speed: "+Math.sqrt(players[1].xSpeed*players[1].xSpeed+players[1].ySpeed*players[1].ySpeed), 5, 40);
			g.drawString("ball speed: "+Math.sqrt(ball.xSpeed*ball.xSpeed+ball.ySpeed*ball.ySpeed), 5, 50);
			
		}
        players[0].draw(g);
        players[1].draw(g);
        ball.draw(g);
		frameCount++;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
		int a=e.getKeyCode();
        pressed.add(a);
		if(a=='K')menuBar.setVisible(true);
		if(a=='L')menuBar.setVisible(false);
		
    }

    @Override
    public void keyReleased(KeyEvent e) {

        pressed.remove(e.getKeyCode());
    }

	public JMenuBar menuBarimiz(){
		menuBar=new JMenuBar();
		JMenu menu1=new JMenu("Game");
		JMenu menu2=new JMenu("Engine");
		m11=new JMenuItem("Reset");
		m21=new JMenuItem("Show Stats");
		m21.addActionListener(this);
		m11.addActionListener(this);
		menu1.add(m11);
		menu2.add(m21);
		menuBar.add(menu1);
		menuBar.add(menu2);
		return menuBar;
	}
	
	public void actionPerformed(ActionEvent e) {
        if((JMenuItem)(e.getSource())==m21){
			if(showStats)showStats=false;
			else showStats=true;
		}else if((JMenuItem)(e.getSource())==m11){
			int i=1;
			for(Player p:players){
				p.xSpeed=0;
				p.ySpeed=0;
				p.setCenterY(HEIGHT/2);
				p.setCenterX(i*WIDTH/3);
				i++;
			}
			i=1;
			ball.setCenterX(WIDTH/2);
			ball.setCenterY(HEIGHT/2);
		}
    }
	public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
    }
}
