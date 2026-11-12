package edu.uic.cs342.poker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Objects;

public class SceneSwitcher {
    public static void switchTo(ActionEvent event, String fxmlPath) throws IOException {
        Parent newRoot = FXMLLoader.load(
                Objects.requireNonNull(SceneSwitcher.class.getResource(fxmlPath))
        );
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(newRoot);
    }
}
