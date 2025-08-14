import Entity.*;
import UI.*;
public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();

        GamePanel panel = new GamePanel(grid);
        new GameWindow(panel);

    }
}