package Tower_Defence.UI;

import Tower_Defence.MoneyManager;
import Tower_Defence.Enemy.WaveManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreenWindow extends JFrame {

    public EndScreenWindow(JFrame gameWindow) {
        setTitle("Game Over");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Calculate reward based on the last wave reached
        int lastWave = WaveManager.getCurrentWave(); // get wave at death
        int earnedCoins = MoneyManager.calculateWaveReward(lastWave);
        MoneyManager.addMoney(earnedCoins); // add to balance

        JLabel label = new JLabel("Game Over", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel rewardLabel = new JLabel("Coins Earned: " + earnedCoins, SwingConstants.CENTER);
        rewardLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton closeButton = new JButton("X");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the game window
                gameWindow.dispose();
                // Close this end screen window
                dispose();
                // Open menu window
                new MenuWindow();
            }
        });

        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        add(rewardLabel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}



