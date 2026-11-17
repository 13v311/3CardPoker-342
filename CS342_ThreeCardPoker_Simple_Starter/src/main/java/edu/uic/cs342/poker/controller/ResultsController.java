package edu.uic.cs342.poker.controller;

import edu.uic.cs342.poker.GameLogic.Player;
import edu.uic.cs342.poker.GameLogic.ThreeCardLogic;
import edu.uic.cs342.poker.SceneSwitcher;
import edu.uic.cs342.poker.Service.GameRules;
import edu.uic.cs342.poker.Service.GameService;
import edu.uic.cs342.poker.cards.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Comparator;

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

    @FXML
    private Label winner;

    @FXML
    private Label winnerHow;

    public void initialize() {
        //updateResults();
    }

    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }


    private void updateResults() {

        if(player1.getDidWin() == 1 && player2.getDidWin() == 1){
            winner.setText("Player 1 and 2 Wins");
            winnerHow.setText(handRankLine(player1) + " and " + handRankLine(player2));
        }
        else if(player1.getDidWin() == 1){
            winner.setText("Player 1 Wins");
            winnerHow.setText(handRankLine(player1));
        }
        else if(player2.getDidWin() == 1){
            winner.setText("Player 2 Wins");
            winnerHow.setText(handRankLine(player2));
        }
        else{
            winner.setText("Dealer Wins");
        }


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

    private String handRankLine(Player player){
        ThreeCardLogic.HandRank ranking = ThreeCardLogic.evalHand(player.getHand());

        return switch (ranking) {
            case STRAIGHT_FLUSH -> "Straight Flush";
            case THREE_OF_KIND -> "Three of a Kind";
            case STRAIGHT -> "Straight";
            case FLUSH -> "Flush";
            case PAIR -> "Pair";
            case HIGH_CARD -> {
                Card highest = player.getHand().stream()
                        .max(Comparator.comparingInt(Card::getValue))
                        .orElseThrow();

                yield "High Card: " + highest.rank();
            }
        };

    }

    @FXML
    private void switchToMenu(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo(event, "/edu/uic/cs342/poker/fxml/welcome.fxml");
    }

    @FXML
    private void continueGame(ActionEvent event) throws IOException {
        //gameController.prepNextGame();
        SceneSwitcher.switchTo(event, "/edu/uic/cs342/poker/fxml/game.fxml");

    }


}
