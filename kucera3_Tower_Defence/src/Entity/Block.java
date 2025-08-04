package Entity;

import java.util.ArrayList;

public class Block extends Entity{

    private ArrayList<Entity> entities = new ArrayList<Entity>();

    public Block(String name, int positionY, int positionX) {
        super(name, positionY, positionX);
    }

    public void addEntity(Entity e) {
        entities.add(e);
        if(e instanceof Shot) {
            for(Entity neighbor : entities){
                neighbor.onShotArrival();
            }
        }
    }
    public void getentities(){}

    public void removeEntity(Entity e) {
        entities.remove(e);
    }
}
