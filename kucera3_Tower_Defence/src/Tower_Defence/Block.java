package Tower_Defence;

import java.util.ArrayList;

public class Block extends Entity {
    private int row, col;
    private ArrayList<Entity> entities = new ArrayList<>();

    public Block(String name, int positionY, int positionX) {
        super(name, positionY, positionX);
        this.row = positionY;
        this.col = positionX;
    }

    @Override
    public void doAction(Grid grid) {

    }
    public void clearEntities() {
        if (entities != null) {
            entities.clear(); // remove all entities from this block
        }
    }


    public Block(Entity entity, int row, int col) {
        super(entity.getName(), row, col);
        this.entities.add(entity);
        this.row = row;
        this.col = col;
    }

    public void addEntity(Entity e) {
        entities.add(e);
        if (e instanceof Shot) {
            // Notify all entities in this block that a shot arrived
            for (Entity neighbor : entities) {
                neighbor.onShotArrival();
            }
        }
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public ArrayList<Entity> getEntities() {
        return new ArrayList<>(entities);
    }


    public boolean hasEntityOfType(Class<?> clazz) {
        for (Entity e : entities) {
            if (clazz.isInstance(e)) return true;
        }
        return false;
    }

    // Getters and setters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}


