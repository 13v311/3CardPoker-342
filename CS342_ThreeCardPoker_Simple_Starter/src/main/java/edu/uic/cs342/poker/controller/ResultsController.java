package edu.uic.cs342.poker.controller;

import edu.uic.cs342.poker.GameLogic.Player;
import edu.uic.cs342.poker.SceneSwitcher;
import edu.uic.cs342.poker.Service.GameService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ResultsController {

    private GameService gameService;
    private Player player1;
    private Player player2;

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
        updateResults();
    }

    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        updateResults();

    }
    @FXML
    private Label playerLabel;

    @FXML
    private Button continueButton;

    @FXML
    private Button menuButton;

    @FXML
    private Label decisionLabel1;

    @FXML
    private Label anteLabel1;

    @FXML
    private Label playLabel1;

    @FXML
    private Label ppLabel1;

    @FXML
    private Label totalLabel1;

    @FXML
    private Label decisionLabel2;

    @FXML
    private Label anteLabel2;

    @FXML
    private Label playLabel2;

    @FXML
    private Label ppLabel2;

    @FXML
    private Label totalLabel2;

    public void initialize() {
        //anteLabel1.setText("$"+player1.getAnte());
    }

    private void updateResults() {

        anteLabel1.setText("$"+player1.getAnte());
        anteLabel2.setText("$"+player2.getAnte());

        System.out.println("Ante label:" + anteLabel1);

        if(player1.getFolded()) {decisionLabel1.setText("Folded");
        } else {decisionLabel1.setText("Played");}

        if(player2.getFolded()) {decisionLabel2.setText("Folded");
        } else {decisionLabel2.setText("Played");}

        playLabel1.setText("$" + player1.getPlayWager());
        playLabel2.setText("$" + player2.getPlayWager());
        ppLabel1.setText("$" + player1.getPairPlusWager());
        ppLabel2.setText("$" + player2.getPairPlusWager());
        totalLabel1.setText("$" + player1.getWinnings());
        totalLabel2.setText("$" + player2.getWinnings());
    }

    @FXML
    private void switchToMenu(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "/edu/uic/cs342/poker/fxml/welcome.fxml");
    }

    @FXML
    private void continueGame(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "/edu/uic/cs342/poker/fxml/game.fxml");
        gameService.continueNextRound();
    }


}
