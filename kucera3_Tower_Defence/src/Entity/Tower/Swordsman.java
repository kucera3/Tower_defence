package Entity.Tower;

public class Swordsman extends Tower {
    public Swordsman(String name, int positionY, int positionX, int range, int damage, int upgradeCost) {
        super(name, positionY, positionX, range, damage, upgradeCost, Type.ATTACKER);
    }
}

