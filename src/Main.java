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

    static Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            // WASD
            while(true) {
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(pressed.contains(KeyEvent.VK_W) && !pressed.contains(KeyEvent.VK_S)){
                    players[0].setCenterY(players[0].getCenterY() - players[0].SPEED);
					players[0].xSpeed=-players[0].speed	//Player.xSpeed oyuncunun hızını hit() için göstermeye yarar
				}
                else if(!pressed.contains(KeyEvent.VK_W) && pressed.contains(KeyEvent.VK_S)){
                    players[0].setCenterY(players[0].getCenterY() + players[0].SPEED);
					players[0].xSpeed=players[0].speed
				}else players[0].xSpeed=0
                if(pressed.contains(KeyEvent.VK_A) && !pressed.contains(KeyEvent.VK_D)){
                    players[0].setCenterX(players[0].getCenterX() - players[0].SPEED);
					players[0].ySpeed=-players[0].speed
				}
                else if(!pressed.contains(KeyEvent.VK_A) && pressed.contains(KeyEvent.VK_D)){
                    players[0].setCenterX(players[0].getCenterX() + players[0].SPEED);
					players[0].ySpeed=players[0].speed
				}else players[0].ySpeed=0
            }
        }
    });

    static Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            // UPDOWNRIGHTLEFT
            while(true) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(pressed.contains(KeyEvent.VK_UP) && !pressed.contains(KeyEvent.VK_DOWN)){
                    players[1].setCenterY(players[1].getCenterY() - players[1].SPEED);
					players[1].xSpeed=-players[1].speed
				}
                else if(pressed.contains(!KeyEvent.VK_UP) && pressed.contains(KeyEvent.VK_DOWN)){
                    players[1].setCenterY(players[1].getCenterY() + players[1].SPEED);
					players[1].xSpeed=players[1].speed
				}else players[1].xSpeed=0
                if(pressed.contains(KeyEvent.VK_LEFT) && !pressed.contains(KeyEvent.VK_RIGHT)){
                    players[1].setCenterX(players[1].getCenterX() - players[1].SPEED);
					players[1].ySpeed=-players[1].speed
				}
                else if(!pressed.contains(KeyEvent.VK_LEFT) && pressed.contains(KeyEvent.VK_RIGHT)){
                    players[1].setCenterX(players[1].getCenterX() + players[1].SPEED);
					players[1].ySpeed=players[1].speed
				}else players[1].ySpeed=0
                
            }
        }
    });

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

    public static void main(String[] args) {
        Main m = new Main();

        // TODO Game loop
        t1.start();
		t2.start();
        ball.ballMover.start();
        while(true) {
            try {
                Thread.sleep(16);	// 1000ms/60fps=16.66
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			// hit(PlayerXPos, PlayerYPos, PlayerXSpeed, PlayerYSpeed)
            if(players[0].intersects(ball.getCenterX(), ball.getCenterY(), ball.SIZE, ball.SIZE))
                ball.hit(players[0].getCenterX(), players[0].getCenterY(), players[0].xSpeed, players[0].ySpeed);
            else if(players[1].intersects(ball.getCenterX(), ball.getCenterY(), ball.SIZE, ball.SIZE))
                ball.hit(players[1].getCenterX(), players[1].getCenterY(), players[1].xSpeed, players[1].ySpeed);

            frame.repaint();
        }
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
