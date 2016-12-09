import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *                  Created by 28627239740ana on 9.12.2016.
 */
public class Main extends JPanel {
    private JFrame frame;

    private final int PLAYER_COUNT = 2;

    Player player;
    // Ball ball;

    static final int WIDTH = 1200;
    static final int HEIGHT = 800;

    Main() {
        frame = new JFrame("Football");
            frame.setSize(WIDTH, HEIGHT);
            frame.setLocation(200, 100);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(false);

        // Initialize players
        Player[] players = new Player[PLAYER_COUNT];
        players[0] = new Player("Batu", WIDTH / 3, HEIGHT / 2);
        players[1] = new Player("Aytaç", WIDTH * 2 / 3, HEIGHT / 2);

        // Initialize ball

        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setLocation(0, 0);
        addKeyListener(player);
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        frame.add(this);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}