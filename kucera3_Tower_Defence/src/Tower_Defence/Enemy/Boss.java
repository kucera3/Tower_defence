package Tower_Defence.Enemy;

public class Boss extends Enemy {
    public Boss(String name, int positionY, int positionX, int hp, int speed) {
        super(name, positionY, positionX, hp, speed);
    }

    public Boss(String name, int startX, int startY) {
        super(name, startX, startY);
    }
}

