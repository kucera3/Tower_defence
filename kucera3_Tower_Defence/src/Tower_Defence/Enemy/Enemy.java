package Tower_Defence.Enemy;

import Tower_Defence.Entity;
import Tower_Defence.Grid;
import Tower_Defence.UI.GamePanel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity {

    private List<Point> path = new ArrayList<>();
    private int currentWaypointIndex = 0;

    private int hp;
    private boolean isAlive;

    private double posX, posY; // exact position in blocks
    private double speed; // blocks per tick

    public Enemy(String name, int hp, double speed, int i, int speed1) {
        super(name, 0, 0);
        this.hp = hp;
        this.speed = speed;
        this.isAlive = hp > 0;
        Grid grid = Grid.getInstance();
        this.posX = 0;
        this.posY = grid.getRows() - 1;

        generatePath();
    }
    public boolean hasReachedEnd() {
        return currentWaypointIndex >= path.size();
    }


    public Enemy(String name, int startRow, int startCol) {
        super(name, startRow, startCol);
        this.hp = 1;
        this.speed = 0.2;
        this.isAlive = true;
        this.posX = startCol;
        this.posY = startRow;
        generatePath();
    }
    public Enemy() {
        super();
    }
    private void generatePath() {
        Grid grid = Grid.getInstance();
        // Example path: bottom-left -> top-left -> top-right -> bottom-right
        path.add(new Point(0, grid.getRows() - 1));
        path.add(new Point(0, 0));
        path.add(new Point(grid.getCols() - 1, 0));
        path.add(new Point(grid.getCols() - 1, grid.getRows() - 1));
    }

    public Point getNextWaypoint() {
        if (currentWaypointIndex >= path.size()) return null;
        return path.get(currentWaypointIndex);
    }

    public void moveAlongPath() {
        Point target = getNextWaypoint();
        if (target == null) return;

        double dx = target.x - posX;
        double dy = target.y - posY;

        double distance = Math.sqrt(dx*dx + dy*dy);
        if (distance < speed) {
            posX = target.x;
            posY = target.y;
            currentWaypointIndex++;
        } else {
            posX += (dx / distance) * speed;
            posY += (dy / distance) * speed;
        }
    }

    @Override
    public void doAction(Grid grid) {
        if (isAlive) {
            moveAlongPath();
        }
    }

    public void takeDamage(int amount) {
        hp -= amount;
        if (hp <= 0) {
            hp = 0;
            isAlive = false;
        }
    }

    public int getHp() {
        return hp;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public int getPixelX(int xOffset) {
        return xOffset + (int)(posX * GamePanel.BLOCK_SIZE);
    }

    public int getPixelY(int yOffset) {
        return yOffset + (int)(posY * GamePanel.BLOCK_SIZE);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setHp(int hp) {
        this.hp = hp;
        this.isAlive = hp > 0;
    }
}
