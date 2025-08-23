package Tower_Defence.Tower;
import Tower_Defence.Block;
import Tower_Defence.Entity;
import Tower_Defence.Grid;

public class Buffer extends Tower {
    private double buffMultiplier = 1.5;

    public Buffer(String name, int positionY, int positionX) {
        super(name, positionY, positionX, 1.0, 0, 100, Type.SUPPORT, "/buffer.png");
    }

    @Override
    public boolean upgrade() {
        if (super.upgrade()) {
            buffMultiplier *= 1.1;
            return true;
        }
        return false;
    }

    public void applyBuffer(Grid grid) {
        for (Block block : grid.getBlocksInRange(getPositionY(), getPositionX(), 1)) {
            for (Entity e : block.getEntities()) {
                if (e instanceof Tower t && t != this && t.getType() != Type.SUPPORT) {
                    t.setDamage((int) (t.getDamage() * buffMultiplier));
                }
            }
        }
    }
}


