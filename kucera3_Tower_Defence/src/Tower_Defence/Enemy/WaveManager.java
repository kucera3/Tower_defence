package Tower_Defence.Enemy;

import Tower_Defence.Block;
import Tower_Defence.Grid;

import java.util.LinkedList;
import java.util.Queue;

public class WaveManager {

    private static int currentWave = 0;
    private static boolean waveInProgress = false;
    private static Queue<Enemy> spawnQueue = new LinkedList<>();
    private static int spawnCounter = 0;

    private static final int TOTAL_ENEMIES_PER_WAVE = 20;
    private static final int SPAWN_INTERVAL_MS = 1000; // 1 second per enemy

    public static void startNextWave() {
        if (waveInProgress) return;

        currentWave++;
        waveInProgress = true;
        spawnQueue.clear();
        spawnCounter = 0;

        int smallHp = 100;
        int middleHp = 200;
        int bigHp = 400;

        int waveMultiplier = ((currentWave - 1) / 10) + 2; // scales every 10 waves
        smallHp *= waveMultiplier;
        middleHp *= waveMultiplier;
        bigHp *= waveMultiplier;

        if (currentWave % 10 == 0) {
            // Boss wave
            Enemy boss = new Boss("Boss", 0, 0);
            boss.setHp(bigHp * 10);
            spawnQueue.add(boss);
        } else {
            // Calculate number of middle and big enemies
            int extraEnemies = (currentWave - 1) % 10; // 0..9
            int numMiddle = extraEnemies;
            int numBig = extraEnemies;
            int numSmall = TOTAL_ENEMIES_PER_WAVE - numMiddle - numBig;

            // Add small enemies
            for (int i = 0; i < numSmall; i++) {
                Enemy e = new SmallEnemy("SmallEnemy", 0, 0);
                e.setHp(smallHp);
                spawnQueue.add(e);
            }

            // Add middle enemies
            for (int i = 0; i < numMiddle; i++) {
                Enemy e = new MiddleEnemy("MiddleEnemy", 0, 0);
                e.setHp(middleHp);
                spawnQueue.add(e);
            }

            // Add big enemies
            for (int i = 0; i < numBig; i++) {
                Enemy e = new BigEnemy("BigEnemy", 0, 0);
                e.setHp(bigHp);
                spawnQueue.add(e);
            }
        }
    }

    // Called each game tick (~200ms); spawns enemy if it's time
    public static Enemy spawnNextEnemy(Grid grid) {
        if (spawnQueue.isEmpty()) return null;

        spawnCounter++;
        if (spawnCounter % (SPAWN_INTERVAL_MS / 200) == 0) {
            Enemy e = spawnQueue.poll();
            if (e != null) {
                Block spawnBlock = grid.getBlock(grid.getRows() - 1, 0); // bottom-left spawn
                spawnBlock.addEntity(e);
                return e;
            }
        }
        return null;
    }

    public static boolean isWaveCleared(Grid grid) {
        // Check EnemyManager for alive enemies instead of the grid
        boolean enemiesAlive = EnemyManager.getAllEnemies().stream().anyMatch(Enemy::isAlive);
        boolean queueEmpty = spawnQueue.isEmpty();

        if (!enemiesAlive && queueEmpty) {
            waveInProgress = false;
            return true;
        }
        return false;
    }


    public static void setCurrentWave(int wave) {
        currentWave = wave;
    }

    public static int getCurrentWave() {
        return currentWave;
    }
    public static boolean isWaveInProgress() {
        return waveInProgress;
    }

}
