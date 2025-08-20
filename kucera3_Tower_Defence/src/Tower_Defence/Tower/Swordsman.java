package Tower_Defence.Tower;

public class Swordsman extends Tower {
    public Swordsman(String name, int positionY, int positionX, int range, int damage, int upgradeCost, Type type, String imagePath) {
        super(name, positionY, positionX, range, damage, upgradeCost, type, imagePath);
    }

    public Swordsman(String name, int positionY, int positionX) {
        super(name, positionY, positionX);
    }
}

