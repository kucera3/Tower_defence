package Tower_Defence.Tower;

import Tower_Defence.Grid;
import Tower_Defence.Entity;
import Tower_Defence.Enemy.*;

public class Archer extends Tower {

    public Archer(String name, int positionY, int positionX) {
        super(name, positionY, positionX, Grid.DEFAULT_ROWS + Grid.DEFAULT_COLS, 5, 100, Type.ATTACKER, "/archer.png");
    }

    @Override
    public boolean upgrade() {
        return super.upgrade(); // damage +10%, cost x1.5, money deducted
    }
    @Override
    public void doAction(Grid grid) {
        // Reset target if dead
        if (getTarget() == null || getTarget().getHp() <= 0) {
            setTarget(null);
            for (Entity e : getEntitiesInRange(grid)) {
                if (e instanceof Enemy enemy && enemy.getHp() > 0) {
                    setTarget(enemy);
                    break;
                }
            }
        }

        // Attack target
        if (getTarget() != null) {
            getTarget().takeDamage(getDamage());
        }
    }

}
