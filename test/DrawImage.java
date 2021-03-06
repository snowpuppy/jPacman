import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.event.ActionListener;

class MyCanvas extends JComponent implements ActionListener, KeyListener {

    // Maze width and height
    public static final int MW = 27;
    public static final int MH = 16;
    // Start x,y of maze on maze image
    public static final int SX = 0;
    public static final int SY = 60;
    // The maze
    public static int[] m = {
  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
  0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0,
  1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

    // There are 164 dots. Subtract 4 for ghosts,
    // 2 for empty positions above ghosts, and
    // subtract one for pacman.
    Dot[] dots = new Dot[157];
    Image maze = Toolkit.getDefaultToolkit().getImage("../images/maze.png");
    Pacman pacman = new Pacman(Toolkit.getDefaultToolkit().getImage("../images/pacman.png"), 360, 420);
    Ghost blinky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/blinky.png"), 330, 270);
    Ghost pinky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/pinky.png"), 330, 300);
    Ghost inky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/inky.png"), 360, 270);
    Ghost clyde = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/clyde.png"), 360, 300);

    public MyCanvas() {
        super();
        int k = 0;
        for (int i = 0; i < MH; i++) {
            for (int j = 0; j < MW; j++) {
                int pos_x = SX + 30*j;
                int pos_y = SY + 30*i;
                boolean skip = false;
                if (m[MW*i + j] == 0) {
                    skip = (pos_x == blinky.x && pos_y == blinky.y) ||
                           (pos_x == pinky.x && pos_y == pinky.y) ||
                           (pos_x == inky.x && pos_y == inky.y) ||
                           (pos_x == clyde.x && pos_y == clyde.y) ||
                           (pos_x == 360 && pos_y == 420 ) ||
                           (pos_x == 330 && pos_y == 240 ) ||
                           (pos_x == 360 && pos_y == 240 );
                    if (skip == false) {
                        dots[k] = new Dot(pos_x, pos_y);
                        k++;
                    }
                }
            }
        }
        setFocusable(true);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(maze, 0, 0, this);
        //g2.drawImage(pacman, 360, 420, this);
        g2.drawImage(pacman.image, pacman.x, pacman.y, pacman.x+30, pacman.y+30, 0, 0, 30, 30, this);
        g2.drawImage(blinky.image, blinky.x, blinky.y, this);
        g2.drawImage(pinky.image, pinky.x, pinky.y, this);
        g2.drawImage(inky.image, inky.x, inky.y, this);
        g2.drawImage(clyde.image, clyde.x, clyde.y, this);
        // Draw all of the dots if they should be
        // shown.
        for (int i = 0; i < dots.length; i++) {
            if (dots[i].show == true) {
                g2.drawImage(dots[i].image, dots[i].x, dots[i].y, this);
            }
        }
        g2.finalize();
    }

    public void actionPerformed(ActionEvent e) {
        // Update AI positions
        
        // Re-Draw the screen.
        this.repaint();
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed.");
    }
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released.");
    }
    public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed.");
    }
}

class Pacman {
    public Image image = null;
    public int x = 0, y = 0;

    public Pacman(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }
}

class Ghost {
    public Image image = null;
    public int x = 0, y = 0;

    public Ghost(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }
}

class Dot {
    public static Image image = Toolkit.getDefaultToolkit().getImage("../images/dot.png");
    public int x,y;
    public boolean show = true;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void hide() {
        show = false;
    }
}

public class DrawImage {
    public static final int SCREEN_X = 800, SCREEN_Y = 600;

    public static void main(String[] a) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, SCREEN_X, SCREEN_Y);
        window.getContentPane().add(new MyCanvas());
        window.setVisible(true);
    }
}
