package futbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class Engine extends JPanel implements KeyListener, ActionListener, ItemListener{
	public static Engine engine;
	public short fps=60;
	public short frameCount=60;
	public short ups=120;
	public short updateCount=120;
	public final Set<Short> pressed=new TreeSet<>();
	public final JFrame frame;
	short GAME_HERTZ=120;
	static short width=1200;
	static short height=800;
	static ArrayList<String> variables=new ArrayList<>();
	File sett;
	Scanner settings;
	Formatter f;
	public JMenuBar menuBar;
	private byte target_fps=60;
	JMenu menu1;
	JMenu menu2;
	public JMenuItem m11;
	public JMenuItem m21;
	public JMenuItem m2r1;
	public JMenuItem m2r2;
	public JMenuItem m2s1;
	public JMenuItem m2s2;
	public JMenuItem m2f1;
	public JMenuItem m2f2;
	public JMenuItem m2f3;
	public boolean showStats=false;
	double TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
	double TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;

	Engine(){
		for(String s: new String[]{"fps","ups"})variables.add(s);

		frame=new JFrame("Football");
		frame.setSize(width+5, height+30);
		frame.setLocation(200, 20);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame,
						"Are you sure to close this window?", "Really? Closing?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					saveConf();
					System.exit(0);
				}
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
	public void run(){
		initialize();

		frame.add(this);
		frame.setVisible(true);

		double lastUpdateTime=System.nanoTime();
		double lastRenderTime=System.nanoTime();
		new Timer(1000){
			@Override
			public void run(){
				while(true){
					try{
						Thread.sleep(time);
					}catch(Exception e){
					}
					ups=updateCount;
					updateCount=0;
					fps=frameCount;
					frameCount=0;
				}
			}
		}.start(); //fps and ups updater
		// Game loop
		while(true){
			double now=System.nanoTime();
			if(now-lastUpdateTime>TIME_BETWEEN_UPDATES){
				gameCodes();
				lastUpdateTime+=TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if(now-lastRenderTime>TARGET_TIME_BETWEEN_RENDERS){
				frame.repaint();
				lastRenderTime=now;
			}
			Thread.yield();
			try{
				Thread.sleep(3);
			}catch(Exception e){
			}
		}
	}
	public void readSett(){
		try{
			sett=new File("settings.txt");
			settings=new Scanner(sett);
		}catch(FileNotFoundException e){
			try{
				f=new Formatter("settings.txt");
				settings=new Scanner(sett);
				System.out.println("settings.txt created");
			}catch(FileNotFoundException f){
			}
		}
		while(settings.hasNext()){
			Scanner srr=new Scanner(settings.nextLine()).useDelimiter("=");
			String sasa=srr.next();
			String sasa2=srr.next();
			if(variables.contains(sasa)){
				System.out.println(sasa+" to "+sasa2);
				try{
					this.getClass().getDeclaredField(sasa).set(this,sasa2);
				}catch(Throwable e){
					System.out.println("haha");
				}
			}
		}
	}
	public void initialize(){
		readSett();
	}
	public void saveConf(){
		String s="";
		System.out.println("saved configs");
	}

	public abstract void gameCodes();
	public abstract void reset();
	public abstract void menuBar();
	protected abstract void actions(ActionEvent e);

	public JMenuBar menuBarimiz(){
		menuBar=new JMenuBar();
		menu1=new JMenu("Game");
		menu2=new JMenu("Engine");
		m11=new JMenuItem("Reset");
		m21=new JMenuItem("Show Stats");
		m2r1=new JMenuItem("1200*800");
		m2r2=new JMenuItem("800*600");
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
		frameCount++;
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
			TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
			System.out.println(GAME_HERTZ);
		}else if(e.getSource()==m2s2){
			GAME_HERTZ=60;
			TIME_BETWEEN_UPDATES=1000000000/GAME_HERTZ;
			System.out.println(GAME_HERTZ);
		}else if(e.getSource()==m2f1){
			target_fps=120;
			TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
		}else if(e.getSource()==m2f2){
			target_fps=60;
			TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
		}else if(e.getSource()==m2f3){
			target_fps=30;
			TARGET_TIME_BETWEEN_RENDERS=1000000000/target_fps;
		}else
		actions(e);
	}

	public void itemStateChanged(ItemEvent e){
		JMenuItem source=(JMenuItem)(e.getSource());
	}
	public static void main(){
		System.out.println("engine class ran123");
		Main.main();
	}
}