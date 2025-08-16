package Tower_Defence.UI;

import javax.swing.*;
import java.awt.*;

public class MenuWindow {

    private JFrame frame;

    public MenuWindow() {
        // Create the main frame
        frame = new JFrame("Tower Defence");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // reasonable size
        frame.setLocationRelativeTo(null); // center on screen

        // Create a panel with vertical layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue()); // spacing at top

        // Create buttons
        JButton playButton = new JButton("Play");
        JButton upgradeButton = new JButton("Upgrade");

        // Center buttons
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add actions
        playButton.addActionListener(e -> openGameWindow());
        upgradeButton.addActionListener(e -> openUpgradeWindow());

        // Add buttons with spacing
        panel.add(playButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(upgradeButton);
        panel.add(Box.createVerticalGlue()); // spacing at bottom

        frame.add(panel);
        frame.setVisible(true);
    }

    private void openGameWindow() {
        GamePanel gamePanel = new GamePanel();
        new GameWindow(gamePanel);
        frame.dispose(); // close menu
    }

    private void openUpgradeWindow() {
        new UpgradeWindow(); // pass current frame for "back" button
        frame.dispose(); // close menu
    }
}




