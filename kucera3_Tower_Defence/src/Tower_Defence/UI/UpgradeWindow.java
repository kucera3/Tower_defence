package Tower_Defence.UI;

import Tower_Defence.MoneyManager;
import Tower_Defence.Tower.*;

import javax.swing.*;
import java.awt.*;

public class UpgradeWindow extends JFrame {

    private JLabel balanceLabel;

    public UpgradeWindow() {
    }

    public UpgradeWindow(Tower[] towers) {
        setTitle("Tower Defence - Upgrade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.5);
        int height = (int) (screenSize.getHeight() * 0.5);
        setSize(width, height);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Upgrade Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Balance
        balanceLabel = new JLabel("Balance: $" + MoneyManager.getBalance(), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(Color.GREEN);
        mainPanel.add(balanceLabel, BorderLayout.SOUTH);

        // Towers Panel
        JPanel towersPanel = new JPanel(new GridLayout(1, towers.length, 20, 10));
        towersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Tower tower : towers) {
            towersPanel.add(createTowerPanel(tower));
        }

        mainPanel.add(towersPanel, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("X");
        backButton.addActionListener(e -> {
            new MenuWindow(); // reopen menu
            dispose(); // close upgrade window
        });
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(backButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);
        setVisible(true);
    }

    //source 50% ChatGPT
    private JPanel createTowerPanel(Tower tower) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.setBackground(Color.DARK_GRAY);

        JLabel nameLabel = new JLabel(tower.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tower image
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(tower.getImagePath()));
        } catch (Exception e) {
            System.err.println("Image not found: " + tower.getImagePath());
        }
        JLabel imageLabel = (icon != null) ?
                new JLabel(new ImageIcon(icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH))) :
                new JLabel("No Image");
        imageLabel.setForeground(Color.WHITE);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel levelLabel = new JLabel("Level: " + tower.getLevel(), SwingConstants.CENTER);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton upgradeButton = new JButton("Upgrade ($" + tower.getUpgradeCost() + ")");
        upgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradeButton.addActionListener(e -> {
            if (MoneyManager.spendMoney(tower.getUpgradeCost())) {
                tower.upgrade();
                levelLabel.setText("Level: " + tower.getLevel());
                balanceLabel.setText("Balance: $" + MoneyManager.getBalance());
                JOptionPane.showMessageDialog(this, tower.getName() + " upgraded!");
            } else {
                JOptionPane.showMessageDialog(this, "Not enough money!");
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(imageLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(levelLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(upgradeButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

}



