import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 *                  Created by 28627239740ana on 9.12.2016.
 */
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
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(pressed.contains(KeyEvent.VK_W) && !pressed.contains(KeyEvent.VK_S))
                    players[0].setCenterY(players[0].getCenterY() - players[0].SPEED);
                if(!pressed.contains(KeyEvent.VK_W) && pressed.contains(KeyEvent.VK_S))
                    players[0].setCenterY(players[0].getCenterY() + players[0].SPEED);
                if(pressed.contains(KeyEvent.VK_A) && !pressed.contains(KeyEvent.VK_D))
                    players[0].setCenterX(players[0].getCenterX() - players[0].SPEED);
                if(!pressed.contains(KeyEvent.VK_A) && pressed.contains(KeyEvent.VK_D))
                    players[0].setCenterX(players[0].getCenterX() + players[0].SPEED);
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

                if(pressed.contains(KeyEvent.VK_UP) && !pressed.contains(KeyEvent.VK_DOWN))
                    players[1].setCenterY(players[1].getCenterY() - players[1].SPEED);
                if(pressed.contains(!KeyEvent.VK_UP) && pressed.contains(KeyEvent.VK_DOWN))
                    players[1].setCenterY(players[1].getCenterY() + players[1].SPEED);
                if(pressed.contains(KeyEvent.VK_LEFT) && !pressed.contains(KeyEvent.VK_RIGHT))
                    players[1].setCenterX(players[1].getCenterX() - players[1].SPEED);
                if(!pressed.contains(KeyEvent.VK_LEFT) && pressed.contains(KeyEvent.VK_RIGHT))
                    players[1].setCenterX(players[1].getCenterX() + players[1].SPEED);
                
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
        ball.ballMover.start();
        while(true) {
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(players[0].intersects(ball.getCenterX(), ball.getCenterY(), ball.SIZE, ball.SIZE))
                ball.hit(players[0].getCenterX(), players[0].getCenterY(), players[0].SIZE, players[0].SIZE);
            else if(players[1].intersects(ball.getCenterX(), ball.getCenterY(), ball.SIZE, ball.SIZE))
                ball.hit(players[1].getCenterX(), players[1].getCenterY(), players[1].SIZE, players[1].SIZE);

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