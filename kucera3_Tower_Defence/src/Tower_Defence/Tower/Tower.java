package Tower_Defence.Tower;

import Tower_Defence.Entity;
import Tower_Defence.Grid;
import Tower_Defence.GameBalanceManager;
import Tower_Defence.Enemy.Enemy;

import java.util.ArrayList;

public class Tower extends Entity {

    private double range;
    private int level;
    private double damage;
    private int upgradeCost;
    private Type type;
    private String imagePath;
    protected Enemy target;

    // 🔹 Main Constructor
    public Tower(String name, int positionY, int positionX,
                 double range, double damage, int upgradeCost,
                 Type type, String imagePath) {
        super(name, positionY, positionX);
        this.level = 1;
        this.range = range;
        this.damage = damage;
        this.upgradeCost = upgradeCost;
        this.type = type;
        this.imagePath = imagePath;
    }

    // 🔹 Simple Constructor (Defaults upgrade cost = 100, type = ATTACKER)
    public Tower(String name, int y, int x, double damage, double range) {
        this(name, y, x, range, damage, 100, Type.ATTACKER, null);
    }

    // 🔹 Get all enemies in range
    public ArrayList<Entity> getEntitiesInRange(Grid grid) {
        ArrayList<Entity> entitiesInRange = new ArrayList<>();
        double towerCenterX = getPositionX() + 0.5;
        double towerCenterY = getPositionY() + 0.5;

        for (Entity e : grid.getAllEntities()) {
            if (e instanceof Enemy enemy && enemy.isAlive()) {
                double enemyCenterX = enemy.getPosX() + 0.5;
                double enemyCenterY = enemy.getPosY() + 0.5;
                double dx = enemyCenterX - towerCenterX;
                double dy = enemyCenterY - towerCenterY;
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance <= range) {
                    entitiesInRange.add(enemy);
                }
            }
        }
        return entitiesInRange;
    }

    @Override
    public void onShotArrival() {
        super.onShotArrival();
        takeDamage(1); // Tower takes minor damage when shot
    }

    // 🔹 Upgrade Logic
    public boolean upgrade() {
        if (!GameBalanceManager.hasEnoughBalance(upgradeCost)) return false;
        GameBalanceManager.spendBalance(upgradeCost);
        level++;
        damage *= 1.1; // Increase damage by 10%
        upgradeCost = (int) Math.ceil(upgradeCost * 1.5); // Scale cost
        return true;
    }

    // 🔹 Getters
    public int getLevel() { return level; }
    public double getDamage() { return damage; }
    public double getRange() { return range; }
    public int getUpgradeCost() { return upgradeCost; }
    public Type getType() { return type; }
    public String getImagePath() { return imagePath; }
    public Enemy getTarget() { return target; }

    // 🔹 Setters
    public void setLevel(int level) { this.level = level; }
    public void setDamage(double damage) { this.damage = damage; }
    public void setRange(double range) { this.range = range; }
    public void setUpgradeCost(int upgradeCost) { this.upgradeCost = upgradeCost; }
    public void setType(Type type) { this.type = type; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setTarget(Enemy target) { this.target = target; }

    @Override
    public void doAction(Grid grid) {
        // Default tower does nothing (override in subclasses)
    }
}
