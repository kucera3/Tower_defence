package Tower_Defence;

public class GameBalanceManager {
    private static int balance;
    private static int towerCost;

    public static void startNewGame() {
        balance = 200; // starting money each game
        towerCost = 100; // initial tower price
    }
    public static void reset() {
        balance = 0;
    }



    public static int getBalance() {
        return balance;
    }


    public static boolean hasEnoughBalance(int amount) {
        return balance >= amount;
    }

    // Spend balance
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
            towerCost += 50; // increase price for next purchase
            return true;
        }
        return false;
    }


    public static int getTowerCost() {
        return towerCost;
    }

    public static void setBalance(int amount) {
        balance = amount;
    }

    public static void resetTowerCost() {
        towerCost = 100;
    }
}

