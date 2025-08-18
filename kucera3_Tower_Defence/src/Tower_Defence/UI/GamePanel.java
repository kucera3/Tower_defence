package Tower_Defence.UI;

import Tower_Defence.Block;
import Tower_Defence.Entity;
import Tower_Defence.Grid;
import Tower_Defence.Tower.Tower;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {

    private Grid grid;
    public static final int BLOCK_SIZE = 100;

    public GamePanel() {
        this.grid = new Grid();
        setPreferredSize(new Dimension(5 * BLOCK_SIZE, 6 * BLOCK_SIZE));
        setBackground(Color.BLACK);

        //  mouse listener for placing/selecting towers
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / BLOCK_SIZE;
                int row = e.getY() / BLOCK_SIZE;

                Block clickedBlock = grid.getBlock(row, col);
                if (clickedBlock != null) {
                    if (!clickedBlock.hasTower()) {
                    } else {

                    }
                }

                repaint();
            }
        });

        startGameLoop();
    }

    private void startGameLoop() {
        Timer timer = new Timer(1000, e -> {
            for (Entity entity : grid.getAllEntities()) {
                entity.doAction(grid);
            }
            repaint();
        });
        timer.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int gridWidth = 5 * BLOCK_SIZE;
        int gridHeight = 6 * BLOCK_SIZE;

        // Center horizontally
        int xOffset = (getWidth() - gridWidth) / 2;

        // Align to bottom
        int yOffset = getHeight() - gridHeight;

        // Draw grid blocks
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                int px = xOffset + x * BLOCK_SIZE;
                int py = yOffset + y * BLOCK_SIZE;
                g.setColor(Color.GRAY);
                g.drawRect(px, py, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        // Draw entities (towers, enemies)
        for (Entity e : grid.getAllEntities()) {
            int px = xOffset + e.getPositionX() * BLOCK_SIZE;
            int py = yOffset + e.getPositionY() * BLOCK_SIZE;
            g.setColor(Color.RED); // you can change color per type
            g.fillOval(px + 10, py + 10, BLOCK_SIZE - 20, BLOCK_SIZE - 20);
            g.setColor(Color.WHITE);
            g.drawString(e.getName(), px + 15, py + 40);
        }
    }


}


