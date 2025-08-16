package Tower_Defence;

public class Entity {
    private String name;
    private int positionY;
    private int positionX;

    public Entity() {
    }

    public Entity(String name, int positionY, int positionX) {
        this.name = name;
        this.positionY = positionY;
        this.positionX = positionX;
    }
    // implement pohyb
    public void doAction(Grid grid) {
    }


    public void onShotArrival() {
    }

    public void takeDamage(int damage) {
        positionY += damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
