package futbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public abstract class Engine extends JPanel implements KeyListener, ActionListener, ItemListener{
	public static Engine engine;
	public short fps=60;
	public short frameCount=60;
	public Set<Short> pressed=new TreeSet<>();
	public  JFrame frame;
	short GAME_HERTZ=125;
	static short width=1200;
	static short height=800;
	File sett=new File("settings.txt");
	Scanner settings;
	Formatter f;
	public JMenuBar menuBar;
	private byte target_fps=60;
	public JMenuItem m11;
	public JMenuItem m21;
	public JMenuItem m2r1;
	public JMenuItem m2r2;
	public JMenuItem m2s1;
	public JMenuItem m2s2;
	public boolean showStats=false;

	Engine(){
		System.out.println("sup created");
		frame=new JFrame("Football");
		frame.setSize(width+5, height+30);
		frame.setLocation(200, 20);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JPanel contPane=new JPanel(new BorderLayout());

		frame.setJMenuBar(menuBarimiz());
		frame.setContentPane(contPane);
		menuBar.setVisible(false);

		initialize();

		setLayout(null);
		setSize(width, height);
		setLocation(0, 0);
		addKeyListener(this);
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocusInWindow();

		frame.add(this);
		frame.setVisible(true);

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
			}catch(Throwable e){
				System.out.println("haha");
			}
		}

		setLayout(null);
		setSize(width,height);
		setLocation(0,0);
		addKeyListener(this);
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocusInWindow();

		frame.add(this);
		frame.setVisible(true);
	}

	public void run(){

		//interepolation things
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER=5;
		double lastUpdateTime=System.nanoTime();
		double lastRenderTime=System.nanoTime();
		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
		//Simple way of finding FPS.
		int lastSecondTime=(int)(lastUpdateTime/1000000000);
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
			int thisSecond=(int)(lastUpdateTime/1000000000);
			if(thisSecond>lastSecondTime){
				//System.out.println("NEW SECOND "+thisSecond+" "+frameCount);
				fps=frameCount;
				frameCount=0;
				lastSecondTime=thisSecond;
			}
			//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
			while(now-lastRenderTime<TARGET_TIME_BETWEEN_RENDERS&&now-lastUpdateTime<TIME_BETWEEN_UPDATES){
				Thread.yield();
				//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
				//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
				try{
					Thread.sleep(8);
				}catch(Exception e){
				}
				//
				now=System.nanoTime();
			}
		}
	}


	public abstract void initialize();
	public abstract void gameCodes();
	public abstract void reset();

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
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(showStats){
			g.drawString("FPS: "+fps+"\n",5,10);
		}
	}
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		short a=(short)e.getKeyCode();
		pressed.add(a);
		if(a=='K'){
			if(!menuBar.isVisible()){
				menuBar.setVisible(true);
				frame.setSize(width+5,height+50);
			}
		}
		if(a=='L'){
			if(menuBar.isVisible()){
				menuBar.setVisible(false);
				frame.setSize(width+5,height+30);
			}
		}
	}
	public void keyReleased(KeyEvent e){
		pressed.remove((short)e.getKeyCode());
	}
	public void actionPerformed(ActionEvent e){
		System.out.println("pressed something");
		if((e.getSource())==m21){
			System.out.println("show stats");
			if(showStats) showStats=false;
			else showStats=true;
		}else if((e.getSource())==m11){
			reset();
		}else if((e.getSource())==m2r1){
			width=1200;
			height=800;
			frame.setSize(width+5,height+50);
		}else if((e.getSource())==m2r2){
			width=800;
			height=600;
			frame.setSize(width+5,height+50);
		}else if(e.getSource()==m2s1){
			GAME_HERTZ=125;
			System.out.println(GAME_HERTZ);
		}else if(e.getSource()==m2s2){
			GAME_HERTZ=60;
			System.out.println(GAME_HERTZ);
		}
	}
	public void itemStateChanged(ItemEvent e){
		JMenuItem source=(JMenuItem)(e.getSource());
	}
	public static void main(String[] args){
		System.out.println("engine class ran123");
		Main.main();
	}
}
