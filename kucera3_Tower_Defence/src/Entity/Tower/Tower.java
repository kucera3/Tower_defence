package Entity.Tower;

import Entity.*;
import Entity.Enemy.Enemy;
import java.util.ArrayList;


public class Tower extends Entity{


    private int range;
    private int damage;
    private int upgradeCost;
    private Type type;

    public Tower(String name, int positionY, int positionX, int range, int damage, int upgradeCost, Type type) {
        super(name, positionY, positionX);
        this.range = range;
        this.damage = damage;
        this.upgradeCost = upgradeCost;
        this.type = type;
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

    public void takeDamage(int damage) {
        this.damage -= damage;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public Type getType() {
        return type;
    }


    public void setRange(int range) {
        this.range = range;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public void setType(Type type) {
        this.type = type;
    }
}


