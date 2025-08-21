package Tower_Defence.Enemy;

import java.util.ArrayList;

public class EnemyManager {
    private static ArrayList<Enemy> enemies = new ArrayList<>();

    public static void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public static void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public static ArrayList<Enemy> getAllEnemies() {
        return enemies;
    }

    public static void reset() {
        enemies.clear();
    }

    public static boolean allDead() {
        for (Enemy e : enemies) {
            if (e.isAlive()) return false;
        }
        return true;
    }
}
