import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {

    Image maze = Toolkit.getDefaultToolkit().getImage("../images/maze.png");
    Image pacman = Toolkit.getDefaultToolkit().getImage("../images/pacman.png");
    Image blinky = Toolkit.getDefaultToolkit().getImage("../images/blinky.png");
    Image pinky = Toolkit.getDefaultToolkit().getImage("../images/pinky.png");
    Image inky = Toolkit.getDefaultToolkit().getImage("../images/inky.png");
    Image clyde = Toolkit.getDefaultToolkit().getImage("../images/clyde.png");

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(maze, 0, 0, this);
        g2.drawImage(pacman, 0, 30, this);
        g2.drawImage(pacman, 60,30, 60+30, 60, 0, 0, 30, 30, this);
        g2.drawImage(blinky, 0, 60, this);
        g2.drawImage(pinky, 0, 90, this);
        g2.drawImage(inky, 0, 120, this);
        g2.drawImage(clyde, 0, 150, this);
        g2.finalize();
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
