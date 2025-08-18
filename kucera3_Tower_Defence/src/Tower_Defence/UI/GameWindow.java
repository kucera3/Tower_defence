package Tower_Defence.UI;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel gamePanel) {
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        // Set panel preferred size to screen size
        gamePanel.setPreferredSize(new Dimension(width, height));

        add(gamePanel);
        pack(); // Resizes frame to match panel
        setLocationRelativeTo(null);
        setVisible(true);
    }
}


