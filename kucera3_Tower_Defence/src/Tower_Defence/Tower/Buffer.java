package Tower_Defence.Tower;

public class Buffer extends Tower {
    public Buffer(String name, int positionY, int positionX, int range, int damage, int upgradeCost) {
        super(name, positionY, positionX, range, damage, upgradeCost, Type.SUPPORT);
    }
}

