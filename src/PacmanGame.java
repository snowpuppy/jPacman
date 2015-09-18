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
import java.util.Random;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

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

        // setup dots in the map.
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
        // set up ghost directions
        pinky.setMoveOrder(Ghost.RIGHT,Ghost.DOWN,Ghost.LEFT,Ghost.UP);
        blinky.setMoveOrder(Ghost.LEFT,Ghost.UP,Ghost.RIGHT,Ghost.DOWN);
        inky.setMoveOrder(Ghost.DOWN,Ghost.RIGHT,Ghost.UP,Ghost.LEFT);
        clyde.setMoveOrder(Ghost.UP,Ghost.DOWN,Ghost.LEFT,Ghost.RIGHT);

        // finish with other settings...
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
        pinky.move(this.m);
        // Move Blinky (left,up,right,down)
        blinky.move(this.m);
        // Move Inky (random direction)
        inky.moveRandom(this.m);
        // Move Clyde (shortest distance to pacman (BFS))
        clyde.moveToPacman(this.m, pacman.x, pacman.y);
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
    static public int getDir(int [] arr, int d, int x, int y) {
        int ret = -1;
        switch (d) {
            case Ghost.RIGHT:
                ret = getRight(arr,x,y);
                break;
            case Ghost.DOWN:
                ret = getDown(arr,x,y);
                break;
            case Ghost.LEFT:
                ret = getLeft(arr,x,y);
                break;
            case Ghost.UP:
                ret = getUp(arr,x,y);
                break;
        }
        return ret;
    }

    static private int getUp(int[] arr, int x, int y) {
        return arr[MW*((y-1-SY)/30) + x/30];
    }
    static private int getDown(int[] arr, int x, int y) {
        return arr[MW*((y+30-SY)/30) + x/30];
    }
    static private int getRight(int[] arr, int x, int y) {
        return arr[MW*((y-SY)/30) + (x+30)/30];
    }
    static private int getLeft(int[] arr, int x, int y) {
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
    public Integer[] moveOrder = {0, 1, 2, 3};
    public Stack<Integer> stack = new Stack<Integer>();
    public int[] visited = new int[PacmanCanvas.m.length];
    private Random rand = new Random(System.currentTimeMillis());

    public Ghost(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void setMoveOrder(int fir, int sec, int thi, int fou) {
        moveOrder[0] = fir;
        moveOrder[1] = sec;
        moveOrder[2] = thi;
        moveOrder[3] = fou;
    }

    private int getOpositeDir(int d) {
        // currently four directions are
        // supported.
        if (d < 0 || d > 3) {
            return -1;
        }
        if (d % 2 == 0) {
            return d+1;
        } else {
            return d-1;
        }
    }

    private void randomizeMoveOrder() {
        List<Integer> rList = new ArrayList<Integer>(Arrays.asList(moveOrder));
        for (int i = moveOrder.length; i > 0; i--) {
            moveOrder[i-1] = rList.remove(rand.nextInt(i));
        }
    }

    public void moveRandom(int[] map) {
        randomizeMoveOrder();
        move(map);
    }

    public void move(int[] map) {
        if (this.x % 30 == 0 && this.y % 30 == 0) { // decide next direction

            // mark this node as visited.
            this.visited[PacmanCanvas.MW*((this.y-PacmanCanvas.SY)/30) + this.x/30] = 1;

            // get visitation/availability info.
            int[] ga = new int[4];
            int[] gv = new int[4];
            for (int i = 0; i < 4; i++) {
                // get availability infonmation (occupied/unoccupied)
                ga[i] = PacmanCanvas.getDir(map,this.moveOrder[i],this.x,this.y);
                // Get visitation info
                gv[i] = PacmanCanvas.getDir(this.visited,this.moveOrder[i],this.x,this.y);
            }

            //System.out.printf("gaUp: %3d, gaDown: %3d, gaLeft: %3d, gaRight: %3d\n", gaUp, gaDown, gaLeft, gaRight);
            //System.out.printf("gvUp: %3d, gvDown: %3d, gvLeft: %3d, gvRight: %3d\n", gvUp, gvDown, gvLeft, gvRight);
            /*
            String d = "";
            if (this.direction == this.UP) { d = "UP";}
            if (this.direction == this.DOWN) { d = "DOWN";}
            if (this.direction == this.RIGHT) { d = "RIGHT";}
            if (this.direction == this.LEFT) { d = "LEFT";}
            System.out.println("Direction: " + d);
            */

            // choose a direction
            if (ga[0] == 0 && gv[0] == 0) {
                this.direction = this.moveOrder[0];
                this.stack.push(getOpositeDir(this.moveOrder[0]));
            } else if (ga[1] == 0 && gv[1] == 0) {
                this.direction = this.moveOrder[1];
                this.stack.push(getOpositeDir(this.moveOrder[1]));
            } else if (ga[2] == 0 && gv[2] == 0) {
                this.direction = this.moveOrder[2];
                this.stack.push(getOpositeDir(this.moveOrder[2]));
            } else if (ga[3] == 0 && gv[3] == 0) {
                this.direction = this.moveOrder[3];
                this.stack.push(getOpositeDir(this.moveOrder[3]));
            } else if (!this.stack.empty()) {
                // will keep running until empty stack
                // exception.
                this.direction = this.stack.pop();
            } else {
                // reset all nodes visited so we can traverse
                // the maze again. :)
                for (int i = 0; i < this.visited.length; i++) {
                    this.visited[i] = 0;
                }
                // reset the direction so that we don't move
                // this turn. :)
                this.direction = -1;
            }
        }

        updatePosition();
    }
    
    private void updatePosition() {
        // move this 
        if (this.direction == this.RIGHT) {
            this.x += 1;
        } else if (this.direction == this.LEFT) {
            this.x -= 1;
        } else if (this.direction == this.UP) {
            this.y -= 1;
        } else if (this.direction == this.DOWN) {
            this.y += 1;
        }
    }

    private int findShortestPath(int[] map, int x, int y) {
        Deque<Integer> queue = new LinkedList<Integer>();
        int finalNode = PacmanCanvas.MW*((y-PacmanCanvas.SY)/30) + x/30;
        int startNode = PacmanCanvas.MW*((this.y-PacmanCanvas.SY)/30) + this.x/30;
        int node = 0, up = 0, down = 0, left = 0, right = 0;

        // Initialize the visited list
        for (int i = 0; i < this.visited.length; i++) {
            this.visited[i] = 0;
        }
        // Add initial node to the queue
        queue.add(startNode);

        // traverse the graph.
        while (!queue.isEmpty()) {
            // pick current node.
            node = queue.remove();

            // Exit if we found our path!
            // In this scenario with
            // ghosts and pacman, we
            // should always find a path.
            if (node == finalNode) {
                break;
            }

            // Decide if a node is valid for the 4
            // cardinal directions and add all valid
            // adjacent nodes to the queue
            up = node - PacmanCanvas.MW;
            down = node + PacmanCanvas.MW;
            right = node + 1;
            left = node - 1;
            if (up > 0 && map[up] == 0 && visited[up] == 0) {
                this.visited[up] = node;
                queue.add(up);
            }
            if (down < map.length && map[down] == 0 && visited[down] == 0) {
                this.visited[down] = node;
                queue.add(down);
            }
            boolean leftValid = (left > 0) &&
                                (left % PacmanCanvas.MW != PacmanCanvas.MW-1);
            if (leftValid && map[left] == 0 && visited[left] == 0) {
                this.visited[left] = node;
                queue.add(left);
            }
            boolean rightValid = (right < map.length) &&
                                 (right % PacmanCanvas.MW != 0);
            if (rightValid && map[right] == 0 && visited[right] == 0) {
                this.visited[right] = node;
                queue.add(right);
            }
        }
        
        // If we didn't find a path.
        // (Shouldn't happen...)
        // then return LEFT.
        // Worst case the ghost
        // goes round in circles.
        int ret = this.LEFT;
        if (!queue.isEmpty()) {
            // backtrack to find the node
            // leading to the shortest
            // path.
            int n = node;
            while (visited[n] != startNode) {
                n = visited[n];
            }
            // Find out which direction that node
            // is relative to the start position.
            if (n == startNode - PacmanCanvas.MW) {
                ret = this.UP;
            } else if (n == startNode + PacmanCanvas.MW) {
                ret = this.DOWN;
            } else if (n == startNode-1) {
                ret = this.LEFT;
            } else if (n == startNode+1) {
                ret = this.RIGHT;
            } 
        }
        return ret;
    }

    public void moveToPacman(int[] map, int x, int y) {
        this.direction = findShortestPath(map,x,y);
        updatePosition();
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
