package Tower_Defence;


import Tower_Defence.UI.GamePanel;
import Tower_Defence.UI.GameWindow;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();

        GamePanel panel = new GamePanel(grid);
        new GameWindow(panel);

    }
}