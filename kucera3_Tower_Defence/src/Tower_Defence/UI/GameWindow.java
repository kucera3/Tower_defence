package Tower_Defence.UI;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final OverlayPanel overlayPanel;
    private final GamePanel gamePanel;

    public GameWindow(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // Window setup
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create overlay and attach as GlassPane
        overlayPanel = new OverlayPanel();
        setGlassPane(overlayPanel);
        overlayPanel.setVisible(false);
        overlayPanel.setBounds(0, 0, getWidth(), getHeight());
        overlayPanel.setLayout(null); // absolute positioning
        overlayPanel.setOpaque(false);

        // Match window size to screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gamePanel.setPreferredSize(screenSize);

        // Add main game panel
        add(gamePanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null); // Center window
        setVisible(true);
    }

    public OverlayPanel getOverlayPanel() {
        return overlayPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}




