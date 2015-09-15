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
import java.util.Stack;
import java.util.ArrayList;
import java.lang.Math;

class PacmanCanvas extends JComponent implements ActionListener, KeyListener {

    // Refresh rate (in milliseconds).
    public static final int REFRESH_RATE = 20;
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

    // Move variable for pacman
    public int moveDirection = -1;
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
        // Move player
        move();    
        // Update AI positions
        moveGhosts();
        // Re-Draw the screen.
        this.repaint();
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KEY_UP || keyCode == KEY_DOWN ||
            keyCode == KEY_RIGHT || keyCode == KEY_LEFT) {
            this.moveDirection = keyCode;
        }
    }

    public void keyReleased(KeyEvent e) {
        //System.out.println("Key Released.");
        int keyCode = e.getKeyCode();
        if (keyCode == KEY_UP || keyCode == KEY_DOWN ||
            keyCode == KEY_RIGHT || keyCode == KEY_LEFT) {
            this.moveDirection = -1;
        }
    }
    public void keyTyped(KeyEvent e) {
        //System.out.println("Key Typed.");
    }

    private int hashLocation(int x, int y) {
        return ((y-SY)/GRIDH)*MW + (x-SX)/GRIDW;
    }

    // Frozen Freefall.
    private void moveGhosts() {
        // To move the ghosts, I'll  need to
        // do graph traversal. I shouldn't need
        // to know the entire extent of the
        // graph. In this case, since the
        // graph is so small and contained
        // in an array in memory, the case
        // is simple. I can check the array
        // to see what options are open
        // and make the ghost move
        // appropriately.


        // Move Pinky (right,down,left,up)
        if (pinky.x % 30 == 0 && pinky.y % 30 == 0) { // decide next direction
            // mark this node as visited.
            pinky.visited[MW*((pinky.y-SY)/30) + pinky.x/30] = 1;

            // get availability infonmation (occupied/unoccupied)
            int gaUp = getUp(this.m,pinky.x,pinky.y);
            int gaDown = getDown(this.m,pinky.x,pinky.y);
            int gaLeft = getLeft(this.m,pinky.x,pinky.y);
            int gaRight = getRight(this.m,pinky.x,pinky.y);
            // Get visitation info
            int gvUp = getUp(pinky.visited,pinky.x,pinky.y);
            int gvDown = getDown(pinky.visited,pinky.x,pinky.y);
            int gvLeft = getLeft(pinky.visited,pinky.x,pinky.y);
            int gvRight = getRight(pinky.visited,pinky.x,pinky.y);

            //System.out.printf("gaUp: %3d, gaDown: %3d, gaLeft: %3d, gaRight: %3d\n", gaUp, gaDown, gaLeft, gaRight);
            //System.out.printf("gvUp: %3d, gvDown: %3d, gvLeft: %3d, gvRight: %3d\n", gvUp, gvDown, gvLeft, gvRight);
            /*
            String d = "";
            if (pinky.direction == pinky.UP) { d = "UP";}
            if (pinky.direction == pinky.DOWN) { d = "DOWN";}
            if (pinky.direction == pinky.RIGHT) { d = "RIGHT";}
            if (pinky.direction == pinky.LEFT) { d = "LEFT";}
            System.out.println("Direction: " + d);
            */

            //printMap(this.m, pinky.x/30, ((pinky.y-SY)/30));

            // choose a direction
            if (gaRight == 0 && gvRight == 0) {
                pinky.direction = pinky.RIGHT;
                pinky.stack.push(pinky.LEFT);
            } else if (gaDown == 0 && gvDown == 0) {
                pinky.direction = pinky.DOWN;
                pinky.stack.push(pinky.UP);
            } else if (gaLeft == 0 && gvLeft == 0) {
                pinky.direction = pinky.LEFT;
                pinky.stack.push(pinky.RIGHT);
            } else if (gaUp == 0 && gvUp == 0) {
                pinky.direction = pinky.UP;
                pinky.stack.push(pinky.DOWN);
            } else if (!pinky.stack.isEmpty()) {
                // will keep running until empty stack
                // exception.
                pinky.direction = pinky.stack.pop();
            } else {
                // reset all nodes visited so we can traverse
                // the maze again. :)
                for (int i = 0; i < pinky.visited.length; i++) {
                    pinky.visited[i] = 0;
                }
                // reset the direction so that we don't move
                // this turn. :)
                pinky.direction = -1;
            }
        }

        // move pinky
        if (pinky.direction == pinky.RIGHT) {
            pinky.x += 1;
        } else if (pinky.direction == pinky.LEFT) {
            pinky.x -= 1;
        } else if (pinky.direction == pinky.UP) {
            pinky.y -= 1;
        } else if (pinky.direction == pinky.DOWN) {
            pinky.y += 1;
        }

        // Move Blinky (left,up,right,down)
        if (blinky.x % 30 == 0 && blinky.y % 30 == 0) { // decide next direction
            // mark this node as visited.
            blinky.visited[MW*((blinky.y-SY)/30) + blinky.x/30] = 1;

            // get availability infonmation (occupied/unoccupied)
            int gaUp = getUp(this.m,blinky.x,blinky.y);
            int gaDown = getDown(this.m,blinky.x,blinky.y);
            int gaLeft = getLeft(this.m,blinky.x,blinky.y);
            int gaRight = getRight(this.m,blinky.x,blinky.y);
            // Get visitation info
            int gvUp = getUp(blinky.visited,blinky.x,blinky.y);
            int gvDown = getDown(blinky.visited,blinky.x,blinky.y);
            int gvLeft = getLeft(blinky.visited,blinky.x,blinky.y);
            int gvRight = getRight(blinky.visited,blinky.x,blinky.y);

            //System.out.printf("gaUp: %3d, gaDown: %3d, gaLeft: %3d, gaRight: %3d\n", gaUp, gaDown, gaLeft, gaRight);
            //System.out.printf("gvUp: %3d, gvDown: %3d, gvLeft: %3d, gvRight: %3d\n", gvUp, gvDown, gvLeft, gvRight);
            /*
            String d = "";
            if (blinky.direction == blinky.UP) { d = "UP";}
            if (blinky.direction == blinky.DOWN) { d = "DOWN";}
            if (blinky.direction == blinky.RIGHT) { d = "RIGHT";}
            if (blinky.direction == blinky.LEFT) { d = "LEFT";}
            System.out.println("Direction: " + d);
            */

            //printMap(this.m, blinky.x/30, ((blinky.y-SY)/30));

            // choose a direction
            if (gaLeft == 0 && gvLeft == 0) {
                blinky.direction = blinky.LEFT;
                blinky.stack.push(blinky.RIGHT);
            } else if (gaUp == 0 && gvUp == 0) {
                blinky.direction = blinky.UP;
                blinky.stack.push(blinky.DOWN);
            } else if (gaRight == 0 && gvRight == 0) {
                blinky.direction = blinky.RIGHT;
                blinky.stack.push(blinky.LEFT);
            } else if (gaDown == 0 && gvDown == 0) {
                blinky.direction = blinky.DOWN;
                blinky.stack.push(blinky.UP);
            } else if (!blinky.stack.isEmpty()) {
                // will keep running until empty stack
                // exception.
                blinky.direction = blinky.stack.pop();
            } else {
                // reset all nodes visited so we can traverse
                // the maze again. :)
                for (int i = 0; i < blinky.visited.length; i++) {
                    blinky.visited[i] = 0;
                }
                // reset the direction so that we don't move
                // this turn. :)
                blinky.direction = -1;
            }
        }

        // move blinky
        if (blinky.direction == blinky.RIGHT) {
            blinky.x += 1;
        } else if (blinky.direction == blinky.LEFT) {
            blinky.x -= 1;
        } else if (blinky.direction == blinky.UP) {
            blinky.y -= 1;
        } else if (blinky.direction == blinky.DOWN) {
            blinky.y += 1;
        }

        // Move Inky (shortest distance to pacman (DJ's algo))
        // Move Clyde (random direction)
    }

    private void move() {
        int keyCode = this.moveDirection;
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

    // Functions to extract relative values
    // from the map based on the object's
    // screen coordinates.
    private int getUp(int[] arr, int x, int y) {
        return arr[MW*((y-1-SY)/30) + x/30];
    }
    private int getDown(int[] arr, int x, int y) {
        return arr[MW*((y+30-SY)/30) + x/30];
    }
    private int getRight(int[] arr, int x, int y) {
        return arr[MW*((y-SY)/30) + (x+30)/30];
    }
    private int getLeft(int[] arr, int x, int y) {
        return arr[MW*((y-SY)/30) + (x-1)/30];
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

    void printMap(int[] map, int x, int y) {
        System.out.printf("[ ");
        for (int i = 0; i < MH; i++) {
            if (i != 0) {
                System.out.printf("  ");
            }
            for (int j = 0; j < MW; j++) {
                if (j != 0) {
                    System.out.printf(", ");
                }
                if (i == y && j == x) {
                    // this indicates the position in the map
                    // passed in. :)
                    System.out.printf("%d", 8);
                } else {
                    System.out.printf("%d", map[MW*i + j]);
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("]");
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
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public Image image = null;
    public int x = 0, y = 0;
    public int direction = -1;
    public Stack<Integer> stack = new Stack<Integer>();
    public int[] visited = new int[PacmanCanvas.m.length];

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
