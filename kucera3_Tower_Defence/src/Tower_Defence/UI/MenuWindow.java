package Tower_Defence.UI;
import javax.swing.*;
import java.awt.*;

public class MenuWindow {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tower Defence");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(3000, 2000);
        frame.setLocationRelativeTo(null); // Center the window on screen

        // Create a panel with a vertical box layout to hold buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add some vertical spacing before the buttons
        panel.add(Box.createVerticalGlue());

        // Create buttons
        JButton playButton = new JButton("Play");
        JButton upgradeButton = new JButton("Upgrade");

        // Center the buttons
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons with spacing
        panel.add(playButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // spacing
        panel.add(upgradeButton);

        // Add some vertical spacing after the buttons
        panel.add(Box.createVerticalGlue());

        // Add panel to frame
        frame.add(panel);

        // Make frame visible
        frame.setVisible(true);
    }
}

