package Tower_Defence.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreenWindow extends JFrame {

    public EndScreenWindow(JFrame gameWindow) { // pass the GamePanel JFrame
        setTitle("Game Over");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Game Over", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

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
        add(label, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}


