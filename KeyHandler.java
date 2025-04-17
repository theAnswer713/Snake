package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener {
    private String direction;
    private boolean hitkey;

    public KeyHandler() {
        direction = "UP";
        hitkey = false;
    }

    public boolean hitKey() {
        return hitkey;
    }

    public String getDirection(String previousDirection) {
        if(direction.equals("UP") & previousDirection.equals("DOWN")) {
            direction = "DOWN";
        }
        if(direction.equals("DOWN") & previousDirection.equals("UP")) {
            direction = "UP";
        }
        if(direction.equals("LEFT") & previousDirection.equals("RIGHT")) {
            direction = "RIGHT";
        }
        if(direction.equals("RIGHT") & previousDirection.equals("LEFT")) {
            direction = "LEFT";
        }
        return direction;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        hitkey = true;
        if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W) {
            direction = "UP";
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_S) {
            direction = "DOWN";
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A) {
            direction = "LEFT";
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D) {
            direction = "RIGHT";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No Code Needed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No Code Needed
    }
}