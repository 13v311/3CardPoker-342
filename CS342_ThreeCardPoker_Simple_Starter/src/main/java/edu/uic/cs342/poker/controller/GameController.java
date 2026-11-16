package edu.uic.cs342.poker.controller;

import edu.uic.cs342.poker.GameLogic.Dealer;
import edu.uic.cs342.poker.GameLogic.Player;
import edu.uic.cs342.poker.SceneSwitcher;
import edu.uic.cs342.poker.Service.GameService;
import edu.uic.cs342.poker.ThemeManager;
import edu.uic.cs342.poker.cards.Card;
import edu.uic.cs342.poker.cards.Deck;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
    }





    @FXML
    private void switchTheme() {
        Scene scene = ThemeButton.getScene();
        ThemeManager.toggleTheme(scene);
    }

    @FXML
    private void deal() {
        DealButton.getStyleClass().remove("active");
        PlayButton1.getStyleClass().add("active");
        FoldButton1.getStyleClass().add("active");
        PlayButton2.getStyleClass().add("active");
        FoldButton2.getStyleClass().add("active");
        anteField1.getStyleClass().add("active");
        anteField2.getStyleClass().add("active");
        pairPlusField1.getStyleClass().add("active");
        pairPlusField2.getStyleClass().add("active");


        gameService.startGame();
    }



    @FXML
    private void play1() {
        String ante = anteField1.getText();
        String pp = pairPlusField1.getText();
        if(ante.isEmpty() || pp.isEmpty()) {
            errorMessage.setText("Please enter an amount for both ante and pair plus fields (Player 1).");
        } else if(!isNumeric(ante) || !isNumeric(pp)) {
            errorMessage.setText("Please enter a numeric value (Player 1).");
        } else {
            ante1 = Integer.parseInt(ante);
            pp1 = Integer.parseInt(pp);

            if(ante1 < 5 || pp1 < 5 || ante1 > player1.getAmount() || pp1 > player1.getAmount() || ante1 > 25 || pp1 > 25) {
                errorMessage.setText("Please enter a valid amount that is less than your total amount and between 5 & 25 inclusive (Player 1).");

            } else {
                PlayButton1.getStyleClass().remove("active");
                FoldButton1.getStyleClass().remove("active");
                p1decision = true;

                if(p2decision) {
                    if(folded2) {
                        gameService.wagers(ante1, pp1, 0, 0);
                        gameService.play(player1);
                        gameService.fold(player2);
                        //player1.setAnte(ante1);

                    } else{
                        gameService.wagers(ante1, pp1, ante2, pp2);
                        gameService.play(player1);
                        gameService.play(player2);
                    }
                    showCards(folded1, folded2);
                }
            }


        }




    }

    @FXML
    private void play2() {
        String ante = anteField2.getText();
        String pp = pairPlusField2.getText();
        if(ante.isEmpty() || pp.isEmpty()) {
            errorMessage.setText("Please enter an amount for both ante and pair plus fields (Player 2).");
        } else if(!isNumeric(ante) || !isNumeric(pp)) {
            errorMessage.setText("Please enter a numeric value (Player 2).");
        } else {
            ante2 = Integer.parseInt(ante);
            pp2 = Integer.parseInt(pp);

            if (ante2 < 5 || pp2 < 5 || ante2 > player2.getAmount() || pp2 > player2.getAmount() || ante2 > 25 || pp2 > 25) {
                errorMessage.setText("Please enter a valid amount that is less than your total amount and between 5 & 25 inclusive(Player 2).");

            } else {
                PlayButton2.getStyleClass().remove("active");
                FoldButton2.getStyleClass().remove("active");
                p2decision = true;

                if (p1decision) {
                    if (folded1) {
                        gameService.wagers(0, 0, ante2, pp2);
                        gameService.fold(player1);
                        gameService.play(player2);

                    } else {
                        gameService.wagers(ante1, pp1, ante2, pp2);
                        gameService.play(player1);
                        gameService.play(player2);
                    }
                    showCards(folded1, folded2);
                }
            }
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
                gameService.wagers(0, 0, 0, 0);
                gameService.fold(player1);
                gameService.fold(player2);
            } else{
                gameService.wagers(0, 0, ante2, pp2);
                gameService.fold(player1);
                gameService.play(player2);
            }
            showCards(folded1, folded2);
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
                gameService.wagers(0, 0, 0, 0);
                gameService.fold(player1);
                gameService.fold(player2);
            } else{
                gameService.wagers(ante1, pp1, 0, 0);
                gameService.play(player1);
                gameService.fold(player2);
            }
            showCards(folded1, folded2);
        }

    }

    private void transitionToResults() {
        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
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

    private void fadeSwapImage(ImageView img, String filename) {
        //fade out the current img
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), img);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);


        fadeOut.setOnFinished(event -> {
            Image nextImage = new Image(getClass().getResourceAsStream(filename));
            img.setImage(nextImage);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), img);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }

    private void showCards(boolean folded1, boolean folded2) {
        if(!folded1) {
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
                fadeSwapImage(p1Cards[i], filename);
                generalPause(2);

            }


        }
        if(!folded2) {
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
                fadeSwapImage(p2Cards[i], filename);

            }


        }

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

            fadeSwapImage(dealerCards[i], filename);
        }


        transitionToResults(); //this is the last thing and there is a 2.5 sec delay before it happens

    }
}
