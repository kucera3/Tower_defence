package Tower_Defence;

import Tower_Defence.Enemy.Enemy;

public class Shot extends Entity {
    private int damage;
    private int directionX;
    private int directionY;

    public Shot(String name, int positionY, int positionX, int damage, int directionX) {
        super(name, positionY, positionX);
        this.damage = damage;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    @Override
    public void doAction(Grid grid) {
        int newY = getPositionY() + directionY;
        int newX = getPositionX() + directionX;

        // remove if shot goes out of bounds
        if (grid.getBlock(newY, newX) == null) {
            grid.getBlock(getPositionY(), getPositionX()).removeEntity(this);
            return;
        }

        // move shot to new block
        grid.moveEntity(this, newY, newX);

        // check if there are enemies in this block
        for (Entity e : grid.getBlock(newY, newX).getEntities()) {
            if (e instanceof Enemy) {
                e.takeDamage(damage);
                // destroy shot after hit
                grid.getBlock(newY, newX).removeEntity(this);
                return;
            }
        }
    }
}

