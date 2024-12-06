package ui;

import javax.swing.SwingUtilities;

import ui.gui.AdventureGameGUI;
import ui.gui.SplashScreen;

public class Main {
    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen("images/splashscreen.png", 4000);
        
        splashScreen.showSplashScreen(4000, () -> {
            SwingUtilities.invokeLater(() -> new AdventureGameGUI());
        });
    }
}
