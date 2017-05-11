package futbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class Engine extends JPanel implements KeyListener, ActionListener, ItemListener{
	static boolean run=true;
	static Engine engine;
	public short fps=60;
	short frameCount=60;
	public short ups=120;
	private short updateCount=120;
	final static Set<Short> pressed=new TreeSet<>();
	final JFrame frame;
	private static short GAME_HERTZ=120;
	static short width=1200;
	static short height=800;
	static ArrayList<String> variables=new ArrayList<>();
	private File sett;
	private Scanner settings;
	private Formatter f;
	private JMenuBar menuBar;
	static private byte target_fps=60;
	JMenu menu1; //Game
	private JMenu menu2; //Engine
	private JMenuItem m11; //reset
	private JMenuItem m21; //Show stats
	private JMenuItem m2r1; //1200*800
	private JMenuItem m2r2; //900*600
	private JMenuItem m2s1; //%100 speed
	private JMenuItem m2s2; //%50 speed
	private JMenuItem m2f1; //120 fps
	private JMenuItem m2f2; //60 fps
	private JMenuItem m2f3; //30 fps
	boolean showStats=true;
	private static double TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
	private static double TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
	private static double lastUpdateTime=System.nanoTime();
	private static double lastRenderTime=System.nanoTime();
	static Random rng=new Random();

	Engine(){
		variables.addAll(new ArrayList<String>(Arrays.asList("fps","ups")));

		frame=new JFrame("Football");
		frame.setSize(width+5, height+30);
		frame.setLocation(200, 20);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				run=false;
				System.exit(0);
			}
		});
		frame.setResizable(false);
		JPanel contPane=new JPanel(new BorderLayout());

		frame.setJMenuBar(menuBarimiz());
		frame.setContentPane(contPane);
		menuBar.setVisible(false);

		setLayout(null);
		setSize(width, height);
		setLocation(0, 0);
		addKeyListener(this);
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocusInWindow();
		

		System.out.println("sup created");
	}
	void run(){
		initialize();

		frame.add(this);
		frame.setVisible(true);

		new Timer(1000){
			@Override
			public void run(){
				while(run){
					super.run();
					ups=updateCount;
					updateCount=0;
					fps=frameCount;
					frameCount=0;
				}
			}
		}.start(); //fps and ups updater
		// Game loop
		TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
		TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
		while(run){
			//double now=System.nanoTime();
			if(System.nanoTime()-lastUpdateTime>TIME_BETWEEN_UPDATES){
				gameCodes();
				lastUpdateTime+=TIME_BETWEEN_UPDATES;
                //lastUpdateTime+=System.nanoTime();
				updateCount++;
                if(System.nanoTime()-lastRenderTime>TARGET_TIME_BETWEEN_RENDERS){
                    frame.repaint();
                    frameCount++;
                    lastRenderTime+=TARGET_TIME_BETWEEN_RENDERS;
                }
			}

			Thread.yield();
			try{
				Thread.sleep(4);
			}catch(Exception ignored){
			}
		}
		try{
			saveConf();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}
	}
	private void readSett(){
		System.out.println("reading settings");
		try{
			sett=new File("settings.txt");
			settings=new Scanner(sett);
		}catch(FileNotFoundException e){
			try{
				f=new Formatter("settings.txt");
				settings=new Scanner(sett);
				System.out.println("settings.txt created");
			}catch(FileNotFoundException ignored){
			}
		}
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
	}
	void initialize(){
		readSett();
	}
	public void saveConf() throws IllegalAccessException{
		String s="";
		for(String a:variables)
			try{
				s+=a+"="+this.getClass().getDeclaredField(a).get(this)+"\n";
			}catch(Exception e){
				try{
					s+=a+"="+this.getClass().getField(a).get(this)+"\n";
				}catch(Exception e1){
					System.out.println("haha"+e1);
					e1.printStackTrace();
				}
			}
		Formatter f=null;
		try{
			f=new Formatter(sett);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		f.format(s);
		f.close();
		System.out.println("saved configs");
	}

	protected abstract void gameCodes();
	protected abstract void reset();
	protected abstract void menuBar();
	protected abstract void actions(ActionEvent e);

	private JMenuBar menuBarimiz(){
		menuBar=new JMenuBar();
		menu1=new JMenu("Game");
		menu2=new JMenu("Engine");
		m11=new JMenuItem("Reset");
		m21=new JMenuItem("Show Stats");
		m2r1=new JMenuItem("1200*800");
		m2r2=new JMenuItem("900*600");
		m2s1=new JMenuItem("%100");
		m2s2=new JMenuItem("%50");
		m2f1=new JMenuItem("120");
		m2f2=new JMenuItem("60");
		m2f3=new JMenuItem("30");
		m21.addActionListener(this);
		m11.addActionListener(this);
		m2r1.addActionListener(this);
		m2r2.addActionListener(this);
		m2s1.addActionListener(this);
		m2s2.addActionListener(this);
		m2f1.addActionListener(this);
		m2f2.addActionListener(this);
		m2f3.addActionListener(this);
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
		menu2.add("FPS:");
		menu2.add(m2f1);
		menu2.add(m2f2);
		menu2.add(m2f3);
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar();
		return menuBar;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(showStats){
			g.drawString("FPS: "+fps+"    UPS:"+ups,5,10);
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
			showStats=!showStats;
		}else if((e.getSource())==m11){
			reset();
		}else if((e.getSource())==m2r1){
			setFrame(1200,800);
		}else if((e.getSource())==m2r2){
			setFrame(900,600);
		}else if(e.getSource()==m2s1){
			setUps(120);
		}else if(e.getSource()==m2s2){
			setUps(60);
		}else if(e.getSource()==m2f1){
			setFps(120);
		}else if(e.getSource()==m2f2){
			setFps(60);
		}else if(e.getSource()==m2f3){
			setFps(30);
		}else
		actions(e);
	}

	public void itemStateChanged(ItemEvent e){
		JMenuItem source=(JMenuItem)(e.getSource());
	}
	public static void main(){
		System.out.println("engine class ran123");
	}
	static float scaleX=width/1200;
	static float scaleY=width/800;
	public static double scaleX(){
		return scaleX;
	}
	public static double scaleY(){
		return scaleY;
	}
	public static double scaleSize(){
		return scaleX;
	}
	public static int scaleX(double a){
		return (int)(a*width/1200);
	}
	public static int scaleY(double a){
		return (int)(a*height/800);
	}
	public static int scaleSize(double a){
		//return (int)(a*scaleX);
		return (int)(a*width/1200);
	}
	public void setFps(int fps){
		target_fps=(byte)fps;
		TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
		System.out.println(fps+" fps");
	}
	public void setUps(int ups){
		GAME_HERTZ=(byte)ups;
		TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
		System.out.println(ups+" ups");
	}
	public void setFrame(int x,int y){
		width=(short)x;
		height=(short)y;
		scaleX=width/1200;
		scaleY=width/800;
		frame.setSize(width+5,height+50);
	}
}