package Tower_Defence;

import Tower_Defence.UI.MenuWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Start the application by opening the menu window
        SwingUtilities.invokeLater(MenuWindow::new);
    }
}
