package edu.uic.cs342.poker.controller;

import edu.uic.cs342.poker.SceneSwitcher;
import edu.uic.cs342.poker.ThemeManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WelcomeController {
    @FXML
    private Label welcomeLabel;



    @FXML
    private void playGame(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "/edu/uic/cs342/poker/fxml/game.fxml");
    }

    @FXML
    private void exitProgram() {
        Platform.exit();
    }

    @FXML
    private void switchToRules(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "/edu/uic/cs342/poker/fxml/rules.fxml");
    }

    @FXML
    private Button ThemeButton;
    @FXML
    private void switchTheme() {
        Scene scene = ThemeButton.getScene();
        ThemeManager.toggleTheme(scene);
    }
}
