package Tower_Defence;

public class MoneyManager {
    private static int balance = 500; // starting money

    public static int getBalance() {
        return balance;
    }

    public static void addMoney(int amount) {
        balance += amount;
    }

    public static boolean spendMoney(int amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    
    public static int calculateWaveReward(int waveReached) {
        double reward = 100;
        for (int i = 1; i < waveReached; i++) {
            reward *= 1.5;
        }
        return (int) Math.ceil(reward);
    }
}
