package Tower_Defence.Tower;

import Tower_Defence.Block;
import Tower_Defence.Entity;
import Tower_Defence.Grid;
import Tower_Defence.MoneyManager;

import java.util.ArrayList;

public class Tower extends Entity {

    private int range;
    private int level;
    private int damage;
    private int upgradeCost;
    private Type type;
    private String imagePath; // path for UpgradeWindow images

    public Tower(String name, int positionY, int positionX, int range, int damage, int upgradeCost, Type type, String imagePath) {
        super(name, positionY, positionX);
        this.range = range;
        this.damage = damage;
        this.upgradeCost = upgradeCost;
        this.type = type;
        this.level = 1;
        this.imagePath = imagePath;
    }

    public Tower(String name, int positionY, int positionX, int range, int damage, int upgradeCost, Type support) {
        super();
    }

    public ArrayList<Entity> getEntitiesInRange(Grid grid) {
        ArrayList<Entity> entitiesInRange = new ArrayList<>();
        ArrayList<Block> blocksInRange = grid.getBlocksInRange(getPositionY(), getPositionX(), range);

        for (Block block : blocksInRange) {
            entitiesInRange.addAll(block.getEntities());
        }
        return entitiesInRange;
    }

    @Override
    public void onShotArrival() {
        super.onShotArrival();
        takeDamage(1);
    }

    // Upgrade the tower if player has enough money
    public boolean upgrade() {
        if (MoneyManager.spendMoney(upgradeCost)) {
            level++;
            damage += 5;
            upgradeCost += 50;
            return true;
        }
        return false; // not enough money
    }


    public int getLevel() { return level; }
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getUpgradeCost() { return upgradeCost; }
    public Type getType() { return type; }
    public String getImagePath() { return imagePath; } // <-- for UpgradeWindow


    public void setLevel(int level) { this.level = level; }
    public void setDamage(int damage) { this.damage = damage; }
    public void setRange(int range) { this.range = range; }
    public void setUpgradeCost(int upgradeCost) { this.upgradeCost = upgradeCost; }
    public void setType(Type type) { this.type = type; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public void takeDamage(int damage) {
        this.damage -= damage;
    }
}



