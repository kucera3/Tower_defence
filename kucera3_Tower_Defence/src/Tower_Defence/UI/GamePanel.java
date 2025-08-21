package Tower_Defence.UI;

import Tower_Defence.*;
import Tower_Defence.Enemy.*;
import Tower_Defence.Enemy.WaveManager;
import Tower_Defence.Tower.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
// mouselisten suradnice blockou
//grid awt component
//https://chatgpt.com/share/68a6141d-987c-8004-8f4c-c51ba8faa72b
//zjistit lokaciu gridu-mouse listener na kliknutie


public class GamePanel extends JPanel {

    private final Grid grid;
    public static final int BLOCK_SIZE = 100;
    private Tower selectedTower;
    private final java.util.List<JButton> tempButtons = new ArrayList<>();

    // Tower images cache
    private final HashMap<String, Image> towerImages = new HashMap<>();
    private final HashMap<String, Image> enemyImages = new HashMap<>();
    private boolean gameStarted = false;
    private Timer gameTimer;
    private Image backgroundImage;

    public GamePanel() {
        this.grid = Grid.getInstance();
        setPreferredSize(new Dimension(grid.getCols() * BLOCK_SIZE, grid.getRows() * BLOCK_SIZE));
        setBackground(Color.BLACK);
        backgroundImage = new ImageIcon(getClass().getResource("/background.jpg")).getImage();
        GameBalanceManager.startNewGame();

        // Load images from resources
        loadTowerImages();
        loadEnemyImages();
        addMouseListener(new GridClickListener());

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

    public void resetGame() {
        EnemyManager.reset();
        TowerManager.reset();
        Grid.getInstance().reset();
        GameBalanceManager.reset();
        WaveManager.setCurrentWave(0);
        repaint();
    }


    private void startGameLoop() {
        Timer timer = new Timer(200, e -> {
            boolean gameOver = false;
            // Enemy actions
            for (Enemy enemy : EnemyManager.getAllEnemies()) {
                enemy.doAction(grid);
                if (enemy.hasReachedEnd()) {
                    gameOver = true;
                }
            }

            // Spawn enemies gradually
            Enemy newEnemy = WaveManager.spawnNextEnemy(grid);
            if (newEnemy != null) EnemyManager.addEnemy(newEnemy);

            // Start next wave if current cleared
            if (WaveManager.isWaveCleared(grid)) {
                WaveManager.startNextWave();
            }
            if (gameOver) {
                ((Timer)e.getSource()).stop();


                resetGame();

                SwingUtilities.invokeLater(() -> new EndScreenWindow((JFrame) SwingUtilities.getWindowAncestor(this)));
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
    private void loadEnemyImages() {
        enemyImages.put("SmallEnemy", new ImageIcon(getClass().getResource("/smallenemy.png")).getImage());
        enemyImages.put("MiddleEnemy", new ImageIcon(getClass().getResource("/middleenemy.png")).getImage());
        enemyImages.put("BigEnemy", new ImageIcon(getClass().getResource("/bigenemy.png")).getImage());
        enemyImages.put("Boss", new ImageIcon(getClass().getResource("/boss.png")).getImage());
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
        int xOffset = (getWidth() - gridWidth) / 2;
        int yOffset = getHeight() - gridHeight;
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Draw grid
        for (int y = 0; y < grid.getRows(); y++) {
            for (int x = 0; x < grid.getCols(); x++) {
                g.setColor(Color.GRAY);
                g.drawRect(xOffset + x * BLOCK_SIZE, yOffset + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        // Draw entities
        for (Entity e : grid.getAllEntities()) {
            int px = xOffset + e.getPositionX() * BLOCK_SIZE;
            int py = yOffset + e.getPositionY() * BLOCK_SIZE;

            if (e instanceof Tower) {
                int size = BLOCK_SIZE / 2;
                int towerX = px + (BLOCK_SIZE - size) / 2;
                int towerY = py + (BLOCK_SIZE - size) / 2;
                Image img = towerImages.getOrDefault(e.getName(), null);
                if (img != null) g.drawImage(img, towerX, towerY, size, size, null);
                else { g.setColor(Color.BLUE); g.fillOval(towerX, towerY, size, size); }

                // Buttons
                g.setColor(Color.DARK_GRAY);
                g.fillRect(towerX, towerY - 18, size, 15);
                g.setColor(Color.WHITE); g.drawString("Replace", towerX + 5, towerY - 6);

                g.setColor(Color.DARK_GRAY);
                g.fillRect(towerX, towerY + size + 3, size, 15);
                g.setColor(Color.WHITE); g.drawString("Upgrade", towerX + 5, towerY + size + 14);
            } else { // Draw enemies
                int size = BLOCK_SIZE / 2;
                int enemyX = ((Enemy) e).getPixelX(xOffset) + (BLOCK_SIZE - size) / 2;
                int enemyY = ((Enemy) e).getPixelY(yOffset) + (BLOCK_SIZE - size) / 2;
                Image img = enemyImages.getOrDefault(e.getName(), null);
                if (img != null) g.drawImage(img, enemyX, enemyY, size, size, null);
                else { g.setColor(Color.RED); g.fillOval(enemyX, enemyY, size, size); }
            }
        }

        // Display money
        g.setColor(Color.YELLOW);
        g.drawString("Money: " + GameBalanceManager.getBalance(), 10, 20);

        // Display current wave
        g.setColor(Color.CYAN);
        g.drawString("Wave: " + WaveManager.getCurrentWave(), 10, 40);
    }

    private class GridClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            int xOffset = (getWidth() - grid.getCols() * BLOCK_SIZE) / 2;
            int yOffset = getHeight() - grid.getRows() * BLOCK_SIZE;

            for (Entity entity : grid.getAllEntities()) {
                if (!(entity instanceof Tower)) continue;

                int towerSize = BLOCK_SIZE / 2;
                int towerX = xOffset + entity.getPositionX() * BLOCK_SIZE + (BLOCK_SIZE - towerSize) / 2;
                int towerY = yOffset + entity.getPositionY() * BLOCK_SIZE + (BLOCK_SIZE - towerSize) / 2;

                // Replace button
                if (new Rectangle(towerX, towerY - 18, towerSize, 15).contains(mouseX, mouseY)) {
                    repaint(); return;
                }

                // Upgrade button
                if (new Rectangle(towerX, towerY + towerSize + 3, towerSize, 15).contains(mouseX, mouseY)) {
                    // upgradeTower((Tower) entity);
                    repaint(); return;
                }

                // Click on tower itself
                if (new Rectangle(towerX, towerY, towerSize, towerSize).contains(mouseX, mouseY)) {
                    JOptionPane.showMessageDialog(GamePanel.this, entity.getName());
                    return;
                }
            }
        }
    }

}



