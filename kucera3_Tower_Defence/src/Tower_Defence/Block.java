package Tower_Defence;

import Tower_Defence.Tower.Tower;

import java.util.ArrayList;

public class Block extends Entity {
    private int row, col;
    private Tower tower;
    private ArrayList<Entity> entities = new ArrayList<>();

    public Block(String name, int positionY, int positionX) {
        super(name, positionY, positionX);
    }

    public void addEntity(Entity e) {
        entities.add(e);
        if (e instanceof Shot) {
            for (Entity neighbor : entities) {
                neighbor.onShotArrival();
            }
        }
    }

    public boolean hasTower() {
        return tower != null;
    }

    public void placeTower(Tower tower) {
        this.tower = tower;
    }

    public void removeTower() {
        this.tower = null;
    }


    public Tower getTower() {
        return tower;
    }

    public ArrayList<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }
}

