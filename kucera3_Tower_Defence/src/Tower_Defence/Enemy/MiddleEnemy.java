package Tower_Defence.Enemy;

public class MiddleEnemy extends Enemy {
    public MiddleEnemy(String name, int positionY, int positionX, int hp, int speed) {
        super(name, positionY, positionX, hp, speed);
    }

    public MiddleEnemy(String name, int startX, int startY) {
        super(name, startX, startY);
    }
}

