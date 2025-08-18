package Tower_Defence.Tower;

import java.util.ArrayList;

public class TowerManager {
    private static ArrayList<Tower> towers = new ArrayList<>();

    public static void addTower(Tower tower) {
        towers.add(tower);
    }

    public static Tower getTowerByName(String name) {
        for (Tower t : towers) {
            if (t.getName().equals(name)) return t;
        }
        return null;
    }

    public static ArrayList<Tower> getAllTowers() {
        return towers;
    }
}

