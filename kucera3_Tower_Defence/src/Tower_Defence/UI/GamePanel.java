package Tower_Defence.UI;

import Tower_Defence.*;
import Tower_Defence.Enemy.*;
import Tower_Defence.Enemy.WaveManager;
import Tower_Defence.Tower.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;


public class GamePanel extends JPanel {

    private final Grid grid;
    public static final int BLOCK_SIZE = 100;
    private Tower selectedTower;
    private final List<JButton> towerButtons = new ArrayList<>();

    private final Map<String, Image> towerImages = new HashMap<>();
    private final Map<String, Image> enemyImages = new HashMap<>();
    private final OverlayPanel overlayPanel;
    private Image backgroundImage;
    private final java.util.List<JButton> tempButtons = new ArrayList<>();
    private final List<JLabel> towerLevelLabels = new ArrayList<>();

    private boolean replaceModeActive = false;
    private int nextTowerCost = 100; // first tower costs $100


    public GamePanel(OverlayPanel overlayPanel) {
        this.overlayPanel = overlayPanel;
        this.grid = Grid.getInstance();

        setPreferredSize(new Dimension(grid.getCols() * BLOCK_SIZE, grid.getRows() * BLOCK_SIZE));
        setBackground(Color.BLACK);
        backgroundImage = new ImageIcon(getClass().getResource("/background.jpg")).getImage();
        GameBalanceManager.startNewGame();

        loadTowerImages();
        loadEnemyImages();


        setLayout(null);
        placeDefaultSwordsman();

        // Mouse listener for clicking towers
        // inside your GamePanel constructor
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int xOffset = (getWidth() - grid.getCols() * BLOCK_SIZE) / 2;
                int yOffset = (getHeight() - grid.getRows() * BLOCK_SIZE);

                for (Entity entity : grid.getAllEntities()) {
                    if (!(entity instanceof Tower tower)) continue;

                    int towerSize = BLOCK_SIZE / 2;
                    int towerX = xOffset + tower.getPositionX() * BLOCK_SIZE + (BLOCK_SIZE - towerSize) / 2;
                    int towerY = yOffset + tower.getPositionY() * BLOCK_SIZE + (BLOCK_SIZE - towerSize) / 2;

                    // Check if clicked on tower
                    if (new Rectangle(towerX, towerY, towerSize, towerSize).contains(mouseX, mouseY)) {
                        replaceTower(tower); // show overlay buttons
                        return;
                    }
                }
            }
        });


        // Buy Tower button (placed with setBounds)
        JButton buyTowerButton = new JButton("Buy New Tower (Cost: " + nextTowerCost + ")");
        buyTowerButton.setBounds(10, 10, 200, 30);
        buyTowerButton.addActionListener(e -> {
            if (GameBalanceManager.getBalance() >= nextTowerCost) {
                if (placeRandomTowerOnEmptyBlock()) {
                    GameBalanceManager.spendBalance(nextTowerCost);

                    // Increase cost by 1.5x for the next tower
                    nextTowerCost = (int) Math.ceil(nextTowerCost * 1.5);

                    // Update button text
                    buyTowerButton.setText("Buy New Tower (Cost: " + nextTowerCost + ")");
                } else {
                    JOptionPane.showMessageDialog(this, "No empty spaces left!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Not enough money!");
            }
            repaint();
            refreshTowerButtons();
        });

        add(buyTowerButton);

        // Make sure tower buttons update on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                refreshTowerButtons();
            }
        });

        startGameLoop();
        // create initial tower buttons
        SwingUtilities.invokeLater(this::refreshTowerButtons);
    }
    private void placeDefaultSwordsman() {
        // Choose a starting position (row, col). Adjust as needed!
        int startRow = 3;
        int startCol = 3;

        Block block = grid.getBlock(startRow, startCol);
        if (block != null && !block.hasEntityOfType(Tower.class)) {
            Tower swordsman = new Swordsman("Swordsman", startRow, startCol);
            block.addEntity(swordsman);
            TowerManager.addTower(swordsman);
        }
    }


    private JButton makeTempButton(String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setBounds(x, y, w, h);
        b.setFont(new Font("Arial", Font.PLAIN, 11));
        b.setFocusable(false);
        return b;
    }
    private void refreshTowerButtons() {
        // remove previous always-visible buttons (you already have towerButtons list)
        for (JButton btn : towerButtons) remove(btn);
        towerButtons.clear();
        for (JButton btn : towerButtons) remove(btn);
        towerButtons.clear();
        for (JLabel lbl : towerLevelLabels) remove(lbl);
        towerLevelLabels.clear();

        if (replaceModeActive) return;

        int gridWidth = grid.getCols() * BLOCK_SIZE;
        int gridHeight = grid.getRows() * BLOCK_SIZE;
        int xOffset = (getWidth() - gridWidth) / 2;
        int yOffset = getHeight() - gridHeight;

        Font smallFont = new Font("Arial", Font.PLAIN, 10);

        for (Entity e : grid.getAllEntities()) {
            if (!(e instanceof Tower tower)) continue;

            int px = xOffset + tower.getPositionX() * BLOCK_SIZE;
            int py = yOffset + tower.getPositionY() * BLOCK_SIZE;

            // Replace (above, centered)
            JButton replaceBtn = new JButton("Replace");
            replaceBtn.setFont(smallFont);
            int btnW = BLOCK_SIZE - 20;
            int btnH = 15;
            replaceBtn.setBounds(px + 10, py + 3, BLOCK_SIZE - 20, 18);
            replaceBtn.addActionListener(ae -> replaceTower(tower));
            add(replaceBtn);
            towerButtons.add(replaceBtn);

            // Tower level (above tower)
            JLabel levelLabel = new JLabel("Lvl " + tower.getLevel(), SwingConstants.CENTER);
            levelLabel.setFont(new Font("Arial", Font.BOLD, 11));
            levelLabel.setBounds(px, py +70, BLOCK_SIZE, 15);
            add(levelLabel);
            towerLevelLabels.add(levelLabel); // add to label list


// Upgrade button (below tower) with cost
            JButton upgradeBtn = new JButton("Upgrade (" + tower.getUpgradeCost() + "$)");
            upgradeBtn.setFont(new Font("Arial",Font.PLAIN,8));
            upgradeBtn.setBounds(px , py + BLOCK_SIZE - 20, BLOCK_SIZE, 18);
            upgradeBtn.addActionListener(ae -> {
                if (tower.upgrade()) {
                    refreshTowerButtons(); // update button text and level label
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough money!");
                }
            });
            add(upgradeBtn);
            towerButtons.add(upgradeBtn);

        }

        revalidate();
        repaint();
    }


    private void replaceTower(Tower selectedTower) {
        // Remove previous temporary buttons
        exitReplaceMode();
        this.selectedTower = selectedTower;
        replaceModeActive = true;
        for (JButton btn : towerButtons) {
            if ("Replace".equals(btn.getText())) {
                btn.setVisible(false);
            }
        }



        int gridWidth = grid.getCols() * BLOCK_SIZE;
        int gridHeight = grid.getRows() * BLOCK_SIZE;
        int xOffset = (getWidth() - gridWidth) / 2;
        int yOffset = getHeight() - gridHeight;

        int btnWidth = BLOCK_SIZE - 20;
        int btnHeight = 18;
        Font smallFont = new Font("Arial", Font.PLAIN, 11);

        // Iterate over all blocks
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                // Skip enemy path: top row and left/right columns
                if (row == 0 || col == 0 || col == grid.getCols() - 1) continue;

                Block block = grid.getBlock(row, col);
                int px = xOffset + col * BLOCK_SIZE + 10;
                int py = yOffset + row * BLOCK_SIZE + 10;

                if (block.hasEntityOfType(Tower.class)) {
                    // Occupied -> "Switch" button
                    Tower otherTower = block.getEntities().stream()
                            .filter(ent -> ent instanceof Tower)
                            .map(ent -> (Tower) ent)
                            .findFirst()
                            .orElse(null);

                    if (otherTower != null && otherTower != selectedTower) {
                        JButton switchBtn = makeTempButton("Switch", px, py, btnWidth, btnHeight);
                        switchBtn.setFont(smallFont);
                        switchBtn.addActionListener(ev -> {
                            swapTowers(selectedTower, otherTower);
                            exitReplaceMode();
                            refreshTowerButtons();
                            repaint();
                        });
                        add(switchBtn);
                        tempButtons.add(switchBtn);
                    }

                } else {
                    // Empty -> "Place" button
                    JButton placeBtn = makeTempButton("Place", px, py, btnWidth, btnHeight);
                    placeBtn.setFont(smallFont);
                    final int targetX = col, targetY = row;
                    placeBtn.addActionListener(ev -> {
                        moveTowerTo(selectedTower, targetX, targetY);
                        exitReplaceMode();
                        refreshTowerButtons();
                        repaint();
                    });
                    add(placeBtn);
                    tempButtons.add(placeBtn);
                }
            }
        }
        revalidate();
        repaint();
    }

    private void swapTowers(Tower t1, Tower t2) {
        int x1 = t1.getPositionX(), y1 = t1.getPositionY();
        int x2 = t2.getPositionX(), y2 = t2.getPositionY();

        Block b1 = grid.getBlock(y1, x1);
        Block b2 = grid.getBlock(y2, x2);

        if (b1 != null) b1.removeEntity(t1);
        if (b2 != null) b2.removeEntity(t2);

        if (b1 != null) b1.addEntity(t2);
        if (b2 != null) b2.addEntity(t1);

        t1.setPositionX(x2);
        t1.setPositionY(y2);
        t2.setPositionX(x1);
        t2.setPositionY(y1);
    }

    private void moveTowerTo(Tower tower, int col, int row) {
        Block oldBlock = grid.getBlock(tower.getPositionY(), tower.getPositionX());
        if (oldBlock != null) oldBlock.removeEntity(tower);

        Block newBlock = grid.getBlock(row, col);
        if (newBlock != null) newBlock.addEntity(tower);

        tower.setPositionX(col);
        tower.setPositionY(row);
    }

    private JButton createOverlayButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        // snug inside the grid cell
        btn.setBounds(x + 6, y + 6, BLOCK_SIZE - 12, 24);
        btn.setFont(new Font("Arial", Font.PLAIN, 11));
        btn.setFocusable(false);
        return btn;
    }

    private void exitReplaceMode() {
        replaceModeActive = false;

        // Remove temp buttons
        for (JButton b : new ArrayList<>(tempButtons)) {
            remove(b);
        }
        tempButtons.clear();

        // Restore visibility of Replace buttons
        for (JButton btn : towerButtons) {
            if ("Replace".equals(btn.getText())) {
                btn.setVisible(true);
            }
        }

        selectedTower = null;
        revalidate();
        repaint();
    }




    private void startGameLoop() {
        Timer timer = new Timer(50, e -> {
            boolean gameOver = false;

            // Towers act
            for (Tower tower : TowerManager.getAllTowers()) {
                tower.doAction(grid);
            }

            // Enemies act & remove dead ones
            List<Enemy> enemies = new ArrayList<>(EnemyManager.getAllEnemies());
            for (Enemy enemy : enemies) {
                if (enemy.getHp() <= 0) {
                    Block block = grid.getBlock(enemy.getPositionY(), enemy.getPositionX());
                    if (block != null) block.removeEntity(enemy);
                    EnemyManager.removeEnemy(enemy);
                    GameBalanceManager.addBalance(GameBalanceManager.getEnemyReward());
                    continue;
                }

                enemy.doAction(grid);
                if (enemy.hasReachedEnd()) gameOver = true;
            }

            // Spawn next enemy if it's time
            Enemy spawned = WaveManager.spawnNextEnemy(grid);
            if (spawned != null) EnemyManager.addEnemy(spawned);

            // If wave cleared and no wave in progress, start next wave
            if (WaveManager.isWaveCleared(grid) && !WaveManager.isWaveInProgress()) {
                WaveManager.startNextWave();
            }

            if (gameOver) {
                ((Timer) e.getSource()).stop();
                resetGame();
                SwingUtilities.invokeLater(() -> new EndScreenWindow((JFrame) SwingUtilities.getWindowAncestor(this)));
            }

            repaint();
            refreshTowerButtons();
        });
        timer.start();
    }


    public void resetGame() {
        EnemyManager.reset();
        TowerManager.reset();
        Grid.getInstance().reset();
        GameBalanceManager.reset();
        WaveManager.setCurrentWave(0);
        repaint();
        refreshTowerButtons();
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
        ArrayList<Block> emptyBlocks = new ArrayList<>();
        for (int y = 1; y < grid.getRows(); y++) {
            for (int x = 1; x < grid.getCols() - 1; x++) {
                Block block = grid.getBlock(y, x);
                if (!block.hasEntityOfType(Tower.class)) emptyBlocks.add(block);
            }
        }
        if (emptyBlocks.isEmpty()) return false;

        Block chosen = emptyBlocks.get(rand.nextInt(emptyBlocks.size()));
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

        if (backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        // Draw grid
        for (int y = 0; y < grid.getRows(); y++) {
            for (int x = 0; x < grid.getCols(); x++) {
                g.setColor(Color.GRAY);
                g.drawRect(xOffset + x * BLOCK_SIZE, yOffset + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        // Draw entities
        for (Entity e : grid.getAllEntities()) {
            // Skip dead enemies
            if (e instanceof Enemy enemy && enemy.getHp() <= 0) continue;

            int px = xOffset + e.getPositionX() * BLOCK_SIZE;
            int py = yOffset + e.getPositionY() * BLOCK_SIZE;

            if (e instanceof Tower) {
                int size = BLOCK_SIZE / 2;
                int towerX = px + (BLOCK_SIZE - size) / 2;
                int towerY = py + (BLOCK_SIZE - size) / 2;
                Image img = towerImages.getOrDefault(e.getName(), null);
                if (img != null) g.drawImage(img, towerX, towerY, size, size, null);
                else { g.setColor(Color.BLUE); g.fillOval(towerX, towerY, size, size); }
            } else if (e instanceof Enemy enemy) {
                int size = BLOCK_SIZE / 2;
                int enemyX = enemy.getPixelX(xOffset) + (BLOCK_SIZE - size) / 2;
                int enemyY = enemy.getPixelY(yOffset) + (BLOCK_SIZE - size) / 2;
                Image img = enemyImages.getOrDefault(e.getName(), null);
                if (img != null) g.drawImage(img, enemyX, enemyY, size, size, null);
                else { g.setColor(Color.RED); g.fillOval(enemyX, enemyY, size, size); }
            }
        }

        // Money & Wave
        g.setColor(Color.YELLOW);
        g.drawString("Money: " + GameBalanceManager.getBalance(), 10, 60);
        g.setColor(Color.CYAN);
        g.drawString("Wave: " + WaveManager.getCurrentWave(), 10, 80);
    }
}

