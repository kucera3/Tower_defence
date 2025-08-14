import javax.swing.*;
import java.awt.*;
import Entity.*;

public class GamePanel extends JPanel {
    private Grid grid;
    private final int BLOCK_SIZE = 80;

    public GamePanel(Grid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(5 * BLOCK_SIZE, 6 * BLOCK_SIZE));
        setBackground(Color.BLACK);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Entity entity : grid.getAllEntities()) {
                    entity.doAction(grid);
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid blocks
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                int px = x * BLOCK_SIZE;
                int py = y * BLOCK_SIZE;
                g.setColor(Color.GRAY);
                g.drawRect(px, py, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        // Draw entities by their position
        for (Entity e : grid.getAllEntities()) {
            int px = e.getPositionX() * BLOCK_SIZE;
            int py = e.getPositionY() * BLOCK_SIZE;
            g.setColor(Color.RED); // or based on entity type
            g.fillOval(px + 10, py + 10, BLOCK_SIZE - 20, BLOCK_SIZE - 20);
            g.setColor(Color.WHITE);
            g.drawString(e.getName(), px + 15, py + 40);
        }
    }

}


