package Tower_Defence.Tower;
import Tower_Defence.Grid;
import Tower_Defence.Entity;
import Tower_Defence.Enemy.*;


public class Swordsman extends Tower {
    public Swordsman(String name, int positionY, int positionX) {
        super(name, positionY, positionX, 1.6, 20, 100, Type.ATTACKER, "/swordsman.png");
    }


    @Override
    public boolean upgrade() {
        return super.upgrade(); // damage +10%, cost x1.5, money deducted
    }

    @Override
    public void doAction(Grid grid) {
        // Check if current target is valid
        if (target == null || target.getHp() <= 0) {
            target = null; // reset
            // Find first enemy in range
            for (Entity e : getEntitiesInRange(grid)) {
                if (e instanceof Enemy enemy && enemy.getHp() > 0) {
                    target = enemy;
                    break;
                }
            }
        }

        // Attack target
        if (target != null) {
            target.takeDamage(getDamage());
            // For Bomber, apply splash to neighbors here
        }
    }
}


