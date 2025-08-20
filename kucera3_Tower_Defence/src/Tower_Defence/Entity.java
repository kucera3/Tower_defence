package Tower_Defence;

public abstract class Entity {
    private String name;
    private int positionY; // row
    private int positionX; // col
    private int health;

    public Entity(String name, int positionY, int positionX) {
        this.name = name;
        this.positionY = positionY;
        this.positionX = positionX;
        this.health = 100; // default health
    }
    public Entity(){

    }

    public String getName() {
        return name;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            onDeath();
        }
    }


    public abstract void doAction(Grid grid);


    public void onShotArrival() {
        // default: do nothing, enemies can override this
    }

    public void onDeath() {
        // default: entity disappears from grid
        Grid.getInstance().getBlock(positionY, positionX).removeEntity(this);
    }
}

