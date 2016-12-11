package futbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class Main extends JPanel implements KeyListener {
    private static JFrame frame;
    private final int PLAYER_COUNT = 2;


    private static Player[] players;
    private static Ball ball;

    static final int WIDTH = 1200;
    static final int HEIGHT = 800;

    static Set<Integer> pressed = new HashSet<>();

    public static void main(String[] args) {
        Main m = new Main();

        // TODO Game loop
        Thread t1 = new Thread(players[0]);
		Thread t2 = new Thread(players[1]);
		t1.start();
		t2.start();
        ball.ballMover.start();
        while(true) {
            try {
                Thread.sleep(16);	// 1000ms/60fps=16.66
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

			//print speeds
			System.out.println("p1: "+Math.sqrt(players[0].xSpeed*players[0].xSpeed+players[0].ySpeed*players[0].ySpeed));
		
            // hit(PlayerXPos, PlayerYPos, PlayerXSpeed, PlayerYSpeed)
            if(players[0].intersects(ball.getCenterX(), ball.getCenterY(), ball.SIZE, ball.SIZE)){
                ball.hit(players[0].getCenterX(), players[0].getCenterY(), players[0].xSpeed, players[0].ySpeed);
				System.out.println("p0 hit");
			}
            else if(players[1].intersects(ball.getCenterX(), ball.getCenterY(), ball.SIZE, ball.SIZE)){
                ball.hit(players[1].getCenterX(), players[1].getCenterY(), players[1].xSpeed, players[1].ySpeed);
				System.out.println("p1 hit");
			}
            frame.repaint();
        }
    }

	Main() {
        frame = new JFrame("Football");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(200, 20);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

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

        players[0].draw(g);
        players[1].draw(g);
        ball.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }
}
