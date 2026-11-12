package edu.uic.cs342.poker.controller;

import edu.uic.cs342.poker.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class RulesController {
    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "/edu/uic/cs342/poker/fxml/welcome.fxml");
    }
}
