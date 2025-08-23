package Tower_Defence.UI;

import Tower_Defence.MoneyManager;
import Tower_Defence.Tower.Tower;

import javax.swing.*;
import java.awt.*;

public class UpgradeWindow extends JFrame {

    private JLabel balanceLabel;

    public UpgradeWindow(Tower[] towers) {
        setTitle("Tower Defence - Upgrade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(900, 450);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel: title + balance
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.DARK_GRAY);

        JLabel titleLabel = new JLabel("Upgrade Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        balanceLabel = new JLabel("Balance: $" + MoneyManager.getBalance(), SwingConstants.RIGHT);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(Color.GREEN);
        topPanel.add(balanceLabel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel: towers in a horizontal row
        JPanel towersPanel = new JPanel(new GridLayout(1, towers.length, 20, 0));
        towersPanel.setBackground(Color.DARK_GRAY);

        for (Tower tower : towers) {
            towersPanel.add(createTowerPanel(tower));
        }

        mainPanel.add(towersPanel, BorderLayout.CENTER);

        // Bottom panel: back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.DARK_GRAY);
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            new MenuWindow();
            dispose();
        });
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTowerPanel(Tower tower) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.setPreferredSize(new Dimension(180, 350));

        // Tower Name
        JLabel nameLabel = new JLabel(tower.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tower Image
        String imagePath = "/images/" + getTowerImageFile(tower.getName()); // classpath resource
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(imagePath));
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
            icon = new ImageIcon(); // empty icon fallback
        }
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tower Level
        JLabel levelLabel = new JLabel("Level: " + tower.getLevel(), SwingConstants.CENTER);
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Upgrade Button
        JButton upgradeButton = new JButton("Upgrade ($" + tower.getUpgradeCost() + ")");
        upgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        upgradeButton.setMaximumSize(new Dimension(160, 30));
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

        // Add components with spacing
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

    private String getTowerImageFile(String towerName) {
        return switch (towerName) {
            case "Archer" -> "archers.png";
            case "Bomber" -> "bomber.png";
            case "Buffer" -> "buffer.png";
            case "Swordsman" -> "swordsman.png";
            default -> "default.png";
        };
    }
}
