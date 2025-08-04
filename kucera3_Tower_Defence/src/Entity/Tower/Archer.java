package Entity.Tower;

public class Archer extends Tower {
    public Archer(String name, int positionY, int positionX, int range, int damage, int upgradeCost) {
        super(name, positionY, positionX, range, damage, upgradeCost, Type.ATTACKER);
    }
}
