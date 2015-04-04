import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

class PacmanCanvas extends JComponent implements ActionListener, KeyListener {

    // Refresh rate (in milliseconds).
    public static final int REFRESH_RATE = 50;
    // Key Constants
    public static final int KEY_UP = KeyEvent.VK_UP;
    public static final int KEY_DOWN = KeyEvent.VK_DOWN;
    public static final int KEY_LEFT = KeyEvent.VK_LEFT;
    public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    // Maze width and height
    public static final int MW = 27;
    public static final int MH = 16;
    // Grid width/height
    public static final int GRIDW = 30;
    public static final int GRIDH = 30;
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

    // The timer
    public static Timer redrawTimer = null;

    // There are 164 dots. Subtract 4 for ghosts,
    // 2 for empty positions above ghosts, and
    // subtract one for pacman.

    // TODO: Create a tree of dots and perform collision detection
    // on the dots.
    Dot[] dotMap = new Dot[m.length];
    Dot[] dots = new Dot[157];
    Image maze = Toolkit.getDefaultToolkit().getImage("../images/maze.png");
    Pacman pacman = new Pacman(Toolkit.getDefaultToolkit().getImage("../images/pacman.png"), 360, 420);
    Ghost blinky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/blinky.png"), 330, 270);
    Ghost pinky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/pinky.png"), 330, 300);
    Ghost inky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/inky.png"), 360, 270);
    Ghost clyde = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/clyde.png"), 360, 300);

    public PacmanCanvas() {
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
                        dotMap[hashLocation(pos_x,pos_y)] = dots[k];
                        k++;
                    }
                }
            }
        }
        setFocusable(true);
        addKeyListener(this);
        redrawTimer = new Timer(REFRESH_RATE,this);
        redrawTimer.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(maze, 0, 0, this);
        // Draw all of the dots if they should be
        // shown.
        for (int i = 0; i < dots.length; i++) {
            if (dots[i].show == true) {
                g2.drawImage(dots[i].image, dots[i].x, dots[i].y, this);
            }
        }
        g2.drawImage(pacman.image, pacman.x, pacman.y, pacman.x+pacman.w, pacman.y+pacman.h,
                                   pacman.fx, pacman.fy, pacman.fx+pacman.w, pacman.fy+pacman.h, this);
        g2.drawImage(blinky.image, blinky.x, blinky.y, this);
        g2.drawImage(pinky.image, pinky.x, pinky.y, this);
        g2.drawImage(inky.image, inky.x, inky.y, this);
        g2.drawImage(clyde.image, clyde.x, clyde.y, this);
        g2.finalize();
    }

    public void actionPerformed(ActionEvent e) {
        // Update AI positions
        
        // Re-Draw the screen.
        this.repaint();
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KEY_UP) {
            //System.out.println("Key Up.");
            if (this.m[MW*((pacman.y-1-SY)/30) + pacman.x/30] == 0) {
                pacman.y -= 1;
                pacman.animate(Pacman.UP);
                hideDots(pacman);
            }
        } else if (keyCode == KEY_DOWN) {
            //System.out.println("Key Down.");
            if (this.m[MW*((pacman.y+30-SY)/30) + pacman.x/30] == 0) {
                pacman.y += 1;
                pacman.animate(Pacman.DOWN);
                hideDots(pacman);
            }
        } else if (keyCode == KEY_RIGHT) {
            //System.out.println("Key Right.");
            if (this.m[MW*((pacman.y-SY)/30) + (pacman.x+30)/30] == 0) {
                pacman.x += 1;
                pacman.animate(Pacman.RIGHT);
                hideDots(pacman);
            }
        } else if (keyCode == KEY_LEFT) {
            //System.out.println("Key Left.");
            if (this.m[MW*((pacman.y-SY)/30) + (pacman.x-1)/30] == 0) {
                pacman.x -= 1;
                pacman.animate(Pacman.LEFT);
                hideDots(pacman);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        //System.out.println("Key Released.");
    }
    public void keyTyped(KeyEvent e) {
        //System.out.println("Key Typed.");
    }

    private int hashLocation(int x, int y) {
        return ((y-SY)/GRIDH)*MW + (x-SX)/GRIDW;
    }

    private List<Dot> collided(int xpos, int ypos, int w, int h) {
        ArrayList<Dot> dotList = new ArrayList<Dot>();
        final int area = w*h;
        final int numBlocks = area/(GRIDW*GRIDH);
        // check near edges and middle
        for (int x = xpos; x < xpos + w; x+=GRIDW) {
            for (int y = ypos; y < ypos + h; y += GRIDH) {
                Dot myDot = dotMap[hashLocation(x,y)];
                if (myDot != null) {
                    dotList.add(myDot);
                }
            }
        }
        // Check far edges of item.
        for (int x = xpos; x < xpos + w; x+=GRIDW) {
            int y = ypos + h;
            Dot myDot = dotMap[hashLocation(x,y)];
            if (myDot != null) {
                dotList.add(myDot);
            }
        }
        for (int y = ypos; y < ypos + h; y+=GRIDH) {
            int x = xpos + w;
            Dot myDot = dotMap[hashLocation(x,y)];
            if (myDot != null) {
                dotList.add(myDot);
            }
        }

        return dotList;
    }

    private void hideDots(Pacman pac) {
        List<Dot> dotList = collided(pac.x, pac.y, pac.w, pac.h);
        //System.out.println("Collided dots: ");
        for (Dot d : dotList) {
            int p_x = pac.x + pac.w/2;
            //System.out.println("pac.x: " + pac.x + " pac.w: " + pac.w + "p_x: " + p_x );
            int p_y = pac.y + pac.h/2;
            //System.out.println("pac.y: " + pac.y + " pac.h: " + pac.h + "p_y: " + p_y );
            int d_x = d.x + d.w/2;
            //System.out.println("d.x: " + d.x + " d.x: " + d.w + "d_x: " + d_x );
            int d_y = d.y + d.h/2;
            //System.out.println("d.y: " + d.y + " d.y: " + d.h + "d_y: " + d_y );
            int dist = (int) Math.sqrt((p_x - d_x)*(p_x - d_x) + (p_y - d_y)*(p_y - d_y));
            //System.out.println("Dist: " + dist);
            if (dist < d.w / 2) {
                d.hide();
            }
        }
    }
}

class Pacman {
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public Image image = null;
    public int x = 0, y = 0;
    static final int w = 30, h = 30;
    public int fx = 0, fy = 0;

    public Pacman(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void animate(int dir) {
        // update the x coordinate for the
        // frame. (Frames only extend in x-direction)
        if (this.fx == 0) {
            this.fx = 30;
        }
        else {
            this.fx = 0;
        }
        // y-coordinate of frame depends on
        // direction passed in.
        if (dir >= RIGHT && dir <= DOWN) {
            this.fy = 30*dir;
        }
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
    public int w=30, h=30;
    public boolean show = true;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void hide() {
        show = false;
    }
}

public class PacmanGame {
    public static final int SCREEN_X = 800, SCREEN_Y = 600;

    public static void main(String[] a) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, SCREEN_X, SCREEN_Y);
        window.getContentPane().add(new PacmanCanvas());
        window.setVisible(true);
    }
}
