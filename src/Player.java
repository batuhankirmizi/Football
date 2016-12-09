import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 *              Created by 28627239740ana on 9.12.2016.
 */
public class Player extends Circle implements KeyListener {
    String name;
    double xPos;
    double yPos;
    final int SIZE = 30;
    final int SPEED = 5;

    final Set<Character> pressed = new HashSet<Character>();

    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true) {
                if(pressed.contains(KeyEvent.VK_W) && !pressed.contains(KeyEvent.VK_S))
                    yPos += 5;

            }
        }
    });

    Player(String name, double xPos, double yPos) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;

        setCenterX(xPos);
        setCenterY(yPos);
        setRadius(SIZE);
    }

    void draw(Graphics g) {
        g.setColor(Color.ORANGE);

        g.fillOval((int)getCenterX(), (int)getCenterY(), (int)getRadius(), (int)getRadius());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyChar());
    }
}