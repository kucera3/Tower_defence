package Tower_Defence.UI;

import Tower_Defence.Tower.*;
import Tower_Defence.Tower.Tower;

import javax.swing.*;
import java.awt.*;

public class MenuWindow {

    private JFrame frame;

    public MenuWindow() {
        // Create the main frame
        frame = new JFrame("Tower Defence");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center on screen

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth());
        int height = (int) (screenSize.getHeight());
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // recenter



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

        //actions
        playButton.addActionListener(e -> openGameWindow());
        upgradeButton.addActionListener(e -> openUpgradeWindow());


        //buttons with spacing
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
        Tower[] towers = {
                new Archer(),
                new Bomber(),
                new Buffer(),
                new Swordsman()
        };
        new UpgradeWindow(towers);
    }

}




