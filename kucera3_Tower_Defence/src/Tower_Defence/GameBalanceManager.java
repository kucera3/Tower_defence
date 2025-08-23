package Tower_Defence;

public class GameBalanceManager {
    private static int balance;
    private static int towerCost;
    private static double enemyRewardMultiplier = 1.0; // multiplies each enemy reward

    public static void startNewGame() {
        balance = 200; // starting money
        towerCost = 100;
        enemyRewardMultiplier = 1.0;
    }

    public static void reset() {
        balance = 0;
        enemyRewardMultiplier = 1.0;
    }

    public static int getBalance() { return balance; }
    public static boolean hasEnoughBalance(int amount) { return balance >= amount; }

    public static boolean spendBalance(int amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public static void addBalance(int amount) {
        balance += amount;
    }

    public static boolean buyTower() {
        if (balance >= towerCost) {
            balance -= towerCost;
            towerCost += 50;
            return true;
        }
        return false;
    }

    public static int getTowerCost() { return towerCost; }
    public static void setBalance(int amount) { balance = amount; }
    public static void resetTowerCost() { towerCost = 100; }

    // new methods for enemy reward
    public static int getEnemyReward() {
        return (int) (100 * enemyRewardMultiplier); // base 100$ * wave multiplier
    }

    public static void increaseEnemyReward() {
        enemyRewardMultiplier *= 1.2; // increase 20% per wave
    }
}

