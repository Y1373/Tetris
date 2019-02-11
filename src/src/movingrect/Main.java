package src.movingrect;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Thread.State;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


public class Main {

    public static int leftCount = 0;
    public static final int horCells = 10;//arz mostatil abi
    public static final int verCells = 17;//tul mostatil abi
    public static final int horStart = 150;// mokhtasate shurue  x mostatil abi
    public static final int verStart = 50; // mokhtasate shurue y mostatil abi
    public static final int[] objectScore = {1, 2, 3, 4, 5, 6, 7};


    public static boolean game_over;
    public static int speed;
    public static int totalScore;
    public static int scoreUp; //?
    public static int object_map[][][] = new int[7][3][3];

    public static FallingObject next_object;
    public static screenMap scrmap = new screenMap();
    public static MovingThread thread;

    public static void main(String[] args) {
        Main.initialize();
        Q q = new Q();
        thread = new MovingThread(q);

    }

    public static void initialize() {
        object_map[0][0][0] = 1;
        object_map[0][0][1] = 0;
        object_map[0][0][2] = 0;
        object_map[0][1][0] = 1;
        object_map[0][1][1] = 0;
        object_map[0][1][2] = 0;
        object_map[0][2][0] = 1;
        object_map[0][2][1] = 0;
        object_map[0][2][2] = 0;

        object_map[1][0][0] = 1;
        object_map[1][0][1] = 0;
        object_map[1][0][2] = 0;
        object_map[1][1][0] = 1;
        object_map[1][1][1] = 1;
        object_map[1][1][2] = 0;
        object_map[1][2][0] = 1;
        object_map[1][2][1] = 0;
        object_map[1][2][2] = 0;

        object_map[2][0][0] = 1;
        object_map[2][0][1] = 1;
        object_map[2][0][2] = 0;
        object_map[2][1][0] = 1;
        object_map[2][1][1] = 1;
        object_map[2][1][2] = 0;
        object_map[2][2][0] = 0;
        object_map[2][2][1] = 0;
        object_map[2][2][2] = 0;

        object_map[3][0][0] = 1;
        object_map[3][0][1] = 1;
        object_map[3][0][2] = 0;
        object_map[3][1][0] = 1;
        object_map[3][1][1] = 0;
        object_map[3][1][2] = 0;
        object_map[3][2][0] = 1;
        object_map[3][2][1] = 0;
        object_map[3][2][2] = 0;

        object_map[4][0][0] = 1;
        object_map[4][0][1] = 0;
        object_map[4][0][2] = 0;
        object_map[4][1][0] = 1;
        object_map[4][1][1] = 0;
        object_map[4][1][2] = 0;
        object_map[4][2][0] = 1;
        object_map[4][2][1] = 1;
        object_map[4][2][2] = 0;

        object_map[5][0][0] = 1;
        object_map[5][0][1] = 0;
        object_map[5][0][2] = 0;
        object_map[5][1][0] = 1;
        object_map[5][1][1] = 1;
        object_map[5][1][2] = 0;
        object_map[5][2][0] = 0;
        object_map[5][2][1] = 1;
        object_map[5][2][2] = 0;

        object_map[6][0][0] = 0;
        object_map[6][0][1] = 1;
        object_map[6][0][2] = 0;
        object_map[6][1][0] = 1;
        object_map[6][1][1] = 1;
        object_map[6][1][2] = 0;
        object_map[6][2][0] = 1;
        object_map[6][2][1] = 0;
        object_map[6][2][2] = 0;

    }
}

class Q {
    synchronized void qwait(int delay) {
        try {
            wait(delay);
        } catch (InterruptedException ex) {
        }
    }

    synchronized void qnotify() {
        notify();
    }
}
//  piade saziye amaiyate key ha
class MyFrame extends JFrame implements KeyListener {
     Q q;
     public MyFrame(Q q) {
        this.q = q;
        addKeyListener(this);
    }
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        MovingThread.PressedKey = key;
        ke.consume();
        if ((key == ke.VK_LEFT) || (key == ke.VK_RIGHT) || (key == ke.VK_UP) || (key == ke.VK_DOWN))
            q.qnotify();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}

class MovingThread implements Runnable {

    Q q;
    public static int PressedKey;
    private static int speed;
    FallingObject fo;

    DrawPanel panel;
    MyFrame application;

    MovingThread(Q q) {
        Main.game_over = false;
        Main.totalScore = 0;
        Main.speed = 250;
        Main.scoreUp = 0;

        speed = Main.speed;
        this.q = q;
        panel = new DrawPanel();
        application = new MyFrame(q);
        application.add(panel);
        fo = new FallingObject(3, -1); //az vasat shekla be payin miad
        Main.next_object = new FallingObject(3, -1);
        new Thread(this, "aaaaa").start();
    }


    public void run() {

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setVisible(true);
        application.setSize(800, 640);

        panel.repaint();
        for (; ; ) {
            q.qwait(speed);
            for (int z = 0; z < 10; z++) {
                if (PressedKey == KeyEvent.VK_LEFT) {
                    fo.move_side(-1);
                    panel.repaint();
                    PressedKey = KeyEvent.VK_0;
                    speed = speed - Main.speed / 10;
                    q.qwait(speed);
                } else if (PressedKey == KeyEvent.VK_RIGHT) {
                    fo.move_side(+1);
                    panel.repaint();
                    PressedKey = KeyEvent.VK_0;
                    speed = speed - Main.speed / 10;
                    q.qwait(speed);
                } else if (PressedKey == KeyEvent.VK_UP) {
                    fo.rotate();
                    panel.repaint();
                    PressedKey = KeyEvent.VK_0;
                    speed = speed -Main.speed / 10;
                    q.qwait(speed);
                } else if (PressedKey == KeyEvent.VK_DOWN) {
                    PressedKey = KeyEvent.VK_0;
                    break;
                }
            }

            if (!fo.move()) {
                Main.scrmap.update();
                fo = new FallingObject(3, -1);
                fo = Main.next_object;
                Main.next_object = new FallingObject(3, -1);
            }

            panel.repaint();//safe abi ra dobare besaz
            speed = Main.speed;

            if (Main.game_over) {
                for (; ; ) ;
            }
        }

    }
}
