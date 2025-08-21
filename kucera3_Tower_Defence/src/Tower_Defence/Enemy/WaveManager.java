package Tower_Defence.Enemy;

import Tower_Defence.Block;
import Tower_Defence.Grid;

import java.util.*;

public class WaveManager {

    private static int currentWave = 0;
    private static boolean waveInProgress = false;
    private static Queue<Enemy> spawnQueue = new LinkedList<>();
    private static int spawnCounter = 0;

    public static void startNextWave() {
        if (waveInProgress) return; // wait until current wave is cleared

        currentWave++;
        waveInProgress = true;
        spawnQueue.clear();
        spawnCounter = 0;

        // Base HP
        int smallHp = 10;
        int middleHp = 20;
        int bigHp = 40;

        if (currentWave > 10) {
            double multiplier = 1 + (currentWave - 10) * 0.5; // 50% more per extra wave
            smallHp = (int) (smallHp * multiplier);
            middleHp = (int) (middleHp * multiplier);
            bigHp = (int) (bigHp * multiplier);
        }

        if (currentWave % 10 == 0) {
            Enemy boss = new Boss("Boss", 0, 0);
            boss.setHp(bigHp * 10);
            spawnQueue.add(boss);
        } else {
            int waveIndex = (currentWave % 10);
            int smallCount = 5 + waveIndex;
            int middleCount = 3 + waveIndex / 2;
            int bigCount = 1 + waveIndex / 3;

            for (int i = 0; i < smallCount; i++) {
                Enemy e = new SmallEnemy("SmallEnemy", 0, 0);
                e.setHp(smallHp);
                spawnQueue.add(e);
            }
            for (int i = 0; i < middleCount; i++) {
                Enemy e = new MiddleEnemy("MiddleEnemy", 0, 0);
                e.setHp(middleHp);
                spawnQueue.add(e);
            }
            for (int i = 0; i < bigCount; i++) {
                Enemy e = new BigEnemy("BigEnemy", 0, 0);
                e.setHp(bigHp);
                spawnQueue.add(e);
            }
        }
    }

    public static Enemy spawnNextEnemy(Grid grid) {
        if (spawnQueue.isEmpty()) return null;

        spawnCounter++;
        int interval = 20;
        if (spawnCounter % (interval / Math.max(spawnQueue.size(), 1)) == 0) {
            Enemy e = spawnQueue.poll();
            if (e != null) {
                Block spawnBlock = grid.getBlock(grid.getRows() - 1, 0); // bottom-left starting block
                spawnBlock.addEntity(e);
                return e; // return the spawned enemy
            }
        }
        return null;
    }

    public static boolean isWaveCleared(Grid grid) {
        boolean enemiesAlive = grid.getAllEntities().stream().anyMatch(entity -> entity instanceof Enemy);
        if (!enemiesAlive && spawnQueue.isEmpty()) {
            waveInProgress = false;
            return true;
        }
        return false;
    }

    public static void setCurrentWave(int currentWave) {
        WaveManager.currentWave = currentWave;
    }

    public static int getCurrentWave() {
        return currentWave;
    }
}