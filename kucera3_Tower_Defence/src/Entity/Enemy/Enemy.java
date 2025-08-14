package Entity.Enemy;

import Entity.*;

public class Enemy extends Entity {
    private int hp;
    private int speed;
    private boolean isAlive;

    public Enemy(String name, int positionY, int positionX, int hp, int speed) {
        super(name, positionY, positionX);
        this.hp = hp;
        this.speed = speed;
        this.isAlive = hp > 0;
    }

    @Override
    public void onShotArrival() {
        super.onShotArrival();
        takeDamage(1);
    }
    @Override
    public void doAction(Grid grid) {
        int newX = getPositionX() + speed;
        if (newX < 5) { // Grid width
            grid.moveEntity(this, getPositionY(), newX);
        }
    }

    public void moveRight() {
        setPositionX(getPositionX() + 1);
    }


    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    // Setters
    public void setHp(int hp) {
        this.hp = hp;
        this.isAlive = hp > 0;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void takeDamage(int amount) {
        this.hp -= amount;
        if (this.hp <= 0) {
            this.hp = 0;
            this.isAlive = false;
        }
    }
}

