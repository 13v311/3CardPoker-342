package edu.uic.cs342.poker;

import javafx.scene.Scene;

public class ThemeManager {
    private static boolean darkMode = false;

    private static final String LIGHT_CSS = ThemeManager.class.getResource("/edu/uic/cs342/poker/css/light-mode.css").toExternalForm();
    private static final String DARK_CSS  = ThemeManager.class.getResource("/edu/uic/cs342/poker/css/dark-mode.css").toExternalForm();

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void toggleTheme(Scene scene) {
        darkMode = !darkMode;
        applyTheme(scene);
    }

    public static void applyTheme(Scene scene) {
        if (scene == null) return;
        scene.getStylesheets().clear();
        scene.getStylesheets().add(darkMode ? DARK_CSS : LIGHT_CSS);
    }
}
