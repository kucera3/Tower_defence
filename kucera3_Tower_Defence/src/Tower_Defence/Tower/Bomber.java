package Tower_Defence.Tower;
import Tower_Defence.Grid;
import Tower_Defence.Block;
import Tower_Defence.Enemy.*;
import Tower_Defence.Entity;

public class Bomber extends Tower {
    public Bomber(String name, int positionY, int positionX) {
        super(name, positionY, positionX, 2.0, 5, 100, Type.ATTACKER, "/bomber.png");
    }

    @Override
    public boolean upgrade() {
        return super.upgrade(); // damage +10%, cost x1.5, money deducted
    }

    @Override
    public void doAction(Grid grid) {
        //Damage all enemies within main range
        for (Block block : grid.getBlocksInRange(getPositionY(), getPositionX(), getRange())) {
            for (Entity e : block.getEntities()) {
                if (e instanceof Enemy enemy) {
                    enemy.takeDamage(getDamage()); // primary damage
                }
            }
        }

        // Splash damage to immediately neighboring blocks (distance = 1)
        for (Block neighbor : grid.getBlocksInRange(getPositionY(), getPositionX(), 1)) {
            // Skip the tower's own block
            if (neighbor.getRow() == getPositionY() && neighbor.getCol() == getPositionX()) continue;

            for (Entity e : neighbor.getEntities()) {
                if (e instanceof Enemy enemy) {
                    enemy.takeDamage(getDamage()); // splash damage
                }
            }
        }
    }

}

