package Tower_Defence.UI;

import javax.swing.*;
import java.awt.*;

public class UpgradeWindow extends JFrame {

    public UpgradeWindow() {
        setTitle("Tower Defence - Upgrade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Upgrade Menu", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        //button to go back to MenuWindow
        JButton backButton = new JButton("X");
        backButton.addActionListener(e -> {
            new MenuWindow(); // reopen menu
            dispose(); // close upgrade window
        });

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(backButton);
        panel.add(topPanel, BorderLayout.NORTH);

        add(panel);
        setVisible(true);
    }
}
