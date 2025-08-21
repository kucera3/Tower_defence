package Tower_Defence.Enemy;

public class BigEnemy extends Enemy {
    public BigEnemy(String name, int positionY, int positionX, int hp, int speed) {
        super(name, positionY, positionX, hp, speed);
    }

    public BigEnemy(String name, int startX, int startY) {
        super(name, startX, startY);
    }
}
