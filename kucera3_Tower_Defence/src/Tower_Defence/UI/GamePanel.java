package Tower_Defence.UI;

import Tower_Defence.Block;
import Tower_Defence.Entity;
import Tower_Defence.GameBalanceManager;
import Tower_Defence.Grid;
import Tower_Defence.Tower.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

public class GamePanel extends JPanel {

    private final Grid grid;
    public static final int BLOCK_SIZE = 100;
    private Tower selectedTower;
    private final java.util.List<JButton> tempButtons = new ArrayList<>();

    // Tower images cache
    private final HashMap<String, Image> towerImages = new HashMap<>();

    public GamePanel() {
        this.grid = Grid.getInstance();
        setPreferredSize(new Dimension(grid.getCols() * BLOCK_SIZE, grid.getRows() * BLOCK_SIZE));
        setBackground(Color.BLACK);
        GameBalanceManager.startNewGame();

        // Load tower images from resources
        loadTowerImages();

        // Add Buy Tower Button
        JButton buyTowerButton = new JButton("Buy New Tower (Cost: 100)");
        buyTowerButton.addActionListener(e -> {
            int cost = 10;
            if (GameBalanceManager.getBalance() >= cost) {
                if (placeRandomTowerOnEmptyBlock()) {
                    GameBalanceManager.spendBalance(cost);
                } else {
                    JOptionPane.showMessageDialog(this, "No empty spaces left!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Not enough money!");
            }
            repaint();
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int xOffset = (getWidth() - grid.getCols() * BLOCK_SIZE) / 2;
                int yOffset = getHeight() - grid.getRows() * BLOCK_SIZE;

                for (Entity entity : grid.getAllEntities()) {
                    if (!(entity instanceof Tower)) continue;

                    int px = xOffset + entity.getPositionX() * BLOCK_SIZE;
                    int py = yOffset + entity.getPositionY() * BLOCK_SIZE;
                    int towerSize = BLOCK_SIZE / 2;
                    int towerX = px + (BLOCK_SIZE - towerSize) / 2;
                    int towerY = py + (BLOCK_SIZE - towerSize) / 2;

                    // Check Replace button
                    Rectangle replaceRect = new Rectangle(towerX, towerY - 18, towerSize, 15);
                    if (replaceRect.contains(mouseX, mouseY)) {
                        //replaceTower((Tower) entity);
                        repaint();
                        return;
                    }

                    // Check Upgrade button
                    Rectangle upgradeRect = new Rectangle(towerX, towerY + towerSize + 3, towerSize, 15);
                    if (upgradeRect.contains(mouseX, mouseY)) {
                        upgradeTower((Tower) entity);
                        repaint();
                        return;
                    }

                    // Optional: click on tower itself
                    Rectangle towerRect = new Rectangle(towerX, towerY, towerSize, towerSize);
                    if (towerRect.contains(mouseX, mouseY)) {
                        JOptionPane.showMessageDialog(GamePanel.this, entity.getName());
                        return;
                    }
                }
            }
        });


        setLayout(new BorderLayout());
        add(buyTowerButton, BorderLayout.NORTH);

        startGameLoop();
    }
    /*private void replaceTower(Tower tower) {
        selectedTower = tower;

        // Clear any previous temp buttons
        tempButtons.forEach(this::remove);
        tempButtons.clear();

        int gridWidth = grid.getCols() * BLOCK_SIZE;
        int gridHeight = grid.getRows() * BLOCK_SIZE;
        int xOffset = (getWidth() - gridWidth) / 2;
        int yOffset = getHeight() - gridHeight;

        // Only allow inside blocks (exclude enemy path: top row, left col, right col)
        for (int y = 1; y < grid.getRows(); y++) {
            for (int x = 1; x < grid.getCols() - 1; x++) {
                Block block = grid.getBlock(y, x);
                int px = xOffset + x * BLOCK_SIZE;
                int py = yOffset + y * BLOCK_SIZE;

                if (block.hasEntityOfType(Tower.class)) {
                    // Show "Switch" button
                    JButton switchBtn = new JButton("Switch");
                    switchBtn.setBounds(px + 10, py + 10, BLOCK_SIZE - 20, 30);
                    switchBtn.addActionListener(e -> {
                        Tower otherTower = (Tower) block.getEntities().stream()
                                .filter(ent -> ent instanceof Tower)
                                .map(ent -> (Tower) ent)
                                .findFirst()
                                .orElse(null);

                        if (otherTower != null) {
                            // Swap positions
                            int oldX = selectedTower.getPositionX();
                            int oldY = selectedTower.getPositionY();

                            selectedTower.setPositionX(otherTower.getPositionX());
                            selectedTower.setPositionY(otherTower.getPositionY());
                            otherTower.setPositionX(oldX);
                            otherTower.setPositionY(oldY);
                        }

                        exitReplaceMode();
                    });
                    add(switchBtn);
                    tempButtons.add(switchBtn);

                } else {
                    // Show "Place" button
                    JButton placeBtn = new JButton("Place");
                    placeBtn.setBounds(px + 10, py + 10, BLOCK_SIZE - 20, 30);
                    int finalX = x;
                    int finalY = y;
                    placeBtn.addActionListener(e -> {
                        // Remove from old block
                        Block oldBlock = grid.getBlock(selectedTower.getPositionY(), selectedTower.getPositionX());
                        oldBlock.removeEntity(selectedTower);

                        // Place in new block
                        block.addEntity(selectedTower);
                        selectedTower.setPositionX(finalX);
                        selectedTower.setPositionY(finalY);

                        exitReplaceMode();
                    });
                    add(placeBtn);
                    tempButtons.add(placeBtn);
                }
            }
        }

        revalidate();
        repaint();
    }

    // Helper to cleanup and return to normal mode
    private void exitReplaceMode() {
        tempButtons.forEach(this::remove);
        tempButtons.clear();
        selectedTower = null;
        revalidate();
        repaint();
    }*/


    private void upgradeTower(Tower tower) {
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

    private void loadTowerImages() {
        towerImages.put("Swordsman", new ImageIcon(getClass().getResource("/swordsmaningame.png")).getImage());
        towerImages.put("Buffer", new ImageIcon(getClass().getResource("/bufferingame.png")).getImage());
        towerImages.put("Archer", new ImageIcon(getClass().getResource("/archeringame.png")).getImage());
        towerImages.put("Bomber", new ImageIcon(getClass().getResource("/bomberingame.png")).getImage());
    }

    private boolean placeRandomTowerOnEmptyBlock() {
        Random rand = new Random();

        // Find empty blocks
        ArrayList<Block> emptyBlocks = new ArrayList<>();
        for (int y = 1; y < grid.getRows(); y++) {
            for (int x = 1; x < grid.getCols()-1; x++) {
                Block block = grid.getBlock(y, x);
                if (!block.hasEntityOfType(Tower.class)) {
                    emptyBlocks.add(block);
                }
            }
        }

        if (emptyBlocks.isEmpty()) {
            return false;
        }

        // Pick random empty block
        Block chosen = emptyBlocks.get(rand.nextInt(emptyBlocks.size()));

        // Pick random tower type
        String[] towerTypes = {"Swordsman", "Buffer", "Archer", "Bomber"};
        String type = towerTypes[rand.nextInt(towerTypes.length)];

        Tower tower = switch (type) {
            case "Swordsman" -> new Swordsman("Swordsman", chosen.getRow(), chosen.getCol());
            case "Buffer" -> new Buffer("Buffer", chosen.getRow(), chosen.getCol());
            case "Bomber" -> new Bomber("Bomber", chosen.getRow(), chosen.getCol());
            case "Archer" -> new Archer("Archer", chosen.getRow(), chosen.getCol());
            default -> null;
        };

        if (tower != null) {
            chosen.addEntity(tower);
            TowerManager.addTower(tower);
            return true;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int gridWidth = grid.getCols() * BLOCK_SIZE;
        int gridHeight = grid.getRows() * BLOCK_SIZE;

        // Center horizontally
        int xOffset = (getWidth() - gridWidth) / 2;
        // Align to bottom
        int yOffset = getHeight() - gridHeight;

        // Draw grid blocks
        for (int y = 0; y < grid.getRows(); y++) {
            for (int x = 0; x < grid.getCols(); x++) {
                int px = xOffset + x * BLOCK_SIZE;
                int py = yOffset + y * BLOCK_SIZE;
                g.setColor(Color.GRAY);
                g.drawRect(px, py, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        // Draw entities
        for (Entity e : grid.getAllEntities()) {
            int px = xOffset + e.getPositionX() * BLOCK_SIZE;
            int py = yOffset + e.getPositionY() * BLOCK_SIZE;

            if (e instanceof Tower) {
                int towerSize = BLOCK_SIZE / 2;
                int towerX = px + (BLOCK_SIZE - towerSize) / 2;
                int towerY = py + (BLOCK_SIZE - towerSize) / 2;

                // Draw tower image if available
                if (towerImages.containsKey(e.getName())) {
                    g.drawImage(towerImages.get(e.getName()), towerX, towerY, towerSize, towerSize, null);
                } else {
                    g.setColor(Color.BLUE);
                    g.fillOval(towerX, towerY, towerSize, towerSize);
                }

                // Draw buttons
                g.setColor(Color.DARK_GRAY);
                // Replace button above tower
                g.fillRect(towerX, towerY - 18, towerSize, 15);
                g.setColor(Color.WHITE);
                g.drawString("Replace", towerX + 5, towerY - 6);

                // Upgrade button below tower
                g.setColor(Color.DARK_GRAY);
                g.fillRect(towerX, towerY + towerSize + 3, towerSize, 15);
                g.setColor(Color.WHITE);
                g.drawString("Upgrade", towerX + 5, towerY + towerSize + 14);

            }
        }

        // Display current balance
        g.setColor(Color.YELLOW);
        g.drawString("Money: " + GameBalanceManager.getBalance(), 10, 20);
    }

}



