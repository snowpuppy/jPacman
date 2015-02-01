import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {

    Image maze = Toolkit.getDefaultToolkit().getImage("../images/maze.png");
    Image pacman = Toolkit.getDefaultToolkit().getImage("../images/pacman.png");
    Ghost blinky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/blinky.png"), 330, 270);
    Ghost pinky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/pinky.png"), 330, 300);
    Ghost inky = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/inky.png"), 360, 270);
    Ghost clyde = new Ghost(Toolkit.getDefaultToolkit().getImage("../images/clyde.png"), 360, 300);
    Image dot = Toolkit.getDefaultToolkit().getImage("../images/dot.png");

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(maze, 0, 0, this);
        g2.drawImage(pacman, 0, 30, this);
        g2.drawImage(pacman, 60,30, 60+30, 60, 0, 0, 30, 30, this);
        g2.drawImage(blinky.image, blinky.x, blinky.y, this);
        g2.drawImage(pinky.image, pinky.x, pinky.y, this);
        g2.drawImage(inky.image, inky.x, inky.y, this);
        g2.drawImage(clyde.image, clyde.x, clyde.y, this);
        g2.drawImage(dot, 0, 170, this);
        g2.finalize();
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
