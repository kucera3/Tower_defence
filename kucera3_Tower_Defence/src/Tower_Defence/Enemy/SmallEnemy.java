package Tower_Defence.Enemy;

public class SmallEnemy extends Enemy {
    public SmallEnemy(String name, int positionY, int positionX, int hp, int speed) {
        super(name, positionY, positionX, hp, speed);
    }

    public SmallEnemy(String name, int startX, int startY) {
        super(name, startX, startY);
    }
}

