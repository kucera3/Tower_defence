package Tower_Defence.Tower;

import java.util.ArrayList;

public class TowerManager {
    private static ArrayList<Tower> towers = new ArrayList<>();
    private static double costMultiplier = 1.0;  // Starts at 1.0x
    private static final double COST_INCREASE_FACTOR = 1.5; // Each placement increases cost by 1.5x

    public static void addTower(Tower tower) {
        towers.add(tower);
        increaseTowerCost(); // 🔹 Increase cost each time a tower is placed
    }

    public static Tower getTowerByName(String name) {
        for (Tower t : towers) {
            if (t.getName().equals(name)) return t;
        }
        return null;
    }

    public static void reset() {
        towers.clear();
        costMultiplier = 1.0; // 🔹 Reset cost multiplier when game restarts
    }

    public static ArrayList<Tower> getAllTowers() {
        return towers;
    }

    public static double getCostMultiplier() {
        return costMultiplier;
    }

    private static void increaseTowerCost() {
        costMultiplier *= COST_INCREASE_FACTOR;
    }
}


