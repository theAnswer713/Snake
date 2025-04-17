package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Random;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Iterator;

public class Snake {
    // MAIN METHOD: CALLS CONSTRUCTOR
    public static void main(String[] args) {
        new Snake();
    }
    // CONSTRUCTOR: CREATES SNAKE GAME FRAME
    public Snake() {
        JFrame frame = new JFrame("Snake Game");
        JPanel panel = new MyPanel();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setAlwaysOnTop(false);
    }
    // PANEL: RUNS THE GAME IN THE FRAME
    public static class MyPanel extends JPanel implements ActionListener {
        // CLASS VARIABLES
        private Random rand;
        private int width, height, scale, gridGap;
        private String currentDirection, previousDirection;
        private Deque<Cell> snake;
        private Deque<Cell> s2;
        private Cell apple;
        private KeyHandler keyhandler;
        private Timer timer;

        // CONSTRUCTOR: SETUP PANEL COLOR, SIZE, TIMER
        public MyPanel() {
            rand = new Random();

            width = 1000;
            height = 800;
            scale = 40;
            gridGap = 4;

            currentDirection = "UP";
            previousDirection = "NONE";

            snake = new LinkedList<Cell>();
            snake.add(new Cell((height/scale)/2*scale, (width/scale)/2*scale));

            s2 = new LinkedList<Cell>();

            int appleRow = rand.nextInt(height/scale)*scale;
            int appleCol = rand.nextInt(width/scale)*scale;
            apple = new Cell(appleRow, appleCol);

            keyhandler = new KeyHandler();
            addKeyListener(keyhandler);
            setFocusable(true);

            setBackground(new Color(0,0,0));
            setPreferredSize(new Dimension(width,height));

            timer = new Timer(100,this);
            timer.start();
        }

        // ACTION PERFORMED: CALLED BY TIMER
        public void actionPerformed(ActionEvent e) {
            // IF PLAYER HAS NOT HIT A KEY YET, DON'T START YET
            if(keyhandler.hitKey() == false) {
                return;
            }

            currentDirection = keyhandler.getDirection(previousDirection);

            /**
             * STEP 1: MAKE THE SNAKE MOVE UP/DOWN/LEFT/RIGHT)
             * USE METHODS LIKE addFirst, addLast, removeFirst, removeLast, etc.
             * EXAMPLE: if(currentDirection.equals("UP")) {snake.addFirst(____, ____);}
             */
            Cell head=snake.getFirst();

            if(currentDirection.equals("UP")) {
                snake.addFirst(new Cell(head.getRow()-scale,head.getCol()));
            }
            if(currentDirection.equals("DOWN")) {
                snake.addFirst(new Cell(head.getRow()+scale,head.getCol()));
            }
            if(currentDirection.equals("LEFT")) {
                snake.addFirst(new Cell(head.getRow(),head.getCol()-scale));
            }
            if(currentDirection.equals("RIGHT")) {
                snake.addFirst(new Cell(head.getRow(),head.getCol()+scale));
            }

            /**
             * STEP 2: IF THE SNAKE TOUCHES APPLE, TELEPORT IT TO EMPTY LOCATION,
             * AND ON THE FRAME THAT THE SNAKE EATS AN APPLE, DON'T removeLast
             */
            head=snake.getFirst();
            if(apple.getRow()==head.getRow()&&apple.getCol()==head.getCol()) {
                boolean onSnake=false;
                do {
                    onSnake=false;
                    int appleRow = rand.nextInt(height / scale) * scale;
                    int appleCol = rand.nextInt(width / scale) * scale;
                    apple = new Cell(appleRow, appleCol);

                    Iterator<Cell> itr=snake.iterator();
                    while(itr.hasNext()) {
                        Cell cell=itr.next();
                        if(cell.getRow()==apple.getRow()&&cell.getCol()==apple.getCol()) {
                            onSnake=true;
                            break;
                        }
                    }
                } while(onSnake);
            }
            else {
                snake.removeLast();
            }

            /**
             * STEP 3: IF THE SNAKE GOES OFF THE SCREEN
             * CALL timer.stop(); and return; to END THE PROGRAM
             */


            /**
             * STEP 4: IF THE SNAKE COLLIDES WITH ITSELF
             * CALL timer.stop(); and return; to END THE PROGRAM
             */
            head=snake.removeFirst();
            Iterator<Cell> itr=snake.iterator();
            while(itr.hasNext()) {
                Cell cell=itr.next();
                if(head.getRow()==cell.getRow()&&head.getCol()==cell.getCol()) {
                    timer.stop();
                    return;
                }
            }
            snake.addFirst(head);

            // WRAP AROUND EDGES
            head=snake.getFirst();
            if(head.getRow()<0) {
                s2.addFirst(new Cell(head.getRow()+height,head.getCol()));
                snake.removeFirst();
            }
            if(head.getRow()>=height) {
                s2.addFirst(new Cell(head.getRow()-height,head.getCol()));
                snake.removeFirst();
            }
            if(head.getCol()<0) {
                s2.addFirst(new Cell(head.getRow(),head.getCol()+width));
                snake.removeFirst();
            }
            if(head.getCol()>=width) {
                s2.addFirst(new Cell(head.getRow(),head.getCol()-width));
                snake.removeFirst();
            }
            if(snake.isEmpty()) {
                snake=s2;
                s2.clear();
            }

            previousDirection = currentDirection;
            repaint();
        }

        // PAINT COMPONENT: DRAW SNAKE AND FOOD
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // DRAW ALL BLACK
            g.setColor(new Color(0,0,0));
            g.fillRect(0, 0, width, height);

            // DRAW GREEN SQUARES
            g.setColor(new Color(0,0,0));
            for(int r=0; r<height; r+=scale) {
                for(int c=0; c<width; c+=scale) {
                    g.fillRect(c, r, scale-gridGap, scale-gridGap);
                }
            }

            // DRAW SNAKE
            g.setColor(new Color(0,200,0));
            Iterator<Cell> iterator = snake.iterator();
            while(iterator.hasNext()) {
                Cell cell = iterator.next();
                g.fillRect(cell.getCol(), cell.getRow(), scale-gridGap, scale-gridGap);
            }

            // DRAW APPLE
            g.setColor(new Color(255,0,0));
            g.fillRect(apple.getCol(), apple.getRow(), scale-gridGap, scale-gridGap);


        }
    }
}
