package Tower_Defence.Tower;

public class Buffer extends Tower {
    public Buffer(String name, int positionY, int positionX, int range, int damage, int upgradeCost, Type type, String imagePath) {
        super(name, positionY, positionX, range, damage, upgradeCost, type, imagePath);
    }

    public Buffer(String name, int positionY, int positionX) {
        super(name, positionY, positionX);
    }
}

