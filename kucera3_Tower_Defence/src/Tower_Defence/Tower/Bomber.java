package Tower_Defence.Tower;

public class Bomber extends Tower {
    public Bomber(String name, int positionY, int positionX, int range, int damage, int upgradeCost) {
        super(name, positionY, positionX, range, damage, upgradeCost, Type.ATTACKER);
    }

    public Bomber(String name, int positionY, int positionX) {
        super(name, positionY, positionX);
    }

    public Bomber() {
        super();
    }
}
