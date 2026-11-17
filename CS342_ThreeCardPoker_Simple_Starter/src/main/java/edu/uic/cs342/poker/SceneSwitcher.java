package edu.uic.cs342.poker;
import edu.uic.cs342.poker.controller.GameController;
import edu.uic.cs342.poker.controller.ResultsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitcher {
    private static Parent gameRoot;
    private static GameController gameController;

    private static final String GAME_FXML =
            "/edu/uic/cs342/poker/fxml/game.fxml";



    private static Parent getGameRoot() throws IOException {
        if (gameRoot == null) {
            FXMLLoader loader = new FXMLLoader(
                    SceneSwitcher.class.getResource(
                            "/edu/uic/cs342/poker/fxml/game.fxml"
                    )
            );

            gameRoot = loader.load();
            gameController = loader.getController();
        }

        return gameRoot;
    }

    public static void switchTo(ActionEvent event, String fxmlPath)
            throws IOException {

        Parent newRoot;

        if (fxmlPath.equals(GAME_FXML)) {
            newRoot = getGameRoot();
        }  else {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(
                        SceneSwitcher.class.getResource(fxmlPath)
                )
        );

        newRoot = loader.load();

        if (fxmlPath.equals("/edu/uic/cs342/poker/fxml/results.fxml")) {
            getGameRoot(); // Make sure GameController exists first

            ResultsController resultsController = loader.getController();
            resultsController.setGameController(gameController);
        }
    }

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();

        stage.getScene().setRoot(newRoot);
    }

    public static void switchTo(ActionEvent event, Parent newRoot) {
        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(newRoot);
    }
}
