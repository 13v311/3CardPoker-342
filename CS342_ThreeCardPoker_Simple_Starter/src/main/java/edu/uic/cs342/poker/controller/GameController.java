package edu.uic.cs342.poker.controller;

import edu.uic.cs342.poker.GameLogic.Dealer;
import edu.uic.cs342.poker.GameLogic.Player;
import edu.uic.cs342.poker.GameLogic.ThreeCardLogic;
import edu.uic.cs342.poker.SceneSwitcher;
import edu.uic.cs342.poker.Service.GameService;
import edu.uic.cs342.poker.ThemeManager;
import edu.uic.cs342.poker.cards.Card;
import edu.uic.cs342.poker.cards.Deck;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class GameController {

    private GameService gameService;
    private Deck deck;
    private Player player1;
    private Player player2;
    private Dealer dealer;
    private boolean p1decision = false;
    private boolean p2decision = false;
    private boolean folded1 = false;
    private boolean folded2 = false;
    private int ante1;
    private int ante2;
    private int pp1;
    private int pp2;

    @FXML
    private Button ThemeButton;

    @FXML
    private Button PlayButton1;

    @FXML
    private Button FoldButton1;

    @FXML
    private Button PlayButton2;

    @FXML
    private Button FoldButton2;

    @FXML
    private Button DealButton;

    @FXML
    private TextField anteField1;

    @FXML
    private TextField anteField2;

    @FXML
    private ImageView deckCard;

    @FXML
    private TextField pairPlusField1;

    @FXML
    private TextField pairPlusField2;

    @FXML
    private Label errorMessage;

    @FXML
    private ImageView p1Card1;

    @FXML
    private ImageView p1Card2;

    @FXML
    private ImageView p1Card3;

    @FXML
    private ImageView p2Card1;

    @FXML
    private ImageView p2Card2;

    @FXML
    private ImageView p2Card3;

    @FXML
    private ImageView dealerCard1;

    @FXML
    private ImageView dealerCard2;

    @FXML
    private ImageView dealerCard3;

    @FXML
    private Label HandLabel1;

    @FXML
    private Label HandLabel2;

    @FXML
    private VBox P2Box;

    private ImageView[] p1Cards;
    private ImageView[] p2Cards;
    private ImageView[] dealerCards;

    public static boolean isNumeric(String str) {
        if (str == null || str.isBlank()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void initialize() throws IOException {
        player1 = new Player(100);
        player2 = new Player(100);
        deck = new Deck();
        dealer = new Dealer(deck);
        gameService = new GameService(player1, player2, dealer);




        p1decision = false; p2decision = false; folded1 = false; folded2 = false;
        ante1 = 0; ante2 = 0; pp1 = 0; pp2 = 0;
        errorMessage.setText("");

        p1Cards = new ImageView[] {p1Card1, p1Card2, p1Card3};
        p2Cards = new ImageView[] {p2Card1, p2Card2, p2Card3};
        dealerCards = new ImageView[] {dealerCard1, dealerCard2, dealerCard3};

        //the program will start with the transition for the cards
        dealCardSequence();
        //dealCardTransition(p1Card1, 50, 50);
    }

    private TranslateTransition dealCardTransition(ImageView card) {

        Point2D deckScenePosition = deckCard.localToScene(0, 0);

        Pane playerPane = (Pane) card.getParent();
        Point2D deckPanePosition = playerPane.sceneToLocal(deckScenePosition);

        double startX = deckPanePosition.getX() - card.getLayoutX();
        double startY = deckPanePosition.getY() - card.getLayoutY();

        card.setTranslateX(startX);
        card.setTranslateY(startY);

        TranslateTransition transition =
                new TranslateTransition(Duration.seconds(0.5), card);

        transition.setToX(0);
        transition.setToY(0);

        return transition;
    }

    private void dealCardSequence() {
        Platform.runLater(() -> {
            //each comment represents the intended position the cards are meant to get to, from a starting pos of 400, 0 for dealer cards
            SequentialTransition sequence = new SequentialTransition();
            sequence.getChildren().add(dealCardTransition(dealerCard1)); //300, 100
            sequence.getChildren().add(dealCardTransition(dealerCard2)); //400, 100
            sequence.getChildren().add(dealCardTransition(dealerCard3)); //500, 100

            //p1 cards
            sequence.getChildren().add(dealCardTransition(p1Card1)); //100, 0
            sequence.getChildren().add(dealCardTransition(p1Card2));//200, 0
            sequence.getChildren().add(dealCardTransition(p1Card3));//300, 0

            //p2 cards
            sequence.getChildren().add(dealCardTransition(p2Card1));//100, 0
            sequence.getChildren().add(dealCardTransition(p2Card2));//200, 0
            sequence.getChildren().add(dealCardTransition(p2Card3));//300, 0

            sequence.play();
        });

    }





    @FXML
    private void switchTheme() {
        Scene scene = ThemeButton.getScene();
        ThemeManager.toggleTheme(scene);
    }

    private boolean checkFields() {
        String ante1 = anteField1.getText();
        String pp1 = pairPlusField1.getText();
        String ante2 = anteField2.getText();
        String pp2 = pairPlusField2.getText();
        if (ante1.isEmpty() || pp1.isEmpty()) {
            errorMessage.setText("Please enter an amount for both ante and pair plus fields (Player 1).");
            return false;
        } else if (!isNumeric(ante1) || !isNumeric(pp1)) {
            errorMessage.setText("Please enter a numeric value (Player 1).");
            return false;
        } else {
            int anteInt = Integer.parseInt(ante1);
            int ppInt = Integer.parseInt(pp1);

            if (anteInt < 5 || ppInt < 5 || anteInt > player1.getAmount() || ppInt > player1.getAmount() || anteInt > 25 || ppInt > 25) {
                errorMessage.setText("Please enter a valid amount that is less than your total amount and between 5 & 25 inclusive (Player 1).");
                return false;
            }
        }

        if (ante2.isEmpty() || pp2.isEmpty()) {
            errorMessage.setText("Please enter an amount for both ante and pair plus fields (Player 2).");
            return false;
        } else if (!isNumeric(ante2) || !isNumeric(pp2)) {
            errorMessage.setText("Please enter a numeric value (Player 2).");
            return false;
        } else {
            int anteInt = Integer.parseInt(ante2);
            int ppInt = Integer.parseInt(pp2);

            if (anteInt < 5 || ppInt < 5 || anteInt > player2.getAmount() || ppInt > player2.getAmount() || anteInt > 25 || ppInt > 25) {
                errorMessage.setText("Please enter a valid amount that is less than your total amount and between 5 & 25 inclusive (Player 2).");
                return false;
            }
        }


        return true;
    }

    @FXML
    private void deal() {
        if(checkFields()) {
            DealButton.getStyleClass().remove("active");
            PlayButton1.getStyleClass().add("active");
            FoldButton1.getStyleClass().add("active");
            PlayButton2.getStyleClass().add("active");
            FoldButton2.getStyleClass().add("active");
            anteField1.setEditable(false);
            anteField2.setEditable(false);
            pairPlusField1.setEditable(false);
            pairPlusField2.setEditable(false);


            gameService.startGame();

            //set the wagers in order to get hands for both players
            String ante1 = anteField1.getText();
            String pp1 = pairPlusField1.getText();
            String ante2 = anteField2.getText();
            String pp2 = pairPlusField2.getText();

            int anteInt1 = Integer.parseInt(ante1);
            int anteInt2 = Integer.parseInt(ante2);
            int ppInt1 = Integer.parseInt(pp1);
            int ppInt2 = Integer.parseInt(pp2);

            gameService.wagers(anteInt1, ppInt1, anteInt2, ppInt2);
            showPlayerCards();

            updateHandLabels(player1.getHand(), 1);
            updateHandLabels(player2.getHand(), 2);
        }

    }



    @FXML
    private void play1() {
        String ante = anteField1.getText();
        String pp = pairPlusField1.getText();
        ante1 = Integer.parseInt(ante);
        pp1 = Integer.parseInt(pp);

        PlayButton1.getStyleClass().remove("active");
        FoldButton1.getStyleClass().remove("active");
        p1decision = true;

        if(p2decision) {
            if(folded2) {

                gameService.play(player1);
                gameService.fold(player2);


            } else{

                gameService.play(player1);
                gameService.play(player2);
            }
            showDealerCards();
        }

    }

    @FXML
    private void play2() {
        String ante = anteField2.getText();
        String pp = pairPlusField2.getText();
        ante2 = Integer.parseInt(ante);
        pp2 = Integer.parseInt(pp);

        PlayButton2.getStyleClass().remove("active");
        FoldButton2.getStyleClass().remove("active");
        p2decision = true;

        if (p1decision) {
            if (folded1) {

                gameService.fold(player1);
                gameService.play(player2);

            } else {

                gameService.play(player1);
                gameService.play(player2);
            }
            showDealerCards();
        }

    }

    @FXML
    private void fold1() {
        PlayButton1.getStyleClass().remove("active");
        FoldButton1.getStyleClass().remove("active");
        p1decision = true;
        folded1 = true;

        if(p2decision) {
            if(folded2) {

                gameService.fold(player1);
                gameService.fold(player2);
            } else{

                gameService.fold(player1);
                gameService.play(player2);
            }
            showDealerCards();
        }

    }

    @FXML
    private void fold2() {
        PlayButton2.getStyleClass().remove("active");
        FoldButton2.getStyleClass().remove("active");
        p2decision = true;
        folded2 = true;

        if(p1decision) {
            if(folded1) {

                gameService.fold(player1);
                gameService.fold(player2);
            } else{

                gameService.play(player1);
                gameService.fold(player2);
            }
            showDealerCards();
        }

    }

    private void transitionToResults() {
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        Parent root;
        //making sure results controller has the game service
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/uic/cs342/poker/fxml/results.fxml")
        );
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ResultsController controller = loader.getController();

        controller.setPlayers(player1, player2);
        controller.setGameService(gameService);

        delay.setOnFinished(event -> {
            //the fxml element passed here doesn't matter
            SceneSwitcher.switchTo(new ActionEvent(dealerCard1, null), root);
        });

        delay.play();
    }

    private void generalPause(double secs) {
        PauseTransition delay = new PauseTransition(Duration.seconds(secs));
        delay.play();
    }

    private Animation fadeSwapImage(ImageView img, String filename) {
        //fade out the current img
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), img);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);


        fadeOut.setOnFinished(event -> {
            Image nextImage = new Image(getClass().getResourceAsStream(filename));
            img.setImage(nextImage);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), img);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        return fadeOut;

    }

    private void showDealerCards() {
        SequentialTransition sequence = new SequentialTransition();
        List<Card> dealerHand = dealer.getHand();
        for(int i = 0; i < dealerHand.size(); i++) {
            String suitName = dealerHand.get(i).suit().toString().toLowerCase();
            String rank = dealerHand.get(i).rank().toString().toLowerCase();

            switch (dealerHand.get(i).rank()) {
                case ACE -> rank = "ace";
                case TWO -> rank = "2";
                case THREE -> rank = "3";
                case FOUR -> rank = "4";
                case FIVE -> rank = "5";
                case SIX -> rank = "6";
                case SEVEN -> rank = "7";
                case EIGHT -> rank = "8";
                case NINE -> rank = "9";
                case TEN -> rank = "10";
                case JACK -> rank = "jack";
                case QUEEN -> rank = "queen";
                case KING -> rank = "king";
            }

            String filename = "/edu/uic/cs342/poker/images/PNG-cards-1.3/" + rank + "_of_" + suitName + ".png";

            sequence.getChildren().add(fadeSwapImage(dealerCards[i], filename));
        }
        sequence.play();

        transitionToResults(); //this is the last thing and there is a 2.5 sec delay before it happens
        DealButton.getStyleClass().add("active");
        HandLabel1.setText("");
        HandLabel2.setText("");
    }

    private void showPlayerCards() {
        SequentialTransition sequence = new SequentialTransition();



            List<Card> p1Hand = player1.getHand();

            for(int i = 0; i < p1Hand.size(); i++) {
                String suitName = p1Hand.get(i).suit().toString().toLowerCase();
                String rank = p1Hand.get(i).rank().toString().toLowerCase();

                switch (p1Hand.get(i).rank()) {
                    case ACE -> rank = "ace";
                    case TWO -> rank = "2";
                    case THREE -> rank = "3";
                    case FOUR -> rank = "4";
                    case FIVE -> rank = "5";
                    case SIX -> rank = "6";
                    case SEVEN -> rank = "7";
                    case EIGHT -> rank = "8";
                    case NINE -> rank = "9";
                    case TEN -> rank = "10";
                    case JACK -> rank = "jack";
                    case QUEEN -> rank = "queen";
                    case KING -> rank = "king";
                }

                String filename = "/edu/uic/cs342/poker/images/PNG-cards-1.3/" + rank + "_of_" + suitName + ".png";
                sequence.getChildren().add(fadeSwapImage(p1Cards[i], filename));


            }




            List<Card> p2Hand = player2.getHand();
            for(int i = 0; i < p2Hand.size(); i++) {
                String suitName = p2Hand.get(i).suit().toString().toLowerCase();
                String rank = p2Hand.get(i).rank().toString().toLowerCase();

                switch (p2Hand.get(i).rank()) {
                    case ACE -> rank = "ace";
                    case TWO -> rank = "2";
                    case THREE -> rank = "3";
                    case FOUR -> rank = "4";
                    case FIVE -> rank = "5";
                    case SIX -> rank = "6";
                    case SEVEN -> rank = "7";
                    case EIGHT -> rank = "8";
                    case NINE -> rank = "9";
                    case TEN -> rank = "10";
                    case JACK -> rank = "jack";
                    case QUEEN -> rank = "queen";
                    case KING -> rank = "king";
                }

                String filename = "/edu/uic/cs342/poker/images/PNG-cards-1.3/" + rank + "_of_" + suitName + ".png";
                sequence.getChildren().add(fadeSwapImage(p2Cards[i], filename));
            }
            sequence.play();
    }

    private void updateHandLabels(List<Card> hand, int player) {
        String labelText = "";
        switch (ThreeCardLogic.evalHand(hand)) {
            case PAIR -> labelText = "Pair";
            case FLUSH -> labelText = "Flush";
            case STRAIGHT -> labelText = "Straight";
            case HIGH_CARD -> labelText = "High Card";
            case THREE_OF_KIND -> labelText = "Three of a Kind";
            case STRAIGHT_FLUSH -> labelText = "Straight Flush";
        }

        if(player == 1) { HandLabel1.setText(labelText); }
        else if (player == 2) { HandLabel2.setText(labelText); }
    }

}
