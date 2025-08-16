package Tower_Defence.UI;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel gamePanel) {
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(gamePanel);
        pack(); // Resize window to fit panel's preferred size
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }
}

